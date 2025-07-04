/*************************************************************
* EclipseSlider.java
* Swiss Ephemeris Lunar Phase Monitor
*
* This program displays current lunar phase data using Swiss Ephemeris
* calculations. The interface shows the current moon phase, illumination 
* percentage, and visual representation at the time of opening.
*************************************************************/

import javax.swing.*; // JFrame,JPanel,JLabel,SwingConstants
import java.awt.*;  // BorderLayout,Color,Graphics2D,Graphics,Font
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
  
  private static final int MOON_RADIUS = 80;
  
  // Current lunar data from Swiss Ephemeris
  private double currentLunarPhase = 0.0;
  private double currentIllumination = 0.0;
  private String currentPhaseName = "New Moon";

  //**************************************

  public EclipseSlider()
  {
    setSize(420, 480);
    setTitle("Swiss Ephemeris - Lunar Phase Monitor");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    createContents();
    updateLunarData(); // Initialize with current data
    setVisible(true);
  } // end EclipseSlider constructor

  //**************************************

  private void createContents()
  {
    setLayout(new BorderLayout(10, 10));
    
    // Create header panel with Swiss Ephemeris branding
    JPanel headerPanel = createHeaderPanel();
    add(headerPanel, BorderLayout.NORTH);
    
    // Create center panel for moon visualization
    moonPanel = new MoonPhasePanel();
    moonPanel.setBackground(new Color(0.05f, 0.05f, 0.15f)); // Deep space background
    moonPanel.setPreferredSize(new Dimension(300, 300));
    moonPanel.setBorder(BorderFactory.createLoweredBevelBorder());
    add(moonPanel, BorderLayout.CENTER);
    
    // Create bottom panel with lunar data display
    JPanel dataPanel = createDataPanel();
    add(dataPanel, BorderLayout.SOUTH);
  } // end createContents
  
  /**
   * Create the header panel with title and branding
   */
  private JPanel createHeaderPanel() {
    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setBackground(new Color(0.2f, 0.2f, 0.3f));
    headerPanel.setBorder(BorderFactory.createRaisedBevelBorder());
    
    JLabel titleLabel = new JLabel("🌙 Swiss Ephemeris Lunar Monitor", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
    titleLabel.setForeground(Color.WHITE);
    titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    headerPanel.add(titleLabel, BorderLayout.CENTER);
    return headerPanel;
  }
  
  /**
   * Create the data panel with lunar information
   */
  private JPanel createDataPanel() {
    JPanel dataPanel = new JPanel(new GridBagLayout());
    dataPanel.setBackground(new Color(0.9f, 0.9f, 0.95f));
    dataPanel.setBorder(BorderFactory.createTitledBorder("Current Lunar Data"));
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 10, 5, 10);
    
    // Phase percentage
    gbc.gridx = 0; gbc.gridy = 0;
    dataPanel.add(new JLabel("Lunar Phase:"), gbc);
    
    gbc.gridx = 1;
    phasePercentageLabel = new JLabel("0.00%", SwingConstants.LEFT);
    phasePercentageLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
    phasePercentageLabel.setForeground(new Color(0.2f, 0.4f, 0.8f));
    dataPanel.add(phasePercentageLabel, gbc);
    
    // Illumination percentage
    gbc.gridx = 0; gbc.gridy = 1;
    dataPanel.add(new JLabel("Illumination:"), gbc);
    
    gbc.gridx = 1;
    illuminationLabel = new JLabel("0.00%", SwingConstants.LEFT);
    illuminationLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
    illuminationLabel.setForeground(new Color(0.8f, 0.4f, 0.2f));
    dataPanel.add(illuminationLabel, gbc);
    
    // Phase name
    gbc.gridx = 0; gbc.gridy = 2;
    dataPanel.add(new JLabel("Phase Name:"), gbc);
    
    gbc.gridx = 1;
    phaseNameLabel = new JLabel("New Moon", SwingConstants.LEFT);
    phaseNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
    phaseNameLabel.setForeground(new Color(0.6f, 0.2f, 0.6f));
    dataPanel.add(phaseNameLabel, gbc);
    
    // Last updated timestamp
    gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
    lastUpdatedLabel = new JLabel("Opened: Loading...", SwingConstants.CENTER);
    lastUpdatedLabel.setFont(new Font("Arial", Font.ITALIC, 10));
    lastUpdatedLabel.setForeground(Color.GRAY);
    dataPanel.add(lastUpdatedLabel, gbc);
    
    return dataPanel;
  }

  //**************************************

  @SuppressWarnings("serial")
  private class MoonPhasePanel extends JPanel
  {
    @Override
    public void paintComponent(Graphics g)
    {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      
      // Enable antialiasing for smooth graphics
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      
      // Draw starfield background
      drawStarfield(g2);
      
      // Draw accurate moon phase based on Swiss Ephemeris data
      drawAccurateMoonPhase(g2);
      
      // Draw informational overlay
      drawInfoOverlay(g2);
    }
    
    /**
     * Draw a starfield background
     */
    private void drawStarfield(Graphics2D g2) {
      g2.setColor(Color.WHITE);
      int width = getWidth();
      int height = getHeight();
      
      // Draw random stars
      for (int i = 0; i < 50; i++) {
        int x = (int)(Math.random() * width);
        int y = (int)(Math.random() * height);
        int size = (Math.random() < 0.7) ? 1 : 2;
        g2.fillOval(x, y, size, size);
      }
    }
    
    /**
     * Draw the moon phase based on Swiss Ephemeris calculations
     */
    private void drawAccurateMoonPhase(Graphics2D g2) {
      int centerX = getWidth() / 2;
      int centerY = getHeight() / 2;
      
      // Draw full moon (light gray)
      g2.setColor(new Color(0.9f, 0.9f, 0.85f));
      g2.fillOval(centerX - MOON_RADIUS, centerY - MOON_RADIUS, 
                  2 * MOON_RADIUS, 2 * MOON_RADIUS);
      
      // Draw moon border
      g2.setColor(new Color(0.7f, 0.7f, 0.6f));
      g2.setStroke(new BasicStroke(2));
      g2.drawOval(centerX - MOON_RADIUS, centerY - MOON_RADIUS, 
                  2 * MOON_RADIUS, 2 * MOON_RADIUS);
      
      // Calculate shadow based on current lunar phase
      drawLunarShadow(g2, centerX, centerY);
      
      // Add moon surface details
      drawMoonSurface(g2, centerX, centerY);
    }
    
    /**
     * Draw the lunar shadow based on current phase percentage
     */
    private void drawLunarShadow(Graphics2D g2, int centerX, int centerY) {
      g2.setColor(new Color(0.1f, 0.1f, 0.15f, 0.8f)); // Semi-transparent shadow
      
      double phaseRadians = (currentLunarPhase / 100.0) * 2 * Math.PI;
      
      if (currentLunarPhase <= 50.0) {
        // Waxing phase: shadow from right
        double shadowWidth = (50.0 - currentLunarPhase) / 50.0 * (2 * MOON_RADIUS);
        int shadowX = (int)(centerX + MOON_RADIUS - shadowWidth);
        
        g2.fillArc(centerX - MOON_RADIUS, centerY - MOON_RADIUS,
                   2 * MOON_RADIUS, 2 * MOON_RADIUS,
                   270, 180);
        
        // Add curved shadow edge
        if (currentLunarPhase > 0 && currentLunarPhase < 50) {
          int arcWidth = (int)(shadowWidth * 2);
          g2.fillOval(shadowX - arcWidth/2, centerY - MOON_RADIUS,
                      arcWidth, 2 * MOON_RADIUS);
        }
      } else {
        // Waning phase: shadow from left  
        double shadowWidth = (currentLunarPhase - 50.0) / 50.0 * (2 * MOON_RADIUS);
        int shadowX = (int)(centerX - MOON_RADIUS);
        
        g2.fillArc(centerX - MOON_RADIUS, centerY - MOON_RADIUS,
                   2 * MOON_RADIUS, 2 * MOON_RADIUS,
                   90, 180);
        
        // Add curved shadow edge
        if (currentLunarPhase > 50 && currentLunarPhase < 100) {
          int arcWidth = (int)(shadowWidth * 2);
          g2.fillOval(shadowX + (int)shadowWidth - arcWidth/2, centerY - MOON_RADIUS,
                      arcWidth, 2 * MOON_RADIUS);
        }
      }
    }
    
    /**
     * Draw moon surface details (craters, etc.)
     */
    private void drawMoonSurface(Graphics2D g2, int centerX, int centerY) {
      g2.setColor(new Color(0.6f, 0.6f, 0.5f, 0.3f));
      
      // Draw a few "craters"
      g2.fillOval(centerX - 20, centerY - 30, 8, 8);
      g2.fillOval(centerX + 15, centerY - 10, 12, 12);
      g2.fillOval(centerX - 10, centerY + 20, 6, 6);
      g2.fillOval(centerX + 25, centerY + 15, 4, 4);
    }
    
    /**
     * Draw informational overlay
     */
    private void drawInfoOverlay(Graphics2D g2) {
      g2.setColor(new Color(1.0f, 1.0f, 1.0f, 0.9f));
      g2.setFont(new Font("Arial", Font.BOLD, 12));
      
      String phaseText = String.format("%.1f%%", currentLunarPhase);
      FontMetrics fm = g2.getFontMetrics();
      int textWidth = fm.stringWidth(phaseText);
      
      // Draw phase percentage in bottom right
      g2.drawString(phaseText, getWidth() - textWidth - 10, getHeight() - 10);
    }
  }

  //**************************************
  
  /**
   * Update lunar data from Swiss Ephemeris (called once on startup)
   */
  private void updateLunarData() {
    try {
      // Get current lunar data from Swiss Ephemeris
      currentLunarPhase = MoonPhases.getCurrentLunarPhase();
      currentIllumination = MoonPhases.getLunarIllumination();
      currentPhaseName = MoonPhases.getPhaseName(currentLunarPhase);
      
      // Update GUI labels
      SwingUtilities.invokeLater(() -> {
        phasePercentageLabel.setText(String.format("%.2f%%", currentLunarPhase));
        illuminationLabel.setText(String.format("%.2f%%", currentIllumination));
        phaseNameLabel.setText(currentPhaseName);
        
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
        lastUpdatedLabel.setText("Opened: " + sdf.format(new Date()));
        
        // Repaint moon display
        moonPanel.repaint();
      });
      
      System.out.println("Swiss Ephemeris Data: Phase=" + 
                        String.format("%.2f%%", currentLunarPhase) + 
                        ", Illumination=" + String.format("%.2f%%", currentIllumination) + 
                        ", Name=" + currentPhaseName);
                        
    } catch (Exception e) {
      System.err.println("Error loading lunar data: " + e.getMessage());
      SwingUtilities.invokeLater(() -> {
        phasePercentageLabel.setText("Error");
        illuminationLabel.setText("Error");
        phaseNameLabel.setText("Swiss Ephemeris Error");
        lastUpdatedLabel.setText("Load Failed: " + new Date());
      });
    }
  }
  //**************************************

  public static void main(String[] args)
  {
    // Set look and feel to system default
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      System.err.println("Could not set system look and feel: " + e.getMessage());
    }
    
    // Create and display the Swiss Ephemeris monitor
    SwingUtilities.invokeLater(() -> {
      new EclipseSlider();
      System.out.println("Swiss Ephemeris Lunar Phase Monitor opened.");
      System.out.println("Displaying current lunar phase data from Swiss Ephemeris calculations.");
    });
  }
} // end class EclipseSlider