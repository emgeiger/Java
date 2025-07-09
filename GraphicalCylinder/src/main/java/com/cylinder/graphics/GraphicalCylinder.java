package com.cylinder.graphics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.BorderLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Basic graphical cylinder application with interactive rotation controls.
 * Provides mouse drag capability and slider controls for viewing the cylinder
 * from different angles.
 * 
 * @author Cylinder Graphics Team
 * @version 1.0
 */
public class GraphicalCylinder extends JFrame {
    
    // GUI Components
    private JSlider elevSlider;
    private JSlider azimuthSlider;
    private JPanel panel;
    private JPanel sliderPanel;
    private Cylinder cylinder;
    private Point mouseDownPoint;

    /**
     * Constructs a new GraphicalCylinder application window.
     */
    public GraphicalCylinder() {
        super("Graphical Cylinder");
        initializeFrame();
        createContents();
        setupEventHandlers();
        finalizeFrame();
    }

    /**
     * Initializes basic frame properties.
     */
    private void initializeFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Creates and layouts all GUI components.
     */
    public void createContents() {
        panel = new JPanel(new BorderLayout());
        sliderPanel = new JPanel();

        // Initialize sliders with proper ranges and tick marks
        elevSlider = createSlider("Elevation", -90, 90, 0, 10);
        azimuthSlider = createSlider("Azimuth", -90, 90, 0, 10);

        // Create cylinder component
        cylinder = new Cylinder(elevSlider.getValue(), azimuthSlider.getValue());
        cylinder.setPreferredSize(new Dimension(600, 500));
        cylinder.setOpaque(true);
        cylinder.setBackground(Color.WHITE); // Set background to help with visibility
        
        System.out.println("GraphicalCylinder: Cylinder created with preferred size: " + cylinder.getPreferredSize());
        
        // Layout slider components
        layoutSliders();
        
        // Add components to main panel
        panel.add(cylinder, BorderLayout.CENTER);
        panel.add(sliderPanel, BorderLayout.SOUTH);
        
        add(panel);
    }

    /**
     * Creates a configured slider with specified parameters.
     * 
     * @param name the slider name for labeling
     * @param min minimum value
     * @param max maximum value
     * @param initial initial value
     * @param majorTick spacing for major tick marks
     * @return configured JSlider
     */
    private JSlider createSlider(String name, int min, int max, int initial, int majorTick) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, initial);
        slider.setMajorTickSpacing(majorTick);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new SliderListener());
        return slider;
    }

    /**
     * Layouts the slider components in an organized manner.
     */
    private void layoutSliders() {
        JPanel elevPanel = new JPanel();
        elevPanel.add(new JLabel("Elevation: "));
        elevPanel.add(elevSlider);
        
        JPanel azimuthPanel = new JPanel();
        azimuthPanel.add(new JLabel("Azimuth: "));
        azimuthPanel.add(azimuthSlider);
        
        sliderPanel.setLayout(new GridLayout(2, 1));
        sliderPanel.add(elevPanel);
        sliderPanel.add(azimuthPanel);
    }

    /**
     * Sets up mouse event handlers for drag-to-rotate functionality.
     */
    private void setupEventHandlers() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseDownPoint = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseDrag(e);
            }
        });
    }

    /**
     * Handles mouse drag events to rotate the cylinder.
     * 
     * @param e the mouse event
     */
    private void handleMouseDrag(MouseEvent e) {
        if (mouseDownPoint == null) return;
        
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
        
        // Update sliders (which will trigger cylinder updates)
        azimuthSlider.setValue((int) newAzimuth);
        elevSlider.setValue((int) newElevation);
        
        mouseDownPoint = currentPoint; // Update for smooth dragging
    }

    /**
     * Finalizes frame setup and makes it visible.
     */
    private void finalizeFrame() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Inner class to handle slider change events.
     */
    private class SliderListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            if (e.getSource() == elevSlider) {
                cylinder.setElevation(elevSlider.getValue());
            } else if (e.getSource() == azimuthSlider) {
                cylinder.setAzimuth(azimuthSlider.getValue());
            }
            panel.repaint();
        }
    }

    /**
     * Main method to launch the application.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Fall back to default look and feel
                System.err.println("Could not set system look and feel: " + e.getMessage());
            }
            new GraphicalCylinder();
        });
    }
}
