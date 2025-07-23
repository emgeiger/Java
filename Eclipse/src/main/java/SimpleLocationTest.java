/**
 * Simple test class to demonstrate topocentric location-based lunar tracking
 * for the desktop platform
 */
public class SimpleLocationTest {
    
    public static void main(String[] args) {
        System.out.println("=== Swiss Ephemeris Lunar Phase Monitor - Location Support Test ===");
        System.out.println("Testing comprehensive topocentric location-based tracking\n");
        
        // Test basic MoonPhases functionality first
        testBasicMoonPhases();
        
        // Test location-based calculations
        testLocationBasedCalculations();
        
        System.out.println("\n=== Location Support Test Complete ===");
    }
    
    private static void testBasicMoonPhases() {
        System.out.println("--- Testing Basic MoonPhases (Geocentric) ---");
        try {
            double currentPhase = MoonPhases.getCurrentLunarPhase();
            double illumination = MoonPhases.getLunarIllumination();
            String phaseName = MoonPhases.getCurrentPhaseName();
            double moonAge = MoonPhases.getMoonAge();
            
            System.out.printf("Current Phase: %.1f%% | Illumination: %.1f%% | %s%n", 
                            currentPhase, illumination, phaseName);
            System.out.printf("Moon Age: %.1f days%n", moonAge);
            
        } catch (Exception e) {
            System.err.println("Error in basic test: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testLocationBasedCalculations() {
        System.out.println("--- Testing Location-Based Topocentric Calculations ---");
        try {
            // Test Calgary, Alberta location
            double latitude = 51.0447;
            double longitude = -114.0719;
            double elevation = 1045.0;
            
            System.out.printf("Setting location: Calgary, AB (%.4f°, %.4f°, %.0fm)%n", 
                            latitude, longitude, elevation);
            
            MoonPhases.setObserverLocation(latitude, longitude, elevation);
            
            // Get topocentric calculations
            double topoPhase = MoonPhases.getCurrentLunarPhase();
            double topoIllumination = MoonPhases.getLunarIllumination();
            String topoPhaseName = MoonPhases.getCurrentPhaseName();
            
            System.out.printf("Topocentric Phase: %.1f%% | Illumination: %.1f%% | %s%n", 
                            topoPhase, topoIllumination, topoPhaseName);
            
            // Clear location and compare
            System.out.println("Clearing location...");
            MoonPhases.clearObserverLocation();
            
            double geoPhase = MoonPhases.getCurrentLunarPhase();
            double geoIllumination = MoonPhases.getLunarIllumination();
            
            System.out.printf("Geocentric Phase: %.1f%% | Illumination: %.1f%%s%n", 
                            geoPhase, geoIllumination);
            
            double phaseDiff = Math.abs(topoPhase - geoPhase);
            double illumDiff = Math.abs(topoIllumination - geoIllumination);
            
            System.out.printf("Difference: Phase %.3f%%, Illumination %.3f%%s%n", 
                            phaseDiff, illumDiff);
            
        } catch (Exception e) {
            System.err.println("Error in location test: " + e.getMessage());
        }
        System.out.println();
    }
}
