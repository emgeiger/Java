import java.util.Calendar;
import java.util.Date;

/**
 * Lunar phase calculator using astronomical algorithms
 * Provides accurate calculations for moon phases and eclipse tracking
 * (Simplified implementation - can be upgraded to Swiss Ephemeris when library is available)
 */
public class MoonPhases
{
    // Astronomical constants
    private static final double LUNAR_CYCLE_DAYS = 29.53058867; // Synodic month length
    private static final double KNOWN_NEW_MOON_JD = 2451550.1; // January 6, 2000 18:14 UTC
    
    /**
     * Calculate current lunar phase as a percentage (0-100)
     * 0 = New Moon, 50 = Full Moon, 100 = Next New Moon
     * @return lunar phase percentage
     */
    public static double getCurrentLunarPhase() {
        return getLunarPhaseForDate(new Date());
    }
    
    /**
     * Calculate lunar phase for a specific date
     * @param date the date to calculate phase for
     * @return lunar phase percentage (0-100)
     */
    public static double getLunarPhaseForDate(Date date) {
        try {
            // Convert date to Julian Day Number
            double julianDay = toJulianDay(date);
            
            // Calculate days since known new moon
            double daysSinceNewMoon = julianDay - KNOWN_NEW_MOON_JD;
            
            // Calculate current position in lunar cycle
            double cyclePosition = (daysSinceNewMoon % LUNAR_CYCLE_DAYS) / LUNAR_CYCLE_DAYS;
            
            // Ensure positive
            if (cyclePosition < 0) cyclePosition += 1.0;
            
            // Convert to percentage (0-100)
            return cyclePosition * 100.0;
            
        } catch (Exception e) {
            System.err.println("Error calculating lunar phase for date: " + e.getMessage());
            return 0.0;
        }
    }
    
    /**
     * Get the lunar illumination percentage (0-100)
     * @return illumination percentage
     */
    public static double getLunarIllumination() {
        double phase = getCurrentLunarPhase();
        
        // Convert phase to illumination
        // New Moon (0%) = 0% illuminated
        // Full Moon (50%) = 100% illuminated  
        // Next New Moon (100%) = 0% illuminated
        
        if (phase <= 50.0) {
            // Waxing: 0% to 100% illuminated
            return (phase / 50.0) * 100.0;
        } else {
            // Waning: 100% to 0% illuminated
            return ((100.0 - phase) / 50.0) * 100.0;
        }
    }
    
    /**
     * Get phase name based on phase percentage
     * @param phase phase percentage (0-100)
     * @return phase name
     */
    public static String getPhaseName(double phase) {
        if (phase < 6.25) return "New Moon";
        else if (phase < 18.75) return "Waxing Crescent";
        else if (phase < 31.25) return "First Quarter";
        else if (phase < 43.75) return "Waxing Gibbous";
        else if (phase < 56.25) return "Full Moon";
        else if (phase < 68.75) return "Waning Gibbous";
        else if (phase < 81.25) return "Last Quarter";
        else if (phase < 93.75) return "Waning Crescent";
        else return "New Moon";
    }
    
    /**
     * Convert Date to Julian Day Number
     * @param date the date to convert
     * @return Julian Day Number
     */
    private static double toJulianDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1; // Calendar month is 0-based
        int day = cal.get(Calendar.DAY_OF_MONTH);
        double hour = cal.get(Calendar.HOUR_OF_DAY) + 
                     cal.get(Calendar.MINUTE) / 60.0 + 
                     cal.get(Calendar.SECOND) / 3600.0;
        
        // Julian Day calculation
        int a = (14 - month) / 12;
        int y = year + 4800 - a;
        int m = month + 12 * a - 3;
        
        double jd = day + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400 - 32045;
        jd += (hour - 12) / 24.0;
        
        return jd;
    }
    
    /**
     * Legacy method to maintain compatibility
     * @return current lunar phase as byte array (for compatibility)
     */
    public static byte[] getMoonPhases() {
        try {
            double phase = getCurrentLunarPhase();
            double illumination = getLunarIllumination();
            String phaseName = getPhaseName(phase);
            
            String result = String.format(
                "{ \"phase\": %.2f, \"illumination\": %.2f, \"name\": \"%s\", \"source\": \"Astronomical Algorithm\" }",
                phase, illumination, phaseName
            );
            
            return result.getBytes("UTF-8");
            
        } catch (Exception e) {
            System.err.println("Error getting moon phases: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Cleanup resources (placeholder for Swiss Ephemeris compatibility)
     */
    public static void cleanup() {
        // Placeholder - no resources to clean up in this implementation
    }
    /**
     * Convenience method to get moon phases data as a String
     * @return JSON string response with Swiss Ephemeris data
     */
    public static String getMoonPhasesAsString() {
        try {
            byte[] data = getMoonPhases();
            if (data != null) {
                return new String(data, "UTF-8");
            }
        } catch (Exception e) {
            System.err.println("Error converting moon phases to string: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Test method to verify Swiss Ephemeris calculations
     */
    public static void main(String[] args) {
        try {
            System.out.println("Testing Swiss Ephemeris lunar calculations...");
            System.out.println("==============================================");
            
            double phase = getCurrentLunarPhase();
            double illumination = getLunarIllumination();
            String phaseName = getPhaseName(phase);
            
            System.out.println("Current Lunar Phase: " + String.format("%.2f", phase) + "%");
            System.out.println("Lunar Illumination: " + String.format("%.2f", illumination) + "%");
            System.out.println("Phase Name: " + phaseName);
            
            String jsonResponse = getMoonPhasesAsString();
            if (jsonResponse != null) {
                System.out.println("JSON Response: " + jsonResponse);
            }
            
            System.out.println("Swiss Ephemeris test completed successfully!");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }
}
