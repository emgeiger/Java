package BrainPlotter;

import BrainControl.Library.BtManager;
import BrainControl.Library.models.BtDataEventArgs;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.comm.SerialPort;
import javax.swing.*;
import javax.swing.border.*;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.*;

/**
 * MainForm is the main GUI application window for BrainPlotter, 
 * equivalent to Form1.cs from the C# project
 */
public class MainForm extends JFrame
{
    private static final long serialVersionUID = 1L;
    
    // Data collection
    private final Queue<Integer> points = new LinkedList<>();
    private SerialPort ardSerial;
    private final String saveFile;
    private static final Lock fileLock = new ReentrantLock();
    private short writeThreshold = 0;
    
    // Thresholds
    private static final float FOCUS_THRESHOLD = 1.15f;
    private static final float FOCUS_SCOPE = 0.2f;
    
    // UI Components
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private XYSeries dataSeries;
    private JLabel fullAverageLabel;
    private JLabel sampleLabel;
    private JTextField logTextArea;
    private JCheckBox clickCheck;
    private JCheckBox checkCom;
    
    // Robot for mouse events
    private Robot robot;
    private int clickCounter = 0;
    
    /**
     * Creates a new MainForm instance
     */
    public MainForm() {
        // Setup the window
        setTitle("Brain Plotter");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Initialize Robot for mouse control
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        
        // Create save file with timestamp
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd__HH-mm-ss");
        saveFile = dateFormat.format(new Date()) + ".csv";
        try (FileOutputStream fos = new FileOutputStream(saveFile)) {
            // Just create the file
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Setup UI
        initializeComponents();
        
        // Start connections
        startArd("COM4");
        startBT();
    }
    
    /**
     * Initializes all UI components
     */
    private void initializeComponents() {
        // Setup chart data
        dataSeries = new XYSeries("EEG Data");
        XYSeriesCollection dataset = new XYSeriesCollection(dataSeries);
        
        // Setup chart
        chart = ChartFactory.createXYLineChart(
            "Brain Wave Data",    // Title
            "Time",               // X-axis label
            "Value",              // Y-axis label
            dataset              // Dataset
        );
        
        // Configure chart appearance
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setRange(0, 100);  // Show last 100 points
        
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(760, 400));
        
        // Create status panel with labels
        JPanel statusPanel = new JPanel(new GridLayout(2, 1));
        fullAverageLabel = new JLabel("Full Focus Average: 0");
        sampleLabel = new JLabel("Sample Buffer Average: 0");
        statusPanel.add(fullAverageLabel);
        statusPanel.add(sampleLabel);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Create control panel with checkboxes
        JPanel controlPanel = new JPanel(new FlowLayout());
        clickCheck = new JCheckBox("Enable Mouse Click");
        checkCom = new JCheckBox("Enable COM Port");
        controlPanel.add(clickCheck);
        controlPanel.add(checkCom);
        
        // Create log panel
        JPanel logPanel = new JPanel(new BorderLayout());
        logTextArea = new JTextField();
        logTextArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logTextArea);
        logScrollPane.setPreferredSize(new Dimension(760, 80));
        logPanel.add(new JLabel("Log:"), BorderLayout.NORTH);
        logPanel.add(logScrollPane, BorderLayout.CENTER);
        logPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Add components to frame
        add(chartPanel, BorderLayout.CENTER);
        
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(statusPanel, BorderLayout.NORTH);
        southPanel.add(controlPanel, BorderLayout.CENTER);
        southPanel.add(logPanel, BorderLayout.SOUTH);
        add(southPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Sets up the Arduino serial connection
     * 
     * @param portName The COM port name
     */
    private void startArd(String portName) {
        try {
            javax.comm.CommPortIdentifier portId = javax.comm.CommPortIdentifier.getPortIdentifier(portName);
            ardSerial = (SerialPort) portId.open("BrainPlotter", 2000);
            ardSerial.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Sets up the Bluetooth connection and event handling
     */
    private void startBT() {
        try {
            BtManager bt = new BtManager("COM10");
            
            bt.addBtEventListener(new BtManager.BtEventListener() {
                @Override
                public void onBtDataParsed(Object sender, BtDataEventArgs e) {
                    // Add the new value
                    points.add(e.getRawValue());
                    
                    // Update UI from AWT event dispatch thread
                    SwingUtilities.invokeLater(() -> {
                        fullAverageLabel.setText("Full Focus Average: " + getAverage(points));
                        dataSeries.add(dataSeries.getItemCount(), e.getRawValue());
                    });
                    
                    // Keeps queue and chart counts below max chart x axis
                    int maxPoints = 100;  // Equivalent to chart1.ChartAreas[0].AxisX.Maximum
                    if (points.size() > maxPoints - 1) {
                        checkFocusThreshold(points);
                        
                        points.poll();  // Remove the oldest point
                        SwingUtilities.invokeLater(() -> {
                            if (dataSeries.getItemCount() > maxPoints) {
                                dataSeries.remove(0);
                            }
                        });
                    }
                    
                    // Writes values to CSV
                    if (++writeThreshold >= maxPoints - 1) {
                        writeThreshold = 0;
                        appendToFile(saveFile, String.join(",", toStringArray(points)));
                    }
                }
            });
            
            // Start the BT manager
            bt.start();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Converts a collection of Integers to an array of Strings
     * 
     * @param collection The Integer collection
     * @return Array of String representations
     */
    private String[] toStringArray(Collection<Integer> collection) {
        return collection.stream()
                .map(String::valueOf)
                .toArray(String[]::new);
    }
    
    /**
     * Calculates the average of a collection of integers
     * 
     * @param values The collection of integers
     * @return The average as an int
     */
    private int getAverage(Collection<Integer> values) {
        return (int) values.stream().mapToInt(Integer::intValue).average().orElse(0);
    }
    
    /**
     * Checks if the focus threshold has been exceeded
     * 
     * @param vals The collection of values to check
     */
    private void checkFocusThreshold(Collection<Integer> vals) {
        java.util.List<Integer> fullRange = new ArrayList<>(vals);
        
        // Calculate average for beginning portion of the list
        int sampleSize = (int)(vals.size() * FOCUS_SCOPE);
        double focusRangeAvg = fullRange.subList(0, sampleSize).stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);
        
        // Calculate overall average
        double fullRangeAvg = getAverage(fullRange);
        
        // Check if threshold exceeded
        if (focusRangeAvg > fullRangeAvg * FOCUS_THRESHOLD) {
            clickCounter++;
            System.out.println(clickCounter + "  BING BANG BOOM REEEEEE");
            
            if (clickCheck.isSelected()) {
                click();
            }
            
            if (checkCom.isSelected()) {
                final String message = clickCounter + " REEEE TRIGGERED\n";
                SwingUtilities.invokeLater(() -> {
                    logTextArea.setText(logTextArea.getText() + message);
                });
                
                try {
                    OutputStream outputStream = ardSerial.getOutputStream();
                    outputStream.write("ON".getBytes());
                    outputStream.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (checkCom.isSelected()) {
                try {
                    OutputStream outputStream = ardSerial.getOutputStream();
                    outputStream.write("OFF".getBytes());
                    outputStream.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        final String sampleText = "Sample Buffer Average: " + focusRangeAvg;
        SwingUtilities.invokeLater(() -> {
            sampleLabel.setText(sampleText);
        });
    }
    
    /**
     * Simulates a mouse click
     */
    private void click() {
        if (robot != null) {
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }
    }
    
    /**
     * Appends text to a file
     * 
     * @param path The file path
     * @param txt The text to append
     */
    private void appendToFile(String path, String txt) {
        fileLock.lock();
        try (FileOutputStream fos = new FileOutputStream(path, true);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
            osw.write(txt);
            osw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fileLock.unlock();
        }
    }
    
    /**
     * Cleans up resources when the window is closed
     */
    @Override
    public void dispose() {
        if (ardSerial != null) {
            ardSerial.close();
        }
        super.dispose();
    }
    
    /**
     * Main method to start the application
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainForm mainForm = new MainForm();
            mainForm.setVisible(true);
        });
    }
}
