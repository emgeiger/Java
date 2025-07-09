import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.BorderLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GraphicalCylinder extends JFrame
{
    private JSlider elevSlider;
    private JSlider azimuthSlider;
    private JPanel panel;
    private JPanel sliderPanel;
    private Cylinder cylinder;
    private Point mouseDownPoint;

    public GraphicalCylinder()
    {
        super("Graphical Cylinder");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createContents();
        
       // panel.setBackground(Color.white);
        add(panel);

        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                mouseDownPoint = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point currentPoint = e.getPoint();
                int deltaX = currentPoint.x - mouseDownPoint.x;
                int deltaY = currentPoint.y - mouseDownPoint.y;

                // Convert mouse movement to rotation instead of translation
                double newAzimuth = azimuthSlider.getValue() + deltaX * 0.5;
                double newElevation = elevSlider.getValue() - deltaY * 0.5;
                
                // Clamp values to slider ranges
                newAzimuth = Math.max(-90, Math.min(90, newAzimuth));
                newElevation = Math.max(-90, Math.min(90, newElevation));
                
                azimuthSlider.setValue((int)newAzimuth);
                elevSlider.setValue((int)newElevation);
                
                mouseDownPoint = currentPoint; // Update for smooth dragging
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void createContents()
    {
        panel = new JPanel(new BorderLayout()); // Add BorderLayout
        sliderPanel = new JPanel();

        // Remove these problematic lines - slider is not initialized
        // slider.setMajorTickSpacing(5);
        // slider.setPaintTicks(true);
        // slider.addChangeListener(new Listener());

        elevSlider = new JSlider(JSlider.HORIZONTAL, -90, 90, 0);
        elevSlider.setMajorTickSpacing(10);
        elevSlider.setPaintTicks(true);
        elevSlider.setPaintLabels(true);
        elevSlider.addChangeListener(new Listener());

        azimuthSlider = new JSlider(JSlider.HORIZONTAL, -90, 90, 0);
        azimuthSlider.setMajorTickSpacing(10);
        azimuthSlider.setPaintTicks(true);
        azimuthSlider.setPaintLabels(true);
        azimuthSlider.addChangeListener(new Listener());

        cylinder = new Cylinder(elevSlider.getValue(), azimuthSlider.getValue());
        cylinder.setPreferredSize(new Dimension(600, 500));
        
        // Add labels and organize sliders better
        JPanel elevPanel = new JPanel();
        elevPanel.add(new JLabel("Elevation: "));
        elevPanel.add(elevSlider);
        
        JPanel azimuthPanel = new JPanel();
        azimuthPanel.add(new JLabel("Azimuth: "));
        azimuthPanel.add(azimuthSlider);
        
        sliderPanel.setLayout(new GridLayout(2, 1));
        sliderPanel.add(elevPanel);
        sliderPanel.add(azimuthPanel);
        
        panel.add(cylinder, BorderLayout.CENTER); // Fix typo: BordderLayout -> BorderLayout
        panel.add(sliderPanel, BorderLayout.SOUTH);
    }

    private class Listener implements ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            if (e.getSource() == elevSlider) {
                cylinder.setElevation(elevSlider.getValue());
            } else if (e.getSource() == azimuthSlider) {
                cylinder.setAzimuth(azimuthSlider.getValue());
            }
            panel.repaint();
        }
    }

    public static void main(String[] args) {
        new GraphicalCylinder();
    }
}
