package com.cylinder.graphics;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * A 3D cylinder visualization component that can be rotated and customized.
 * This class extends JPanel to provide interactive 3D rendering of a cylinder
 * with support for rotation, color customization, and size adjustment.
 * 
 * @author Cylinder Graphics Team
 * @version 1.0
 */
public class Cylinder extends JPanel {
    
    // Constants for angle limits
    private static final double MAX_ANGLE = Math.PI / 2.0001;
    private static final double ANGLE_THRESHOLD = Math.PI / 2.0;
    
    // Cylinder properties
    private double cylElev;    // cylinder axis elevation radians
    private double cylAzm;     // cylinder axis azimuth radians
    private double cylH = 400; // cylinder height in pixels
    private double cylD = 200; // cylinder diameter in pixels
    
    // Lighting properties
    private float c1 = 0.3f;   // minimum illumination brightness
    private float c2 = 0.7f;   // maximum illumination brightness
    
    // Color properties
    private float r1, g1, b1;  // dark color components
    private float r2, g2, b2;  // light color components
    
    // Position tracking for drag functionality
    private double cylX = 0;
    private double cylY = 0;

    /**
     * Constructs a new Cylinder with specified elevation and azimuth angles.
     * 
     * @param elev the elevation angle in degrees
     * @param azimuth the azimuth angle in degrees
     */
    public Cylinder(double elev, double azimuth) {
        setElevation(elev);
        setAzimuth(azimuth);
        
        // Initialize default colors (blue gradient)
        r1 = 0.3f; g1 = 0.3f; b1 = 0.8f; // Blue-ish dark
        r2 = 0.7f; g2 = 0.7f; b2 = 1.0f; // Blue-ish light
        
        // Set component properties to ensure visibility
        setOpaque(true);
        setBackground(Color.LIGHT_GRAY);
        
        System.out.println("Cylinder created with elevation: " + elev + ", azimuth: " + azimuth);
    }

    /**
     * Sets the elevation angle of the cylinder.
     * 
     * @param elev the elevation angle in degrees
     */
    public void setElevation(double elev) {
        cylElev = Math.toRadians(elev);
        if (Math.abs(cylElev) >= ANGLE_THRESHOLD) {
            cylElev = Math.signum(cylElev) * MAX_ANGLE;
        }
        repaint();
    }

    /**
     * Sets the azimuth angle of the cylinder.
     * 
     * @param azimuth the azimuth angle in degrees
     */
    public void setAzimuth(double azimuth) {
        cylAzm = Math.toRadians(azimuth);
        if (Math.abs(cylAzm) >= ANGLE_THRESHOLD) {
            cylAzm = Math.signum(cylAzm) * MAX_ANGLE;
        }
        repaint();
    }

    /**
     * Gets the current elevation angle.
     * 
     * @return the elevation angle in degrees
     */
    public double getElevation() {
        return Math.toDegrees(cylElev);
    }

    /**
     * Gets the current azimuth angle.
     * 
     * @return the azimuth angle in degrees
     */
    public double getAzimuth() {
        return Math.toDegrees(cylAzm);
    }

    /**
     * Sets the height of the cylinder.
     * 
     * @param height the height in pixels
     */
    public void setHeight(double height) {
        cylH = height;
        repaint();
    }

    /**
     * Sets the diameter of the cylinder.
     * 
     * @param diameter the diameter in pixels
     */
    public void setDiameter(double diameter) {
        cylD = diameter;
        repaint();
    }

    /**
     * Gets the current height of the cylinder.
     * 
     * @return the height in pixels
     */
    public double getCylinderHeight() {
        return cylH;
    }

    /**
     * Gets the current diameter of the cylinder.
     * 
     * @return the diameter in pixels
     */
    public double getDiameter() {
        return cylD;
    }

    // Position methods for drag functionality
    public void setCylX(double x) {
        this.cylX = x;
        repaint();
    }

    public void setCylY(double y) {
        this.cylY = y;
        repaint();
    }

    public double getCylX() {
        return cylX;
    }

    public double getCylY() {
        return cylY;
    }

    // Color setter methods
    public void setRed1(int red1) {
        r1 = Math.max(0, Math.min(255, red1)) / 255.0f;
        repaint();
    }

    public void setGreen1(int green1) {
        g1 = Math.max(0, Math.min(255, green1)) / 255.0f;
        repaint();
    }

    public void setBlue1(int blue1) {
        b1 = Math.max(0, Math.min(255, blue1)) / 255.0f;
        repaint();
    }

    public void setRed2(int red2) {
        r2 = Math.max(0, Math.min(255, red2)) / 255.0f;
        repaint();
    }

    public void setGreen2(int green2) {
        g2 = Math.max(0, Math.min(255, green2)) / 255.0f;
        repaint();
    }

    public void setBlue2(int blue2) {
        b2 = Math.max(0, Math.min(255, blue2)) / 255.0f;
        repaint();
    }

    // Color getter methods
    public int getRed1() {
        return Math.round(r1 * 255);
    }

    public int getGreen1() {
        return Math.round(g1 * 255);
    }

    public int getBlue1() {
        return Math.round(b1 * 255);
    }

    public int getRed2() {
        return Math.round(r2 * 255);
    }

    public int getGreen2() {
        return Math.round(g2 * 255);
    }

    public int getBlue2() {
        return Math.round(b2 * 255);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("paintComponent called - Component size: " + getWidth() + "x" + getHeight());
        
        Graphics2D g2d = (Graphics2D) g.create();
        
        // Enable antialiasing for smoother graphics
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        try {
            render3DCylinder(g2d);
        } finally {
            g2d.dispose();
        }
    }

    /**
     * Renders the 3D cylinder using mathematical transformations.
     * This method implements the core 3D rendering logic.
     * 
     * @param g2d the Graphics2D context to draw on
     */
    private void render3DCylinder(Graphics2D g2d) {
        int w = getWidth();
        int h = super.getHeight(); // Use JPanel's getHeight method for component height
        
        if (w <= 0 || h <= 0) return;
        
        // Center the cylinder in the component
        double centerX = w / 2.0 + cylX;
        double centerY = h / 2.0 + cylY;
        
        // Calculate 3D transformation matrices
        double cosElev = Math.cos(cylElev);
        double sinElev = Math.sin(cylElev);
        double cosAzm = Math.cos(cylAzm);
        double sinAzm = Math.sin(cylAzm);
        
        // Draw cylinder using mathematical 3D projection
        drawCylinderSurface(g2d, centerX, centerY, cosElev, sinElev, cosAzm, sinAzm);
    }

    /**
     * Draws the cylinder surface with proper 3D projection and shading.
     */
    private void drawCylinderSurface(Graphics2D g2d, double centerX, double centerY, 
                                   double cosElev, double sinElev, double cosAzm, double sinAzm) {
        // Implementation of 3D cylinder rendering with proper mathematical transformations
        // This is a simplified version - the full implementation would be more complex
        
        double radius = cylD / 2.0;
        double height = cylH;
        
        // Draw cylinder body
        for (int i = 0; i < 360; i += 2) {
            double angle = Math.toRadians(i);
            double x = radius * Math.cos(angle);
            double z = radius * Math.sin(angle);
            
            // Apply 3D transformations
            double x3d = x * cosAzm - z * sinAzm;
            double y3d = -height / 2;
            double z3d = x * sinAzm + z * cosAzm;
            
            // Project to 2D
            double x2d = centerX + x3d;
            double y2d = centerY + y3d * cosElev + z3d * sinElev;
            
            // Calculate lighting based on surface normal
            float brightness = calculateBrightness(x, z, cosAzm, sinAzm);
            Color color = interpolateColor(brightness);
            
            g2d.setColor(color);
            g2d.fillOval((int)(x2d - 1), (int)(y2d - height/2), 2, (int)height);
        }
    }

    /**
     * Calculates surface brightness based on lighting model.
     */
    private float calculateBrightness(double x, double z, double cosAzm, double sinAzm) {
        // Simple lighting calculation
        double normalX = x;
        double normalZ = z;
        double lightX = 1.0; // Light direction
        double lightZ = 0.0;
        
        double dot = normalX * lightX + normalZ * lightZ;
        return (float) (c1 + (c2 - c1) * Math.max(0, dot));
    }

    /**
     * Interpolates between dark and light colors based on brightness.
     */
    private Color interpolateColor(float brightness) {
        float r = r1 + (r2 - r1) * brightness;
        float g = g1 + (g2 - g1) * brightness;
        float b = b1 + (b2 - b1) * brightness;
        
        return new Color(
            Math.max(0, Math.min(1, r)),
            Math.max(0, Math.min(1, g)),
            Math.max(0, Math.min(1, b))
        );
    }
}
