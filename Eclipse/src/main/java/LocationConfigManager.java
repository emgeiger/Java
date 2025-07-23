import java.io.*;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * LocationConfigManager - Handles location configuration persistence
 * 
 * This class manages loading and saving observer location settings
 * for the Swiss Ephemeris Lunar Phase Monitor. It provides methods
 * to read/write location.properties file and manage default locations.
 */
public class LocationConfigManager {
    
    private static final Logger LOGGER = Logger.getLogger(LocationConfigManager.class.getName());
    private static final String CONFIG_FILE = "location.properties";
    
    private Properties properties;
    
    /**
     * Load location configuration from file
     */
    public void loadConfiguration() {
        properties = new Properties();
        
        try {
            // Try to load existing configuration
            File configFile = new File(CONFIG_FILE);
            if (configFile.exists()) {
                try (FileInputStream fis = new FileInputStream(configFile)) {
                    properties.load(fis);
                    LOGGER.info("Location configuration loaded from " + CONFIG_FILE);
                }
            } else {
                // Create default configuration
                createDefaultConfiguration();
                LOGGER.info("Created default location configuration");
            }
            
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error loading location configuration: " + e.getMessage(), e);
            createDefaultConfiguration();
        }
    }
    
    /**
     * Save current configuration to file
     */
    public void saveConfiguration() {
        if (properties == null) {
            LOGGER.warning("No configuration to save");
            return;
        }
        
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            properties.store(fos, "Swiss Ephemeris Lunar Phase Monitor - Location Configuration\\n" +
                                 "Generated: " + new java.util.Date());
            LOGGER.info("Location configuration saved to " + CONFIG_FILE);
            
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving location configuration: " + e.getMessage(), e);
        }
    }
    
    /**
     * Set observer location and save to configuration
     */
    public void setObserverLocation(double latitude, double longitude, double elevation) {
        setObserverLocation(latitude, longitude, elevation, "", "");
    }
    
    /**
     * Set observer location with metadata and save to configuration
     */
    public void setObserverLocation(double latitude, double longitude, double elevation, 
                                  String name, String description) {
        if (properties == null) {
            loadConfiguration();
        }
        
        // Validate coordinates
        if (latitude < -90.0 || latitude > 90.0) {
            throw new IllegalArgumentException("Latitude must be between -90 and +90 degrees");
        }
        if (longitude < -180.0 || longitude > 180.0) {
            throw new IllegalArgumentException("Longitude must be between -180 and +180 degrees");
        }
        
        // Set location properties
        properties.setProperty("observer.latitude", String.valueOf(latitude));
        properties.setProperty("observer.longitude", String.valueOf(longitude));
        properties.setProperty("observer.elevation", String.valueOf(elevation));
        properties.setProperty("location.enabled", "true");
        properties.setProperty("last.modified", new java.util.Date().toString());
        
        if (name != null && !name.trim().isEmpty()) {
            properties.setProperty("location.name", name.trim());
        }
        if (description != null && !description.trim().isEmpty()) {
            properties.setProperty("location.description", description.trim());
        }
        
        // Auto-save if enabled
        if (Boolean.parseBoolean(properties.getProperty("save.location.on.change", "true"))) {
            saveConfiguration();
        }
        
        // Apply to MoonPhases
        MoonPhases.setObserverLocation(latitude, longitude, elevation);
        
        LOGGER.info(String.format("Observer location set: %.6f°, %.6f°, %.1fm (%s)", 
                   latitude, longitude, elevation, name));
    }
    
    /**
     * Clear observer location
     */
    public void clearObserverLocation() {
        if (properties == null) {
            loadConfiguration();
        }
        
        properties.setProperty("observer.latitude", "0.0");
        properties.setProperty("observer.longitude", "0.0");
        properties.setProperty("observer.elevation", "0.0");
        properties.setProperty("location.enabled", "false");
        properties.setProperty("location.name", "");
        properties.setProperty("location.description", "");
        properties.setProperty("last.modified", new java.util.Date().toString());
        
        // Auto-save if enabled
        if (Boolean.parseBoolean(properties.getProperty("save.location.on.change", "true"))) {
            saveConfiguration();
        }
        
        // Apply to MoonPhases
        MoonPhases.clearObserverLocation();
        
        LOGGER.info("Observer location cleared");
    }
    
    /**
     * Get current observer latitude
     */
    public double getLatitude() {
        if (properties == null) {
            loadConfiguration();
        }
        return Double.parseDouble(properties.getProperty("observer.latitude", "0.0"));
    }
    
    /**
     * Get current observer longitude
     */
    public double getLongitude() {
        if (properties == null) {
            loadConfiguration();
        }
        return Double.parseDouble(properties.getProperty("observer.longitude", "0.0"));
    }
    
    /**
     * Get current observer elevation
     */
    public double getElevation() {
        if (properties == null) {
            loadConfiguration();
        }
        return Double.parseDouble(properties.getProperty("observer.elevation", "0.0"));
    }
    
    /**
     * Check if location is currently enabled
     */
    public boolean isLocationEnabled() {
        if (properties == null) {
            loadConfiguration();
        }
        return Boolean.parseBoolean(properties.getProperty("location.enabled", "false"));
    }
    
    /**
     * Get location name
     */
    public String getLocationName() {
        if (properties == null) {
            loadConfiguration();
        }
        return properties.getProperty("location.name", "");
    }
    
    /**
     * Get location description
     */
    public String getLocationDescription() {
        if (properties == null) {
            loadConfiguration();
        }
        return properties.getProperty("location.description", "");
    }
    
    /**
     * Load a default location by name
     */
    public void loadDefaultLocation(String locationKey) {
        if (properties == null) {
            loadConfiguration();
        }
        
        String prefix = "default." + locationKey.toLowerCase() + ".";
        String latitude = properties.getProperty(prefix + "latitude");
        String longitude = properties.getProperty(prefix + "longitude");
        String elevation = properties.getProperty(prefix + "elevation");
        String name = properties.getProperty(prefix + "name");
        
        if (latitude != null && longitude != null && elevation != null) {
            setObserverLocation(
                Double.parseDouble(latitude),
                Double.parseDouble(longitude),
                Double.parseDouble(elevation),
                name != null ? name : locationKey,
                "Default location: " + locationKey
            );
        } else {
            throw new IllegalArgumentException("Default location not found: " + locationKey);
        }
    }
    
    /**
     * Get all available default location keys
     */
    public String[] getDefaultLocationKeys() {
        if (properties == null) {
            loadConfiguration();
        }
        
        return new String[]{"calgary", "newyork", "london", "sydney", "tokyo"};
    }
    
    /**
     * Initialize location on startup if enabled
     */
    public void initializeOnStartup() {
        if (properties == null) {
            loadConfiguration();
        }
        
        boolean autoLoad = Boolean.parseBoolean(properties.getProperty("auto.load.location.on.startup", "true"));
        if (autoLoad && isLocationEnabled()) {
            double lat = getLatitude();
            double lon = getLongitude();
            double elev = getElevation();
            
            // Apply to MoonPhases without saving (already saved)
            MoonPhases.setObserverLocation(lat, lon, elev);
            
            LOGGER.info(String.format("Restored observer location on startup: %.6f°, %.6f°, %.1fm (%s)", 
                       lat, lon, elev, getLocationName()));
        }
    }
    
    /**
     * Create default configuration file
     */
    private void createDefaultConfiguration() {
        properties = new Properties();
        
        // Default values
        properties.setProperty("observer.latitude", "0.0");
        properties.setProperty("observer.longitude", "0.0");
        properties.setProperty("observer.elevation", "0.0");
        properties.setProperty("location.enabled", "false");
        properties.setProperty("auto.load.location.on.startup", "true");
        properties.setProperty("validate.coordinates.on.input", "true");
        properties.setProperty("save.location.on.change", "true");
        properties.setProperty("use.atmospheric.refraction", "true");
        properties.setProperty("use.parallax.correction", "true");
        properties.setProperty("precision.decimal.places", "3");
        
        // Default locations
        properties.setProperty("default.calgary.latitude", "51.0447");
        properties.setProperty("default.calgary.longitude", "-114.0719");
        properties.setProperty("default.calgary.elevation", "1045.0");
        properties.setProperty("default.calgary.name", "Calgary, Alberta, Canada");
        
        properties.setProperty("default.newyork.latitude", "40.7128");
        properties.setProperty("default.newyork.longitude", "-74.0060");
        properties.setProperty("default.newyork.elevation", "10.0");
        properties.setProperty("default.newyork.name", "New York City, New York, USA");
        
        properties.setProperty("config.version", "1.0");
        properties.setProperty("last.modified", new java.util.Date().toString());
        
        saveConfiguration();
    }
    
    /**
     * Get formatted location status string
     */
    public String getLocationStatusString() {
        if (!isLocationEnabled()) {
            return "No location set (Geocentric calculations)";
        }
        
        String name = getLocationName();
        if (name != null && !name.trim().isEmpty()) {
            return String.format("%s (%.4f°, %.4f°, %.0fm) - Topocentric calculations", 
                               name.trim(), getLatitude(), getLongitude(), getElevation());
        } else {
            return String.format("Location: %.4f°, %.4f°, %.0fm - Topocentric calculations", 
                               getLatitude(), getLongitude(), getElevation());
        }
    }
}
