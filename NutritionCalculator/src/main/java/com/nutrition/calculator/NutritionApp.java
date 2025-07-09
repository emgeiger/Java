package com.nutrition.calculator;

import com.nutrition.calculator.ui.NutritionCalculatorGUI;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Main entry point for the Nutrition Calculator application.
 * Launches the Swing GUI for the MVP version.
 */
public class NutritionApp {
    
    public static void main(String[] args) {
        // Launch GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                NutritionCalculatorGUI app = new NutritionCalculatorGUI();
                app.setVisible(true);
                
                System.out.println("🥗 Nutrition Calculator MVP started successfully!");
                System.out.println("Using in-memory data storage for this session.");
                
            } catch (Exception e) {
                System.err.println("Failed to start application: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
