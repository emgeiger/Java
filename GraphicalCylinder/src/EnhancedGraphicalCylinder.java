package com.cylinder.graphics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Enhanced graphical cylinder application with comprehensive controls.
 * Provides mouse drag capability, slider controls, and color customization
 * for viewing the cylinder from different angles.
 * 
 * @author Cylinder Graphics Team
 * @version 2.0
 */
public class EnhancedGraphicalCylinder extends JFrame {
    private JSlider elevSlider;
    private JSlider azimuthSlider;
    private JSlider heightSlider;
    private JSlider diameterSlider;
    private JSlider red1Slider, green1Slider, blue1Slider;
    private JSlider red2Slider, green2Slider, blue2Slider;
    private JPanel panel;
    private JPanel controlPanel;
    private Cylinder cylinder;
    private Point mouseDownPoint;
    private JButton resetButton;
    private JLabel statusLabel;

    public EnhancedGraphicalCylinder() {
        super("Enhanced Graphical Cylinder - 3D Rotation View");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createContents();
        
        add(panel);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseDownPoint = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point currentPoint = e.getPoint();
                int deltaX = currentPoint.x - mouseDownPoint.x;
                int deltaY = currentPoint.y - mouseDownPoint.y;

                // Convert mouse movement to rotation with sensitivity
                double sensitivity = 0.5;
                double newAzimuth = azimuthSlider.getValue() + deltaX * sensitivity;
                double newElevation = elevSlider.getValue() - deltaY * sensitivity;
                
                // Clamp values to slider ranges
                newAzimuth = Math.max(-90, Math.min(90, newAzimuth));
                newElevation = Math.max(-90, Math.min(90, newElevation));
                
                azimuthSlider.setValue((int)newAzimuth);
                elevSlider.setValue((int)newElevation);
                
                updateStatusLabel();
                mouseDownPoint = currentPoint;
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void createContents() {
        panel = new JPanel(new BorderLayout());
        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        // Initialize sliders
        elevSlider = createSlider("Elevation", -90, 90, 0);
        azimuthSlider = createSlider("Azimuth", -90, 90, 0);
        heightSlider = createSlider("Height", 200, 600, 400);
        diameterSlider = createSlider("Diameter", 100, 400, 200);
        
        // Color sliders for gradient effect
        red1Slider = createSlider("Red1", 0, 255, 76);
        green1Slider = createSlider("Green1", 0, 255, 76);
        blue1Slider = createSlider("Blue1", 0, 255, 200);
        
        red2Slider = createSlider("Red2", 0, 255, 178);
        green2Slider = createSlider("Green2", 0, 255, 178);
        blue2Slider = createSlider("Blue2", 0, 255, 255);

        // Create cylinder
        cylinder = new Cylinder(elevSlider.getValue(), azimuthSlider.getValue());
        cylinder.setPreferredSize(new Dimension(700, 600));
        updateCylinderFromSliders();

        // Reset button
        resetButton = new JButton("Reset View");
        resetButton.addActionListener(e -> resetView());

        // Status label
        statusLabel = new JLabel("Elevation: 0°, Azimuth: 0°");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Layout components
        setupControlPanels();
        
        panel.add(cylinder, BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.EAST);
        panel.add(statusLabel, BorderLayout.SOUTH);
        
        updateStatusLabel();
    }

    private JSlider createSlider(String name, int min, int max, int initial) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, initial);
        slider.setMajorTickSpacing((max - min) / 4);
        slider.setMinorTickSpacing((max - min) / 8);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new EnhancedListener());
        return slider;
    }

    private void setupControlPanels() {
        // Rotation controls
        JPanel rotationPanel = createControlGroup("Rotation Controls", 
            new JComponent[][]{{new JLabel("Elevation:"), elevSlider},
                              {new JLabel("Azimuth:"), azimuthSlider}});

        // Size controls
        JPanel sizePanel = createControlGroup("Size Controls",
            new JComponent[][]{{new JLabel("Height:"), heightSlider},
                              {new JLabel("Diameter:"), diameterSlider}});

        // Color controls
        JPanel color1Panel = createControlGroup("Dark Color (RGB)",
            new JComponent[][]{{new JLabel("Red:"), red1Slider},
                              {new JLabel("Green:"), green1Slider},
                              {new JLabel("Blue:"), blue1Slider}});

        JPanel color2Panel = createControlGroup("Light Color (RGB)",
            new JComponent[][]{{new JLabel("Red:"), red2Slider},
                              {new JLabel("Green:"), green2Slider},
                              {new JLabel("Blue:"), blue2Slider}});

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(resetButton);

        controlPanel.add(rotationPanel);
        controlPanel.add(sizePanel);
        controlPanel.add(color1Panel);
        controlPanel.add(color2Panel);
        controlPanel.add(buttonPanel);
    }

    private JPanel createControlGroup(String title, JComponent[][] components) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setLayout(new GridLayout(components.length, 2, 5, 5));
        
        for (JComponent[] row : components) {
            panel.add(row[0]);
            panel.add(row[1]);
        }
        
        return panel;
    }

    private void resetView() {
        elevSlider.setValue(0);
        azimuthSlider.setValue(0);
        heightSlider.setValue(400);
        diameterSlider.setValue(200);
        red1Slider.setValue(76);
        green1Slider.setValue(76);
        blue1Slider.setValue(200);
        red2Slider.setValue(178);
        green2Slider.setValue(178);
        blue2Slider.setValue(255);
        updateStatusLabel();
    }

    private void updateCylinderFromSliders() {
        cylinder.setElevation(elevSlider.getValue());
        cylinder.setAzimuth(azimuthSlider.getValue());
        
        // Update colors
        cylinder.setRed1(red1Slider.getValue());
        cylinder.setGreen1(green1Slider.getValue());
        cylinder.setBlue1(blue1Slider.getValue());
        cylinder.setRed2(red2Slider.getValue());
        cylinder.setGreen2(green2Slider.getValue());
        cylinder.setBlue2(blue2Slider.getValue());
        
        cylinder.repaint();
    }

    private void updateStatusLabel() {
        statusLabel.setText(String.format("Elevation: %d°, Azimuth: %d°, Height: %d, Diameter: %d", 
            elevSlider.getValue(), azimuthSlider.getValue(), 
            heightSlider.getValue(), diameterSlider.getValue()));
    }

    private class EnhancedListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            updateCylinderFromSliders();
            updateStatusLabel();
        }
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Fall back to default look and feel
            }
            new EnhancedGraphicalCylinder();
        });
    }
}
