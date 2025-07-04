package BrainPlotter;

import javax.swing.SwingUtilities;

/**
 * Main application class for BrainPlotter
 */
public class BrainPlotterApp {
    
    /**
     * Main entry point for the application
     * 
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainForm mainForm = new MainForm();
            mainForm.setVisible(true);
        });
    }
}
