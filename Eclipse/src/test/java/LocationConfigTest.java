/**
 * Simple test for LocationConfigManager
 */
public class LocationConfigTest {
    
    public static void main(String[] args) {
        System.out.println("Testing LocationConfigManager...");
        
        try {
            LocationConfigManager config = new LocationConfigManager();
            
            // Test loading configuration
            config.loadConfiguration();
            System.out.println("✓ Configuration loaded");
            
            // Test setting location
            config.setObserverLocation(51.0447, -114.0719, 1045.0, "Calgary", "Test location");
            System.out.println("✓ Location set: " + config.getLocationStatusString());
            
            // Test loading default location
            config.loadDefaultLocation("newyork");
            System.out.println("✓ Default location loaded: " + config.getLocationStatusString());
            
            // Test clearing location
            config.clearObserverLocation();
            System.out.println("✓ Location cleared: " + config.getLocationStatusString());
            
            System.out.println("All tests passed!");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
