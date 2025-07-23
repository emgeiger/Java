/*************************************************************
 * EclipseSlider.java
 * Swiss Ephemeris Lunar Phase Monitor with Topocentric Location Support
 *
 * This program displays current lunar phase data using Swiss Ephemeris
 * calculations with location-based topocentric corrections. The interface 
 * shows the current moon phase, illumination percentage, and visual 
 * representation with a disabled JSlider that tracks the moon's progress 
 * through its monthly cycle. Includes location input for precise calculations.
 *************************************************************/

import javax.swing.*; // JFrame, JPanel, JLabel, SwingConstants, JSlider, JButton, JTextField
import java.awt.*;     // BorderLayout, Color, Graphics2D, Graphics, Font, GridBagLayout, GridBagConstraints
import java.awt.event.*; // ActionListener, WindowAdapter, WindowEvent
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EclipseSlider extends JFrame
{
    private MoonPhasePanel moonPanel;
    private JLabel phasePercentageLabel;
    private JLabel illuminationLabel;
    private JLabel phaseNameLabel;
    private JLabel lastUpdatedLabel;
    private JLabel locationStatusLabel;
    private JSlider moonProgressSlider;
    
    // Location input components
    private JTextField latitudeField;
    private JTextField longitudeField;
    private JTextField elevationField;
    private JButton setLocationButton;
    private JButton clearLocationButton;
    
    private static final int MOON_RADIUS = 80;
    
    public EclipseSlider() {
        super("Swiss Ephemeris Lunar Phase Monitor with Location Support");
        
        // Set up the main window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        // Create the main layout
        setLayout(new BorderLayout());
        
        // Create header panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Create location input panel
        JPanel locationPanel = createLocationPanel();
        add(locationPanel, BorderLayout.WEST);
        
        // Create center panel with moon visualization
        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);
        
        // Create data display panel
        JPanel dataPanel = createDataPanel();
        add(dataPanel, BorderLayout.SOUTH);
        
        // Load initial Swiss Ephemeris data
        updateLunarData();
        
        // Window closing handler
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Swiss Ephemeris Lunar Phase Monitor closed");
                System.exit(0);
            }
        });
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(20, 20, 40));
        headerPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        headerPanel.setPreferredSize(new Dimension(800, 60));
        
        JLabel titleLabel = new JLabel("🌙 Swiss Ephemeris Lunar Phase Monitor", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        
        headerPanel.add(titleLabel);
        return headerPanel;
    }
    
    /**
     * Create location input panel for topocentric calculations
     */
    private JPanel createLocationPanel() {
        JPanel locationPanel = new JPanel();
        locationPanel.setLayout(new GridBagLayout());
        locationPanel.setBackground(new Color(30, 30, 50));
        locationPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.CYAN), 
            "Observer Location", 
            0, 0, 
            new Font("Arial", Font.BOLD, 12), 
            Color.CYAN));
        locationPanel.setPreferredSize(new Dimension(200, 300));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Latitude input
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel latLabel = new JLabel("Latitude:");
        latLabel.setForeground(Color.WHITE);
        locationPanel.add(latLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        latitudeField = new JTextField(8);
        latitudeField.setToolTipText("Latitude in degrees (-90 to 90)");
        locationPanel.add(latitudeField, gbc);
        
        // Longitude input
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lonLabel = new JLabel("Longitude:");
        lonLabel.setForeground(Color.WHITE);
        locationPanel.add(lonLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        longitudeField = new JTextField(8);
        longitudeField.setToolTipText("Longitude in degrees (-180 to 180)");
        locationPanel.add(longitudeField, gbc);
        
        // Elevation input
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel elevLabel = new JLabel("Elevation (m):");
        elevLabel.setForeground(Color.WHITE);
        locationPanel.add(elevLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        elevationField = new JTextField(8);
        elevationField.setToolTipText("Elevation in meters above sea level");
        elevationField.setText("0");
        locationPanel.add(elevationField, gbc);
        
        // Set Location button
        gbc.gridx = 0; gbc.gridy = 6;
        setLocationButton = new JButton("Set Location");
        setLocationButton.setBackground(new Color(0, 100, 0));
        setLocationButton.setForeground(Color.WHITE);
        setLocationButton.addActionListener(e -> setObserverLocation());
        locationPanel.add(setLocationButton, gbc);
        
        // Clear Location button
        gbc.gridx = 0; gbc.gridy = 7;
        clearLocationButton = new JButton("Clear Location");
        clearLocationButton.setBackground(new Color(100, 0, 0));
        clearLocationButton.setForeground(Color.WHITE);
        clearLocationButton.addActionListener(e -> clearObserverLocation());
        locationPanel.add(clearLocationButton, gbc);
        
        // Location status
        gbc.gridx = 0; gbc.gridy = 8;
        locationStatusLabel = new JLabel("<html><center>No location set<br>(Geocentric calc)</center></html>");
        locationStatusLabel.setForeground(Color.YELLOW);
        locationStatusLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        locationPanel.add(locationStatusLabel, gbc);
        
        return locationPanel;
    }
    
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(10, 10, 30));
        
        // Create moon visualization panel
        moonPanel = new MoonPhasePanel();
        moonPanel.setPreferredSize(new Dimension(400, 400));
        
        // Create disabled slider that tracks moon progress
        moonProgressSlider = new JSlider(0, 100, 0);
        moonProgressSlider.setEnabled(false); // Disabled - cannot be moved by user
        moonProgressSlider.setMajorTickSpacing(25);
        moonProgressSlider.setMinorTickSpacing(5);
        moonProgressSlider.setPaintTicks(true);
        moonProgressSlider.setPaintLabels(true);
        moonProgressSlider.setBackground(new Color(10, 10, 30));
        moonProgressSlider.setForeground(Color.WHITE);
        
        // Add labels to slider
        java.util.Hashtable<Integer, JLabel> labelTable = new java.util.Hashtable<>();
        labelTable.put(0, new JLabel("New Moon"));
        labelTable.put(25, new JLabel("First Quarter"));
        labelTable.put(50, new JLabel("Full Moon"));
        labelTable.put(75, new JLabel("Last Quarter"));
        labelTable.put(100, new JLabel("New Moon"));
        
        // Set label colors to white
        for (JLabel label : labelTable.values()) {
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.PLAIN, 10));
        }
        
        moonProgressSlider.setLabelTable(labelTable);
        
        // Panel for slider with padding
        JPanel sliderPanel = new JPanel(new BorderLayout());
        sliderPanel.setBackground(new Color(10, 10, 30));
        sliderPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        sliderPanel.add(moonProgressSlider, BorderLayout.CENTER);
        
        // Add title for slider
        JLabel sliderTitle = new JLabel("Lunar Cycle Progress (Read-Only)", SwingConstants.CENTER);
        sliderTitle.setForeground(Color.WHITE);
        sliderTitle.setFont(new Font("Arial", Font.BOLD, 12));
        sliderPanel.add(sliderTitle, BorderLayout.NORTH);
        
        centerPanel.add(moonPanel, BorderLayout.CENTER);
        centerPanel.add(sliderPanel, BorderLayout.SOUTH);
        
        return centerPanel;
    }
    
    private JPanel createDataPanel() {
        JPanel dataPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        dataPanel.setBackground(new Color(30, 30, 50));
        dataPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // Create data labels
        phasePercentageLabel = createDataLabel("Phase: ---%");
        illuminationLabel = createDataLabel("Illumination: ---%");
        phaseNameLabel = createDataLabel("Phase Name: ---");
        lastUpdatedLabel = createDataLabel("Opened: ---");
        
        dataPanel.add(phasePercentageLabel);
        dataPanel.add(illuminationLabel);
        dataPanel.add(phaseNameLabel);
        dataPanel.add(lastUpdatedLabel);
        
        return dataPanel;
    }
    
    private JLabel createDataLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        return label;
    }
    
    private void updateLunarData() {
        try {
            // Get current lunar data from Swiss Ephemeris calculations
            double phase = MoonPhases.getCurrentLunarPhase();
            double illumination = MoonPhases.getLunarIllumination();
            String phaseName = MoonPhases.getPhaseName(phase);
            
            // Update GUI labels
            phasePercentageLabel.setText(String.format("Phase: %.1f%%", phase));
            illuminationLabel.setText(String.format("Illumination: %.1f%%", illumination));
            phaseNameLabel.setText("Phase Name: " + phaseName);
            
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
            lastUpdatedLabel.setText("Opened: " + formatter.format(new Date()));
            
            // Update moon visualization
            moonPanel.setPhase(phase);
            
            // Update disabled slider position to track moon progress
            moonProgressSlider.setValue((int) Math.round(phase));
            
            // Repaint components
            moonPanel.repaint();
            repaint();
            
            // Console output
            System.out.println("Swiss Ephemeris Lunar Phase Monitor opened");
            System.out.println(String.format("Current Phase: %.1f%% | Illumination: %.1f%% | %s", 
                              phase, illumination, phaseName));
            
        } catch (Exception e) {
            System.err.println("Error updating lunar data: " + e.getMessage());
            phasePercentageLabel.setText("Phase: Error");
            illuminationLabel.setText("Illumination: Error");
            phaseNameLabel.setText("Phase Name: Error");
            lastUpdatedLabel.setText("Error loading data");
        }
    }
    
    /**
     * Custom panel for drawing the moon phase visualization
     */
    class MoonPhasePanel extends JPanel {
        private double currentPhase = 0.0;
        
        public void setPhase(double phase) {
            this.currentPhase = phase;
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            // Enable antialiasing for smooth rendering
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Set background to space black
            setBackground(new Color(5, 5, 15));
            
            // Draw starfield background
            drawStarfield(g2d);
            
            // Calculate moon position (center of panel)
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            
            // Draw moon base (full circle)
            g2d.setColor(new Color(220, 220, 200)); // Moon color
            Ellipse2D moonBase = new Ellipse2D.Double(
                centerX - MOON_RADIUS, centerY - MOON_RADIUS, 
                MOON_RADIUS * 2, MOON_RADIUS * 2
            );
            g2d.fill(moonBase);
            
            // Draw moon surface details (craters)
            drawMoonSurface(g2d, centerX, centerY);
            
            // Draw phase shadow based on current phase percentage
            drawPhaseShading(g2d, centerX, centerY, currentPhase);
            
            // Draw moon outline
            g2d.setColor(new Color(180, 180, 160));
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(moonBase);
            
            // Draw phase percentage text
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            String phaseText = String.format("%.1f%%", currentPhase);
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(phaseText);
            g2d.drawString(phaseText, centerX - textWidth/2, centerY + MOON_RADIUS + 25);
        }
        
        private void drawStarfield(Graphics2D g2d) {
            g2d.setColor(Color.WHITE);
            // Draw small stars randomly
            for (int i = 0; i < 50; i++) {
                int x = (int) (Math.random() * getWidth());
                int y = (int) (Math.random() * getHeight());
                int size = (int) (Math.random() * 3) + 1;
                g2d.fillOval(x, y, size, size);
            }
        }
        
        private void drawMoonSurface(Graphics2D g2d, int centerX, int centerY) {
            g2d.setColor(new Color(180, 180, 160));
            
            // Draw some craters
            g2d.fillOval(centerX - 30, centerY - 20, 8, 8);
            g2d.fillOval(centerX + 15, centerY - 35, 6, 6);
            g2d.fillOval(centerX - 10, centerY + 25, 10, 10);
            g2d.fillOval(centerX + 25, centerY + 10, 5, 5);
        }
        
        private void drawPhaseShading(Graphics2D g2d, int centerX, int centerY, double phase) {
            // Draw shadow based on phase
            g2d.setColor(new Color(20, 20, 40, 180)); // Semi-transparent dark blue
            
            if (phase <= 50.0) {
                // Waxing phases (shadow on the left)
                double shadowWidth = (50.0 - phase) / 50.0 * MOON_RADIUS * 2;
                Arc2D shadow = new Arc2D.Double(
                    centerX - MOON_RADIUS, centerY - MOON_RADIUS,
                    MOON_RADIUS * 2, MOON_RADIUS * 2,
                    90, 180, Arc2D.PIE
                );
                g2d.fill(shadow);
                
                // Additional gradual shadow
                if (phase < 50.0) {
                    Ellipse2D gradualShadow = new Ellipse2D.Double(
                        centerX - shadowWidth/2, centerY - MOON_RADIUS,
                        shadowWidth, MOON_RADIUS * 2
                    );
                    g2d.fill(gradualShadow);
                }
            } else {
                // Waning phases (shadow on the right)
                double shadowWidth = (phase - 50.0) / 50.0 * MOON_RADIUS * 2;
                Arc2D shadow = new Arc2D.Double(
                    centerX - MOON_RADIUS, centerY - MOON_RADIUS,
                    MOON_RADIUS * 2, MOON_RADIUS * 2,
                    270, 180, Arc2D.PIE
                );
                g2d.fill(shadow);
                
                // Additional gradual shadow
                if (phase > 50.0) {
                    Ellipse2D gradualShadow = new Ellipse2D.Double(
                        centerX + MOON_RADIUS - shadowWidth/2, centerY - MOON_RADIUS,
                        shadowWidth, MOON_RADIUS * 2
                    );
                    g2d.fill(gradualShadow);
                }
            }
        }
    }
    
    /**
     * Set observer location for topocentric calculations
     */
    private void setObserverLocation() {
        try {
            double latitude = Double.parseDouble(latitudeField.getText().trim());
            double longitude = Double.parseDouble(longitudeField.getText().trim());
            double elevation = Double.parseDouble(elevationField.getText().trim());
            
            // Validate ranges
            if (latitude < -90 || latitude > 90) {
                throw new IllegalArgumentException("Latitude must be between -90 and 90 degrees");
            }
            if (longitude < -180 || longitude > 180) {
                throw new IllegalArgumentException("Longitude must be between -180 and 180 degrees");
            }
            if (elevation < -500 || elevation > 10000) {
                throw new IllegalArgumentException("Elevation must be between -500 and 10000 meters");
            }
            
            // Set location in MoonPhases
            MoonPhases.setObserverLocation(latitude, longitude, elevation);
            
            // Update status label
            locationStatusLabel.setText(String.format(
                "<html><center>Location Set:<br>%.3f°, %.3f°<br>%.0fm elevation<br>(Topocentric calc)</center></html>",
                latitude, longitude, elevation));
            locationStatusLabel.setForeground(Color.GREEN);
            
            // Update lunar data with new location
            updateLunarData();
            
            System.out.printf("Observer location set: %.6f°, %.6f°, %.0fm%n", 
                            latitude, longitude, elevation);
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Please enter valid numeric values for latitude, longitude, and elevation.",
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), 
                "Invalid Range", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error setting location: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Clear observer location (revert to geocentric calculations)
     */
    private void clearObserverLocation() {
        try {
            MoonPhases.clearObserverLocation();
            
            // Clear input fields
            latitudeField.setText("");
            longitudeField.setText("");
            elevationField.setText("0");
            
            // Update status label
            locationStatusLabel.setText("<html><center>No location set<br>(Geocentric calc)</center></html>");
            locationStatusLabel.setForeground(Color.YELLOW);
            
            // Update lunar data
            updateLunarData();
            
            System.out.println("Observer location cleared - using geocentric calculations");
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error clearing location: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        try {
            // Set look and feel
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set look and feel: " + e.getMessage());
        }
        
        SwingUtilities.invokeLater(() -> {
            new EclipseSlider().setVisible(true);
        });
    }
}