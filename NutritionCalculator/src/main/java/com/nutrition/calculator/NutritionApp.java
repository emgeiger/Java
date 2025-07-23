package com.nutrition.calculator;

import com.nutrition.calculator.ui.NutritionCalculatorGUI;
import com.nutrition.calculator.service.JsonRecipeService;
import com.nutrition.calculator.service.RecipeService;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * NutritionApp - Main entry point for the desktop Nutrition Calculator application
 * 
 * This application provides comprehensive nutrition tracking and recipe management
 * capabilities with a modern Swing GUI interface. It integrates with Supabase
 * for cloud data synchronization and supports offline operation.
 * 
 * Features:
 * - Recipe management and nutrition calculation
 * - Food logging and daily nutrition tracking
 * - Integration with nutrition APIs
 * - Modern FlatLaf Look and Feel
 * - Corporate network compatibility
 * 
 * @author NutritionCalculator Development Team
 * @version 1.0
 */
public class NutritionApp {
    
    private static final Logger LOGGER = Logger.getLogger(NutritionApp.class.getName());
    private static final String APP_NAME = "Nutrition Calculator";
    private static final String APP_VERSION = "1.0";
    
    /**
     * Main entry point for the desktop nutrition calculator application.
     * 
     * @param args Command line arguments (currently unused)
     */
    public static void main(String[] args) {
        // Log application startup
        LOGGER.info("Starting " + APP_NAME + " v" + APP_VERSION);
        
        // Set system properties for better GUI experience
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        System.setProperty("sun.java2d.d3d", "false");
        
        // Configure corporate network settings if needed
        configureCorporateNetwork();
        
        // Set Look and Feel
        setupLookAndFeel();
        
        // Initialize the application on EDT
        SwingUtilities.invokeLater(() -> {
            try {
                createAndShowGUI();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Failed to initialize GUI", e);
                showErrorDialog("Application Startup Error", 
                    "Failed to start the nutrition calculator application.\n" +
                    "Error: " + e.getMessage());
                System.exit(1);
            }
        });
    }
    
    /**
     * Configure corporate network settings for API access.
     * Handles SSL certificates and proxy configuration for enterprise environments.
     */
    private static void configureCorporateNetwork() {
        try {
            // Check if running in corporate environment
            String proxyHost = System.getProperty("http.proxyHost");
            if (proxyHost != null && !proxyHost.isEmpty()) {
                LOGGER.info("Corporate proxy detected: " + proxyHost);
                
                // Configure SSL trust settings for corporate certificates
                System.setProperty("javax.net.ssl.trustStore", 
                    System.getProperty("java.home") + "/lib/security/cacerts");
                System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
                
                // Allow legacy SSL protocols if needed
                System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Failed to configure corporate network settings", e);
        }
    }
    
    /**
     * Set up the modern FlatLaf Look and Feel for better visual appearance.
     */
    private static void setupLookAndFeel() {
        try {
            // Use FlatLaf for modern appearance
            UIManager.setLookAndFeel(new FlatLightLaf());
            
            // Configure UI defaults for nutrition app theme
            UIManager.put("Button.arc", 5);
            UIManager.put("Component.arc", 5);
            UIManager.put("ProgressBar.arc", 5);
            UIManager.put("TextComponent.arc", 5);
            
            // Nutrition-themed colors
            UIManager.put("Component.focusColor", new Color(76, 175, 80)); // Green theme
            UIManager.put("Button.default.background", new Color(139, 195, 74));
            
            LOGGER.info("FlatLaf Look and Feel configured successfully");
            
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Failed to set FlatLaf Look and Feel", e);
            
            // Fallback to system Look and Feel
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
                LOGGER.info("Fallback to system Look and Feel");
            } catch (Exception ex) {
                LOGGER.log(Level.WARNING, "Failed to set system Look and Feel", ex);
            }
        }
    }
    
    /**
     * Create and display the main nutrition calculator GUI.
     * Initializes the recipe service and sets up the main window.
     */
    private static void createAndShowGUI() {
        try {
            // Initialize the recipe service
            RecipeService recipeService = new JsonRecipeService();
            
            // Create and configure the main GUI
            NutritionCalculatorGUI gui = new NutritionCalculatorGUI(recipeService);
            
            // Configure main frame
            JFrame frame = new JFrame(APP_NAME + " v" + APP_VERSION);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(gui);
            
            // Set application icon
            setApplicationIcon(frame);
            
            // Configure window properties
            frame.setMinimumSize(new Dimension(800, 600));
            frame.setPreferredSize(new Dimension(1200, 800));
            frame.pack();
            
            // Center on screen
            frame.setLocationRelativeTo(null);
            
            // Show the application
            frame.setVisible(true);
            
            LOGGER.info("Nutrition Calculator GUI initialized successfully");
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to create GUI", e);
            throw new RuntimeException("GUI initialization failed", e);
        }
    }
    
    /**
     * Set the application icon for the main frame.
     * 
     * @param frame The main application frame
     */
    private static void setApplicationIcon(JFrame frame) {
        try {
            // Try to load application icon
            var iconUrl = NutritionApp.class.getResource("/images/nutrition-icon.png");
            if (iconUrl != null) {
                ImageIcon icon = new ImageIcon(iconUrl);
                frame.setIconImage(icon.getImage());
            } else {
                LOGGER.warning("Application icon not found, using default");
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Failed to set application icon", e);
        }
    }
    
    /**
     * Display an error dialog to the user.
     * 
     * @param title The dialog title
     * @param message The error message to display
     */
    private static void showErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(
            null,
            message,
            title,
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Get application information for about dialog.
     * 
     * @return Application information string
     */
    public static String getApplicationInfo() {
        return APP_NAME + " v" + APP_VERSION + "\n" +
               "Comprehensive nutrition tracking and recipe management\n" +
               "Built with Java " + System.getProperty("java.version") + "\n" +
               "© 2025 NutritionCalculator Development Team";
    }
    
    /**
     * Open the help documentation in the default browser.
     */
    public static void openHelpDocumentation() {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(new URI("https://github.com/emgeiger/Java/blob/main/NutritionCalculator/README.md"));
                }
            }
        } catch (IOException | URISyntaxException e) {
            LOGGER.log(Level.WARNING, "Failed to open help documentation", e);
            JOptionPane.showMessageDialog(
                null,
                "Unable to open help documentation.\nPlease visit the project repository for documentation.",
                "Help",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}
