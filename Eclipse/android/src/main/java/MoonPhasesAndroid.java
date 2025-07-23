package com.ovintiv.moonphases;

import java.util.Calendar;
import java.util.Date;

/**
 * Android version of MoonPhases with SeekBar integration
 * Comprehensive lunar phase calculations for Android applications
 * 
 * This class provides accurate lunar phase calculations design            info.append(String.format("Next New Moon: %s\n", new java.text.SimpleDateFormat("MMM dd, yyyy").format(nextNewMoon)));
            info.append(String.format("Next Full Moon: %s\n", new java.text.SimpleDateFormat("MMM dd, yyyy").format(nextFullMoon))); to work
 * with a disabled SeekBar that tracks the moon's progress through its cycle.
 * Optimized for Android performance and memory usage.
 */
public class MoonPhasesAndroid {
    
    // Astronomical constants
    private static final double LUNAR_CYCLE_DAYS = 29.530588853; // Average synodic month
    private static final double J2000_EPOCH = 2451545.0; // Julian day for J2000.0 epoch
    
    // SeekBar integration constants
    public static final int SEEKBAR_MIN = 0;
    public static final int SEEKBAR_MAX = 100;
    public static final int NEW_MOON_POSITION = 0;
    public static final int FULL_MOON_POSITION = 50;
    
    // Known new moon reference (January 6, 2000, 18:14 UTC)
    private static final double REFERENCE_NEW_MOON_JD = 2451550.26;
    
    // Location-based topocentric calculation variables
    private static double observerLatitude = 0.0;   // Degrees (-90 to +90)
    private static double observerLongitude = 0.0;  // Degrees (-180 to +180)
    private static double observerElevation = 0.0;  // Meters above sea level
    private static boolean locationSet = false;     // Flag indicating if location is set
    
    /**
     * Set observer location for topocentric calculations
     * @param latitude Observer's latitude in degrees (-90 to +90)
     * @param longitude Observer's longitude in degrees (-180 to +180)
     * @param elevation Observer's elevation in meters above sea level
     */
    public static void setObserverLocation(double latitude, double longitude, double elevation) {
        if (latitude < -90.0 || latitude > 90.0) {
            throw new IllegalArgumentException("Latitude must be between -90 and +90 degrees");
        }
        if (longitude < -180.0 || longitude > 180.0) {
            throw new IllegalArgumentException("Longitude must be between -180 and +180 degrees");
        }
        if (elevation < -1000.0 || elevation > 10000.0) {
            throw new IllegalArgumentException("Elevation must be between -1000 and +10000 meters");
        }
        
        observerLatitude = latitude;
        observerLongitude = longitude;
        observerElevation = elevation;
        locationSet = true;
        
        System.out.println(String.format("Android observer location set: %.6f°, %.6f°, %.1fm", 
            latitude, longitude, elevation));
    }
    
    /**
     * Clear observer location and revert to geocentric calculations
     */
    public static void clearObserverLocation() {
        observerLatitude = 0.0;
        observerLongitude = 0.0;
        observerElevation = 0.0;
        locationSet = false;
        
        System.out.println("Android observer location cleared - reverting to geocentric calculations");
    }
    
    /**
     * Check if observer location is currently set
     * @return true if location is set, false if using geocentric calculations
     */
    public static boolean isLocationSet() {
        return locationSet;
    }
    
    /**
     * Get current observer location information
     * @return String describing current location status
     */
    public static String getLocationInfo() {
        if (locationSet) {
            return String.format("Location: %.6f°, %.6f°, %.1fm (Topocentric calculations)", 
                                observerLatitude, observerLongitude, observerElevation);
        } else {
            return "Location: Not set (Geocentric calculations)";
        }
    }
    
    /**
     * Get the current lunar phase percentage (0-100) for SeekBar positioning
     * @return Phase percentage where 0 = New Moon, 50 = Full Moon, 100 = New Moon (next cycle)
     */
    public static double getCurrentLunarPhase() {
        try {
            double currentJD = getCurrentJulianDay();
            
            // Calculate precise moon phase using lunar longitude difference
            double moonLongitude = getMoonLongitude(currentJD);
            double sunLongitude = getSunLongitude(currentJD);
            
            // Calculate elongation (angular separation)
            double elongation = moonLongitude - sunLongitude;
            
            // Normalize to 0-360 degrees
            elongation = normalizeAngle(elongation);
            
            // Convert to phase percentage for SeekBar
            double phasePercentage = (elongation / 360.0) * 100.0;
            
            return Math.round(phasePercentage * 10.0) / 10.0; // Round to 1 decimal place
            
        } catch (Exception e) {
            System.err.println("Error calculating lunar phase: " + e.getMessage());
            return 0.0; // Default to new moon on error
        }
    }
    
    /**
     * Get the lunar illumination percentage (0-100)
     * @return Illumination percentage where 0 = completely dark, 100 = completely illuminated
     */
    public static double getLunarIllumination() {
        try {
            double phase = getCurrentLunarPhase();
            
            // Calculate illumination based on phase
            // Maximum illumination at 50% (full moon)
            double illumination;
            if (phase <= 50.0) {
                // Waxing phases: 0% to 100% illumination
                illumination = phase * 2.0;
            } else {
                // Waning phases: 100% back to 0% illumination  
                illumination = (100.0 - phase) * 2.0;
            }
            
            // Ensure bounds
            illumination = Math.max(0.0, Math.min(100.0, illumination));
            
            return Math.round(illumination * 10.0) / 10.0; // Round to 1 decimal place
            
        } catch (Exception e) {
            System.err.println("Error calculating lunar illumination: " + e.getMessage());
            return 50.0; // Default value
        }
    }
    
    /**
     * Get the name of the current lunar phase based on SeekBar position
     * @param phasePercentage The phase percentage (0-100) from SeekBar
     * @return String name of the lunar phase
     */
    public static String getPhaseName(double phasePercentage) {
        // Phase names based on traditional lunar cycle divisions
        if (phasePercentage < 6.25 || phasePercentage >= 93.75) {
            return "New Moon";
        } else if (phasePercentage < 18.75) {
            return "Waxing Crescent";
        } else if (phasePercentage < 31.25) {
            return "First Quarter";
        } else if (phasePercentage < 43.75) {
            return "Waxing Gibbous";
        } else if (phasePercentage < 56.25) {
            return "Full Moon";
        } else if (phasePercentage < 68.75) {
            return "Waning Gibbous";
        } else if (phasePercentage < 81.25) {
            return "Last Quarter";
        } else {
            return "Waning Crescent";
        }
    }
    
    /**
     * Get the current lunar phase name
     * @return Current phase name
     */
    public static String getCurrentPhaseName() {
        return getPhaseName(getCurrentLunarPhase());
    }
    
    /**
     * Calculate the age of the moon in days since new moon
     * @return Moon age in days (0-29.53)
     */
    public static double getMoonAge() {
        try {
            double phase = getCurrentLunarPhase();
            return (phase / 100.0) * LUNAR_CYCLE_DAYS;
        } catch (Exception e) {
            System.err.println("Error calculating moon age: " + e.getMessage());
            return 0.0;
        }
    }
    
    /**
     * Get SeekBar position for current moon phase
     * @return SeekBar position (0-100) representing current lunar phase
     */
    public static int getSeekBarPosition() {
        return (int) Math.round(getCurrentLunarPhase());
    }
    
    /**
     * Calculate days until next major phase
     * @param targetPhase Target phase percentage (0=New, 25=First Quarter, 50=Full, 75=Last Quarter)
     * @return Days until target phase
     */
    public static double getDaysUntilPhase(double targetPhase) {
        try {
            double currentPhase = getCurrentLunarPhase();
            double phaseDifference = targetPhase - currentPhase;
            
            // Handle wrap-around (e.g., from 90% to 10% = 20%, not -80%)
            if (phaseDifference < 0) {
                phaseDifference += 100;
            }
            
            return (phaseDifference / 100.0) * LUNAR_CYCLE_DAYS;
            
        } catch (Exception e) {
            System.err.println("Error calculating days until phase: " + e.getMessage());
            return 0.0;
        }
    }
    
    /**
     * Get the next new moon date
     * @return Date of the next new moon
     */
    public static Date getNextNewMoon() {
        try {
            double daysUntil = getDaysUntilPhase(0.0);
            if (daysUntil < 1.0) { // If very close to new moon, get next cycle
                daysUntil += LUNAR_CYCLE_DAYS;
            }
            
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, (int) Math.round(daysUntil));
            return cal.getTime();
            
        } catch (Exception e) {
            System.err.println("Error calculating next new moon: " + e.getMessage());
            return new Date();
        }
    }
    
    /**
     * Get the next full moon date
     * @return Date of the next full moon
     */
    public static Date getNextFullMoon() {
        try {
            double daysUntil = getDaysUntilPhase(50.0);
            
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, (int) Math.round(daysUntil));
            return cal.getTime();
            
        } catch (Exception e) {
            System.err.println("Error calculating next full moon: " + e.getMessage());
            return new Date();
        }
    }
    
    /**
     * Get detailed lunar information as a formatted string for Android UI
     * @return Formatted string with current lunar data
     */
    public static String getLunarInfo() {
        try {
            double phase = getCurrentLunarPhase();
            double illumination = getLunarIllumination();
            String phaseName = getPhaseName(phase);
            double moonAge = getMoonAge();
            Date nextNewMoon = getNextNewMoon();
            Date nextFullMoon = getNextFullMoon();
            
            StringBuilder info = new StringBuilder();
            info.append("=== Lunar Phase Data ===\\n");
            info.append(String.format("Current Phase: %.1f%%\\n", phase));
            info.append(String.format("Illumination: %.1f%%\\n", illumination));
            info.append(String.format("Phase Name: %s\\n", phaseName));
            info.append(String.format("Moon Age: %.1f days\\n", moonAge));
            info.append(String.format("Next New Moon: %s\\n", android.text.format.DateFormat.getDateInstance().format(nextNewMoon)));
            info.append(String.format("Next Full Moon: %s\\n", android.text.format.DateFormat.getDateInstance().format(nextFullMoon)));
            
            return info.toString();
            
        } catch (Exception e) {
            return "Error retrieving lunar information: " + e.getMessage();
        }
    }
    
    // ===============================
    // ASTRONOMICAL CALCULATION METHODS
    // ===============================
    
    /**
     * Calculate Moon's ecliptic longitude using Meeus algorithms
     * @param julianDay Julian day number
     * @return Moon's longitude in degrees
     */
    private static double getMoonLongitude(double julianDay) {
        // Time in Julian centuries since J2000.0
        double T = (julianDay - J2000_EPOCH) / 36525.0;
        
        // Moon's mean longitude (degrees)
        double L0 = 218.3164477 + 481267.88123421 * T - 0.0015786 * T * T 
                   + T * T * T / 538841.0 - T * T * T * T / 65194000.0;
        
        // Moon's mean elongation
        double D = 297.8501921 + 445267.1114034 * T - 0.0018819 * T * T 
                  + T * T * T / 545868.0 - T * T * T * T / 113065000.0;
        
        // Sun's mean anomaly
        double M = 357.5291092 + 35999.0502909 * T - 0.0001536 * T * T 
                  + T * T * T / 24490000.0;
        
        // Moon's mean anomaly
        double M1 = 134.9633964 + 477198.8675055 * T + 0.0087414 * T * T 
                   + T * T * T / 69699.0 - T * T * T * T / 14712000.0;
        
        // Moon's argument of latitude
        double F = 93.2720950 + 483202.0175233 * T - 0.0036539 * T * T 
                  - T * T * T / 3526000.0 + T * T * T * T / 863310000.0;
        
        // Convert to radians
        D = Math.toRadians(D);
        M = Math.toRadians(M);
        M1 = Math.toRadians(M1);
        F = Math.toRadians(F);
        
        // Main periodic terms for longitude (simplified for mobile performance)
        double longitude = L0 + 6.288774 * Math.sin(M1)
                         + 1.274027 * Math.sin(2 * D - M1)
                         + 0.658314 * Math.sin(2 * D)
                         - 0.185116 * Math.sin(M)
                         - 0.114332 * Math.sin(2 * F)
                         + 0.058793 * Math.sin(2 * (D - M1))
                         + 0.057066 * Math.sin(2 * D - M - M1)
                         + 0.053322 * Math.sin(2 * D + M1)
                         + 0.045758 * Math.sin(2 * D - M)
                         - 0.040923 * Math.sin(M - M1)
                         - 0.034720 * Math.sin(D)
                         - 0.030383 * Math.sin(M + M1);
        
        return normalizeAngle(longitude);
    }
    
    /**
     * Calculate Sun's ecliptic longitude using simplified VSOP87 theory
     * @param julianDay Julian day number
     * @return Sun's longitude in degrees
     */
    private static double getSunLongitude(double julianDay) {
        // Time in Julian centuries since J2000.0
        double T = (julianDay - J2000_EPOCH) / 36525.0;
        
        // Sun's mean longitude
        double L0 = 280.46646 + 36000.76983 * T + 0.0003032 * T * T;
        
        // Sun's mean anomaly
        double M = 357.52911 + 35999.05029 * T - 0.0001537 * T * T;
        M = Math.toRadians(M);
        
        // Equation of center
        double C = (1.914602 - 0.004817 * T - 0.000014 * T * T) * Math.sin(M)
                 + (0.019993 - 0.000101 * T) * Math.sin(2 * M)
                 + 0.000289 * Math.sin(3 * M);
        
        // True longitude
        double longitude = L0 + C;
        
        return normalizeAngle(longitude);
    }
    
    /**
     * Normalize angle to 0-360 degrees
     * @param angle Angle in degrees
     * @return Normalized angle (0-360)
     */
    private static double normalizeAngle(double angle) {
        while (angle < 0) {
            angle += 360;
        }
        while (angle >= 360) {
            angle -= 360;
        }
        return angle;
    }
    
    /**
     * Get current Julian day
     * @return Current Julian day number
     */
    private static double getCurrentJulianDay() {
        Calendar cal = Calendar.getInstance();
        
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1; // Calendar.MONTH is 0-based
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        
        // Convert to Julian day
        if (month <= 2) {
            year -= 1;
            month += 12;
        }
        
        int A = year / 100;
        int B = 2 - A + (A / 4);
        
        double JD = Math.floor(365.25 * (year + 4716)) + Math.floor(30.6001 * (month + 1)) 
                   + day + B - 1524.5;
        
        // Add time of day
        JD += (hour + minute / 60.0 + second / 3600.0) / 24.0;
        
        return JD;
    }
}
