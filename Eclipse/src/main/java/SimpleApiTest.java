import java.util.Date;
import java.util.Calendar;

public class SimpleApiTest {
    public static void main(String[] args) {
        try {
            System.out.println("Simple Swiss Ephemeris Test");
            System.out.println("===========================");
            
            // Test current lunar phase
            System.out.println("Testing current lunar phase calculation...");
            double currentPhase = MoonPhases.getCurrentLunarPhase();
            double illumination = MoonPhases.getLunarIllumination();
            String phaseName = MoonPhases.getPhaseName(currentPhase);
            
            System.out.println("Current Lunar Phase: " + String.format("%.2f", currentPhase) + "%");
            System.out.println("Lunar Illumination: " + String.format("%.2f", illumination) + "%");
            System.out.println("Phase Name: " + phaseName);
            System.out.println();
            
            // Test historical date
            System.out.println("Testing historical date calculation...");
            Calendar cal = Calendar.getInstance();
            cal.set(2024, Calendar.JULY, 20); // July 20, 2024 (known Full Moon)
            Date testDate = cal.getTime();
            
            double historicalPhase = MoonPhases.getLunarPhaseForDate(testDate);
            String historicalPhaseName = MoonPhases.getPhaseName(historicalPhase);
            
            System.out.println("July 20, 2024 Lunar Phase: " + String.format("%.2f", historicalPhase) + "%");
            System.out.println("Historical Phase Name: " + historicalPhaseName);
            System.out.println();
            
            // Test JSON output
            System.out.println("Testing JSON output...");
            String jsonOutput = MoonPhases.getMoonPhasesAsString();
            if (jsonOutput != null) {
                System.out.println("JSON Response: " + jsonOutput);
            } else {
                System.out.println("Failed to get JSON response");
            }
            System.out.println();
            
            // Test multiple dates for phase progression
            System.out.println("Testing phase progression over time...");
            Calendar testCal = Calendar.getInstance();
            testCal.set(2024, Calendar.JUNE, 1); // Start of June 2024
            
            for (int i = 0; i < 30; i += 7) { // Every week for a month
                testCal.add(Calendar.DAY_OF_MONTH, (i == 0) ? 0 : 7);
                Date progressDate = testCal.getTime();
                double progressPhase = MoonPhases.getLunarPhaseForDate(progressDate);
                String progressName = MoonPhases.getPhaseName(progressPhase);
                
                System.out.printf("Date: %tF - Phase: %.1f%% (%s)%n", 
                    progressDate, progressPhase, progressName);
            }
            
            System.out.println();
            System.out.println("Swiss Ephemeris test completed successfully!");
            
        } catch (Exception e) {
            System.out.println("Exception: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Clean up any resources
            MoonPhases.cleanup();
        }
    }
}
