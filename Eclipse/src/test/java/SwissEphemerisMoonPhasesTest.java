import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;
import java.util.Calendar;

/**
 * Test class for Swiss Ephemeris-based MoonPhases functionality
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Swiss Ephemeris Moon Phases Tests")
public class SwissEphemerisMoonPhasesTest
{
    @Test
    @Order(1)
    @DisplayName("Test Swiss Ephemeris initialization")
    void testSwissEphemerisInitialization() {
        assertDoesNotThrow(() -> {
            double phase = MoonPhases.getCurrentLunarPhase();
            assertTrue(phase >= 0.0 && phase <= 100.0, 
                "Lunar phase should be between 0 and 100");
        }, "Swiss Ephemeris should initialize without errors");
    }

    @Test
    @Order(2)
    @DisplayName("Test current lunar phase calculation")
    void testCurrentLunarPhase() {
        double phase = MoonPhases.getCurrentLunarPhase();
        
        assertNotNull(phase, "Lunar phase should not be null");
        assertTrue(phase >= 0.0 && phase <= 100.0, 
            "Lunar phase should be between 0 and 100, got: " + phase);
        
        System.out.println("✅ Current lunar phase: " + String.format("%.2f", phase) + "%");
    }

    @Test
    @Order(3)
    @DisplayName("Test lunar illumination calculation")
    void testLunarIllumination() {
        double illumination = MoonPhases.getLunarIllumination();
        
        assertNotNull(illumination, "Lunar illumination should not be null");
        assertTrue(illumination >= 0.0 && illumination <= 100.0, 
            "Lunar illumination should be between 0 and 100, got: " + illumination);
        
        System.out.println("✅ Current lunar illumination: " + String.format("%.2f", illumination) + "%");
    }

    @ParameterizedTest
    @Order(4)
    @DisplayName("Test phase names for different phase values")
    @ValueSource(doubles = {0.0, 12.5, 25.0, 37.5, 50.0, 62.5, 75.0, 87.5, 100.0})
    void testPhaseNames(double phase) {
        String phaseName = MoonPhases.getPhaseName(phase);
        
        assertNotNull(phaseName, "Phase name should not be null");
        assertFalse(phaseName.trim().isEmpty(), "Phase name should not be empty");
        
        System.out.println("✅ Phase " + phase + "% = " + phaseName);
    }

    @Test
    @Order(5)
    @DisplayName("Test lunar phase for specific historical date")
    void testLunarPhaseForSpecificDate() {
        // Test for a known date - January 1, 2024
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.JANUARY, 1, 12, 0, 0);
        Date testDate = cal.getTime();
        
        double phase = MoonPhases.getLunarPhaseForDate(testDate);
        
        assertTrue(phase >= 0.0 && phase <= 100.0, 
            "Historical lunar phase should be between 0 and 100, got: " + phase);
        
        String phaseName = MoonPhases.getPhaseName(phase);
        System.out.println("✅ Lunar phase on Jan 1, 2024: " + String.format("%.2f", phase) + "% (" + phaseName + ")");
    }

    @Test
    @Order(6)
    @DisplayName("Test consistency between current phase and illumination")
    void testPhaseIlluminationConsistency() {
        double phase = MoonPhases.getCurrentLunarPhase();
        double illumination = MoonPhases.getLunarIllumination();
        
        // Around Full Moon (phase ~50), illumination should be high
        if (Math.abs(phase - 50.0) < 5.0) {
            assertTrue(illumination > 90.0, 
                "Illumination should be high during Full Moon, got: " + illumination);
        }
        
        // Around New Moon (phase ~0 or ~100), illumination should be low
        if (phase < 5.0 || phase > 95.0) {
            assertTrue(illumination < 10.0, 
                "Illumination should be low during New Moon, got: " + illumination);
        }
        
        System.out.println("✅ Phase/Illumination consistency check passed");
    }

    @Test
    @Order(7)
    @DisplayName("Test legacy getMoonPhases method")
    void testLegacyGetMoonPhases() {
        byte[] moonData = MoonPhases.getMoonPhases();
        
        assertNotNull(moonData, "Moon phases data should not be null");
        assertTrue(moonData.length > 0, "Moon phases data should not be empty");
        
        String jsonData = new String(moonData);
        assertTrue(jsonData.contains("phase"), "JSON should contain phase data");
        assertTrue(jsonData.contains("illumination"), "JSON should contain illumination data");
        assertTrue(jsonData.contains("name"), "JSON should contain phase name");
        
        System.out.println("✅ Legacy method JSON output: " + jsonData);
    }

    @Test
    @Order(8)
    @DisplayName("Test performance of Swiss Ephemeris calculations")
    @Timeout(5)
    void testCalculationPerformance() {
        long startTime = System.currentTimeMillis();
        
        // Perform multiple calculations to test performance
        for (int i = 0; i < 10; i++) {
            MoonPhases.getCurrentLunarPhase();
            MoonPhases.getLunarIllumination();
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        assertTrue(duration < 5000, 
            "10 calculations should complete within 5 seconds, took: " + duration + "ms");
        
        System.out.println("✅ Performance test: 10 calculations in " + duration + "ms");
    }

    @Test
    @Order(9)
    @DisplayName("Test date range calculations")
    void testDateRangeCalculations() {
        Calendar cal = Calendar.getInstance();
        
        // Test calculations for past, present, and future dates
        Date[] testDates = {
            new Date(cal.getTimeInMillis() - 365L * 24 * 60 * 60 * 1000), // 1 year ago
            new Date(), // Today
            new Date(cal.getTimeInMillis() + 365L * 24 * 60 * 60 * 1000)  // 1 year from now
        };
        
        String[] dateLabels = {"1 year ago", "Today", "1 year from now"};
        
        for (int i = 0; i < testDates.length; i++) {
            double phase = MoonPhases.getLunarPhaseForDate(testDates[i]);
            String phaseName = MoonPhases.getPhaseName(phase);
            
            assertTrue(phase >= 0.0 && phase <= 100.0, 
                "Phase for " + dateLabels[i] + " should be valid");
            
            System.out.println("✅ " + dateLabels[i] + ": " + 
                String.format("%.2f", phase) + "% (" + phaseName + ")");
        }
    }

    @AfterAll
    static void cleanup() {
        System.out.println("🧹 Cleaning up Swiss Ephemeris resources...");
        MoonPhases.cleanup();
        System.out.println("✅ Swiss Ephemeris tests completed successfully!");
    }
}
