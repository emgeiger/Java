package com.ovintiv.moonphases;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Android Activity demonstrating MoonPhases with disabled SeekBar and Location Support
 * Shows real-time lunar phase tracking with visual indicators and topocentric calculations
 * Simplified version for Java compatibility
 */
public class MoonPhaseActivity {
    
    private static final String TAG = "MoonPhaseActivity";
    
    // UI Components (simulated for Java environment)
    private static class SeekBar {
        private int value;
        private int min = 0;
        private int max = 100;
        public void setProgress(int progress) { this.value = progress; }
        public void setEnabled(boolean enabled) {}
        public void setMax(int max) { this.max = max; }
        public void setMin(int min) { this.min = min; }
        public int getProgress() { return value; }
    }
    
    private static class TextView {
        private String text;
        public void setText(String text) { 
            this.text = text;
            System.out.println("TextView: " + text);
        }
        public String getText() { return text; }
    }
    
    private static class Button {
        public void setOnClickListener(Runnable action) {}
        public void setEnabled(boolean enabled) {}
    }
    
    private SeekBar moonPhaseSeekBar;
    private TextView phasePercentageText;
    private TextView illuminationText;
    private TextView phaseNameText;
    private TextView moonAgeText;
    private TextView nextNewMoonText;
    private TextView nextFullMoonText;
    private TextView locationStatusText;
    
    private Button requestLocationButton;
    private Button clearLocationButton;
    
    private boolean isLocationEnabled = false;
    private double currentLatitude = 0.0;
    private double currentLongitude = 0.0;
    private double currentElevation = 0.0;
    
    /**
     * Initialize the moon phase activity with location support
     */
    public void onCreate() {
        // Initialize UI components
        initializeViews();
        
        // Set up disabled SeekBar for moon phase tracking
        setupMoonPhaseSeekBar();
        
        // Set up location controls
        setupLocationControls();
        
        // Initial data load
        updateLunarData();
        
        System.out.println("MoonPhaseActivity initialized with location support");
    }
    
    private void initializeViews() {
        moonPhaseSeekBar = new SeekBar();
        phasePercentageText = new TextView();
        illuminationText = new TextView();
        phaseNameText = new TextView();
        moonAgeText = new TextView();
        nextNewMoonText = new TextView();
        nextFullMoonText = new TextView();
        locationStatusText = new TextView();
        
        requestLocationButton = new Button();
        clearLocationButton = new Button();
        
        // Set initial location status
        locationStatusText.setText("Location: Not set (Geocentric calculations)");
    }
    
    /**
     * Set up location control buttons
     */
    private void setupLocationControls() {
        requestLocationButton.setOnClickListener(() -> {
            // Simulate location request (in real Android app, would request GPS)
            simulateLocationRequest();
        });
        
        clearLocationButton.setOnClickListener(() -> {
            clearLocation();
        });
    }
    
    /**
     * Simulate location request for demonstration purposes
     * In a real Android implementation, this would request GPS permissions and location
     */
    private void simulateLocationRequest() {
        // Default to Calgary, Alberta coordinates as example
        double latitude = 51.0447; 
        double longitude = -114.0719;
        double elevation = 1045.0; // meters
        
        setLocation(latitude, longitude, elevation);
        System.out.println("Location set to Calgary, AB (51.0447°, -114.0719°, 1045m)");
    }
    
    /**
     * Set observer location for topocentric calculations
     */
    public void setLocation(double latitude, double longitude, double elevation) {
        try {
            currentLatitude = latitude;
            currentLongitude = longitude;
            currentElevation = elevation;
            isLocationEnabled = true;
            
            // Set location in MoonPhasesAndroid
            MoonPhasesAndroid.setObserverLocation(latitude, longitude, elevation);
            
            // Update location status
            locationStatusText.setText(String.format(Locale.US,
                "Location: %.4f°, %.4f°, %.0fm (Topocentric calculations)",
                latitude, longitude, elevation));
            
            // Update lunar data with new location
            updateLunarData();
            
            System.out.printf("Android location set: %.6f°, %.6f°, %.0fm%n", 
                            latitude, longitude, elevation);
            
        } catch (Exception e) {
            System.err.println("Error setting location: " + e.getMessage());
            isLocationEnabled = false;
        }
    }
    
    /**
     * Clear observer location (revert to geocentric calculations)
     */
    public void clearLocation() {
        try {
            currentLatitude = 0.0;
            currentLongitude = 0.0;
            currentElevation = 0.0;
            isLocationEnabled = false;
            
            // Clear location in MoonPhasesAndroid
            MoonPhasesAndroid.clearObserverLocation();
            
            // Update location status
            locationStatusText.setText("Location: Not set (Geocentric calculations)");
            
            // Update lunar data
            updateLunarData();
            
            System.out.println("Android location cleared - using geocentric calculations");
            
        } catch (Exception e) {
            System.err.println("Error clearing location: " + e.getMessage());
        }
    }
    
    private void setupMoonPhaseSeekBar() {
        // Configure SeekBar for moon phase display
        moonPhaseSeekBar.setMin(MoonPhasesAndroid.SEEKBAR_MIN);
        moonPhaseSeekBar.setMax(MoonPhasesAndroid.SEEKBAR_MAX);
        
        // Disable user interaction - SeekBar only shows current phase
        moonPhaseSeekBar.setEnabled(false);
        
        // Set initial position
        moonPhaseSeekBar.setProgress(MoonPhasesAndroid.getSeekBarPosition());
        
        System.out.println("SeekBar configured: Min=" + MoonPhasesAndroid.SEEKBAR_MIN + 
                          ", Max=" + MoonPhasesAndroid.SEEKBAR_MAX + 
                          ", Current=" + MoonPhasesAndroid.getSeekBarPosition());
    }
    
    private void updateLunarData() {
        try {
            // Get current lunar data
            double currentPhase = MoonPhasesAndroid.getCurrentLunarPhase();
            double illumination = MoonPhasesAndroid.getLunarIllumination();
            String phaseName = MoonPhasesAndroid.getCurrentPhaseName();
            double moonAge = MoonPhasesAndroid.getMoonAge();
            
            // Update SeekBar position
            int seekBarPosition = MoonPhasesAndroid.getSeekBarPosition();
            moonPhaseSeekBar.setProgress(seekBarPosition);
            
            // Update text displays
            phasePercentageText.setText(String.format("Phase: %.1f%%", currentPhase));
            illuminationText.setText(String.format("Illumination: %.1f%%", illumination));
            phaseNameText.setText(String.format("Phase: %s", phaseName));
            moonAgeText.setText(String.format("Moon Age: %.1f days", moonAge));
            
            // Update next moon events
            java.util.Date nextNewMoon = MoonPhasesAndroid.getNextNewMoon();
            java.util.Date nextFullMoon = MoonPhasesAndroid.getNextFullMoon();
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            nextNewMoonText.setText(String.format("Next New Moon: %s", dateFormat.format(nextNewMoon)));
            nextFullMoonText.setText(String.format("Next Full Moon: %s", dateFormat.format(nextFullMoon)));
            
            System.out.printf("Lunar data updated - Phase: %.1f%%, Illumination: %.1f%%, Age: %.1f days%n",
                            currentPhase, illumination, moonAge);
            
        } catch (Exception e) {
            System.err.println("Error updating lunar data: " + e.getMessage());
            
            // Show error state
            phasePercentageText.setText("Phase: Error");
            illuminationText.setText("Illumination: Error");
            phaseNameText.setText("Phase: Unknown");
            moonAgeText.setText("Moon Age: Error");
            nextNewMoonText.setText("Next New Moon: Error");
            nextFullMoonText.setText("Next Full Moon: Error");
        }
    }
    
    /**
     * Demonstrate the Android moon phase activity
     */
    public static void demonstrate() {
        System.out.println("=== Android MoonPhase Activity Demo (Java Compatible) ===");
        
        MoonPhaseActivity activity = new MoonPhaseActivity();
        activity.onCreate();
        
        System.out.println("\n--- Setting Calgary Location ---");
        activity.simulateLocationRequest();
        
        System.out.println("\n--- Clearing Location ---");
        activity.clearLocation();
        
        System.out.println("\n--- Final Update ---");
        activity.updateLunarData();
        
        System.out.println("=== Android Demo Complete ===");
    }
    
    /**
     * Get current location status
     */
    public String getLocationStatus() {
        if (isLocationEnabled) {
            return String.format(Locale.US, "Location: %.4f°, %.4f°, %.0fm", 
                                currentLatitude, currentLongitude, currentElevation);
        } else {
            return "Location: Not set (Geocentric calculations)";
        }
    }
}
