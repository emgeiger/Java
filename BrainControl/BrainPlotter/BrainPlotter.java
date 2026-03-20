package BrainPlotter.*;

import BrainControl.Library.*;
import BrainControl.Library.models.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import javax.swing.*;

public class BrainPlotter extends JFrame {
    private Queue<Integer> points = new ConcurrentLinkedQueue<>();
    private SerialPort ard = new SerialPort();
    private String saveFile;
    private static final Object locker = new Object();
    private short writeThreshold = 0;
    private static final float FOCUS_THRESHOLD = 1.15f;
    private static final float FOCUS_SCOPE = .2f;
    private JLabel fullAverageLabel;
    private JCheckBox clickCheck;
    private JCheckBox checkCom;
    private JTextArea textBox1;
    private Chart chart1;
    private JLabel sampleLabel;

    public BrainPlotter() {
        saveFile = new SimpleDateFormat("dd__HH-mm-ss").format(new Date()) + ".csv";
        try {
            new File(saveFile).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startArd("COM4");
        startBT();
    }

    private void startArd(String portName) {
        ard.setPortName(portName);
        ard.setBaudRate(9600);
        // ard.open();
    }

    private void startBT() {
        BtManager bt = new BtManager("COM10");

        // BT data received
        bt.setBtDataParsed((o, e) -> {
            points.add(e.getRawValue());
            SwingUtilities.invokeLater(() -> fullAverageLabel.setText("Full Focus Average: " + points.stream().mapToInt(Integer::intValue).average().orElse(0)));

            SwingUtilities.invokeLater(() -> chart1.getSeries().get(0).addPoint(e.getRawValue()));

            // Keeps queue and chart counts below max chart x axis
            if (points.size() > chart1.getChartAreas().get(0).getAxisX().getMaximum() - 1) {
                checkFocusThreshold(points);

                points.poll();
                SwingUtilities.invokeLater(() -> chart1.getSeries().get(0).removeAt(0));
            }

            // Writes values to CSV
            if (++writeThreshold >= chart1.getChartAreas().get(0).getAxisX().getMaximum() - 1) {
                writeThreshold = 0;
                appendToFile(saveFile, points.stream().map(Object::toString).collect(Collectors.joining(",")));
            }
        });
    }

    private void checkFocusThreshold(Queue<Integer> vals) {
        List<Integer> fullRange = new ArrayList<>(vals);
        double focusRange = fullRange.subList(0, (int) (vals.size() * FOCUS_SCOPE)).stream().mapToInt(Integer::intValue).average().orElse(0);

        if (focusRange > fullRange.stream().mapToInt(Integer::intValue).average().orElse(0) * FOCUS_THRESHOLD) {
            System.out.println("BING BANG BOOM REEEEEE");
            if (clickCheck.isSelected())
                click();

            if (checkCom.isSelected()) {
                SwingUtilities.invokeLater(() -> textBox1.append("REEEE TRIGGERED\n"));
                ard.write("ON");
            }
        } else {
            if (checkCom.isSelected())
                ard.write("OFF");
        }

        SwingUtilities.invokeLater(() -> sampleLabel.setText("Sample Buffer Average: " + focusRange));
    }

    private void click() {
        // Simulate mouse click in Java
    }

    private void appendToFile(String path, String txt) {
        synchronized (locker) {
            try (FileOutputStream file = new FileOutputStream(path, true);
                 OutputStreamWriter writer = new OutputStreamWriter(file, StandardCharsets.UTF_8)) {
                writer.write(txt);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        ard.close();
        super.finalize();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BrainPlotter().setVisible(true));
    }
}
