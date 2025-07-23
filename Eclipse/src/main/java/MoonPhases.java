import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Comprehensive lunar phase calculations with JSlider integration
 * Desktop version using precise astronomical algorithms with location-based tracking
 * 
 * This class provides accurate lunar phase calculations designed to work
 * with a disabled JSlider that tracks the moon's progress through its cycle.
 * Calculations are based on astronomical algorithms from "Astronomical Algorithms" 
 * by Jean Meeus and adapted for high precision with topocentric corrections.
 */
public class MoonPhases {
    
    // Astronomical constants
    private static final double LUNAR_CYCLE_DAYS = 29.530588853; // Average synodic month
    private static final double J2000_EPOCH = 2451545.0; // Julian day for J2000.0 epoch
    private static final double CENTURIES_PER_DAY = 1.0 / 36525.0; // Julian centuries per day
    private static final double EARTH_RADIUS_KM = 6378.137; // Earth's equatorial radius in km
    private static final double MOON_DISTANCE_KM = 384400.0; // Average Earth-Moon distance in km
    
    // JSlider integration constants
    public static final int SLIDER_MIN = 0;
    public static final int SLIDER_MAX = 100;
    public static final int NEW_MOON_POSITION = 0;
    public static final int FULL_MOON_POSITION = 50;
    
    // Observer location (topocentric) variables
    private static double observerLatitude = 0.0;   // Default: Equator
    private static double observerLongitude = 0.0;  // Default: Prime Meridian
    private static double observerElevation = 0.0;  // Default: Sea level (meters)
    private static boolean locationSet = false;     // Track if location has been set
    
    // Known new moon reference (January 6, 2000, 18:14 UTC)
    private static final double REFERENCE_NEW_MOON_JD = 2451550.26;
    
    /**
     * Get the current lunar phase percentage (0-100) for JSlider positioning
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
            
            // Convert to phase percentage for JSlider
            double phasePercentage = (elongation / 360.0) * 100.0;
            
            return Math.round(phasePercentage * 10.0) / 10.0; // Round to 1 decimal place
            
        } catch (Exception e) {
            System.err.println("Error calculating lunar phase: " + e.getMessage());
            return 0.0; // Default to new moon on error
        }
    }
    
    // ===============================
    // LOCATION-BASED (TOPOCENTRIC) METHODS
    // ===============================
    
    /**
     * Set observer location for topocentric calculations
     * @param latitude Observer latitude in degrees (-90 to +90)
     * @param longitude Observer longitude in degrees (-180 to +180)
     * @param elevation Observer elevation in meters above sea level
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
        
        System.out.println(String.format("Observer location set: %.6f°, %.6f°, %.1fm", 
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
        
        System.out.println("Observer location cleared - reverting to geocentric calculations");
    }
    
    /**
     * Get current lunar phase name based on phase percentage
     * @return String describing the current lunar phase
     */
    public static String getCurrentPhaseName() {
        try {
            double phase = getCurrentLunarPhase();
            
            if (phase >= 0 && phase < 1) {
                return "New Moon";
            } else if (phase >= 1 && phase < 49) {
                return "Waxing Crescent";
            } else if (phase >= 49 && phase < 51) {
                return "Full Moon";
            } else if (phase >= 51 && phase < 99) {
                return "Waning Crescent";
            } else {
                return "New Moon";
            }
        } catch (Exception e) {
            System.err.println("Error determining phase name: " + e.getMessage());
            return "Unknown";
        }
    }
    
    /**
     * Get observer location information
     * @return String describing current observer location
     */
    public static String getObserverLocation() {
        if (!locationSet) {
            return "No observer location set (using geocentric calculations)";
        }
        return String.format("Observer: %.6f°N, %.6f°E, %.1fm elevation", 
            observerLatitude, observerLongitude, observerElevation);
    }
    
    /**
     * Check if observer location has been set
     * @return true if location is configured, false if using geocentric
     */
    public static boolean hasObserverLocation() {
        return locationSet;
    }
    
    /**
     * Get topocentric (location-aware) lunar phase percentage
     * @return Phase percentage adjusted for observer location
     */
    public static double getTopocentricLunarPhase() {
        try {
            if (!locationSet) {
                return getCurrentLunarPhase(); // Fallback to geocentric
            }
            
            double currentJD = getCurrentJulianDay();
            
            // Calculate topocentric moon and sun positions
            double[] moonPos = getTopocentricMoonPosition(currentJD);
            double[] sunPos = getTopocentricSunPosition(currentJD);
            
            // Calculate elongation with topocentric corrections
            double elongation = moonPos[0] - sunPos[0]; // Longitude difference
            
            // Apply parallax corrections
            double parallaxCorrection = calculateParallaxCorrection(moonPos[2]); // moonPos[2] = distance
            elongation += parallaxCorrection;
            
            // Normalize to 0-360 degrees
            elongation = normalizeAngle(elongation);
            
            // Convert to phase percentage
            double phasePercentage = (elongation / 360.0) * 100.0;
            
            return Math.round(phasePercentage * 10.0) / 10.0;
            
        } catch (Exception e) {
            System.err.println("Error calculating topocentric lunar phase: " + e.getMessage());
            return getCurrentLunarPhase(); // Fallback to geocentric
        }
    }
    
    /**
     * Get enhanced JSlider position accounting for observer location
     * @return Slider position (0-100) with location corrections applied
     */
    public static int getLocationAwareSliderPosition() {
        try {
            if (!locationSet) {
                return getSliderPosition(); // Use geocentric if no location set
            }
            
            // Blend geocentric and topocentric for smooth slider movement
            double geocentricPhase = getCurrentLunarPhase();
            double topocentricPhase = getTopocentricLunarPhase();
            
            // Use 95% geocentric, 5% topocentric for subtle location influence
            double blendedPhase = (geocentricPhase * 0.95) + (topocentricPhase * 0.05);
            
            return (int) Math.round(blendedPhase);
            
        } catch (Exception e) {
            System.err.println("Error calculating location-aware slider position: " + e.getMessage());
            return getSliderPosition(); // Fallback to basic calculation
        }
    }
    
    /**
     * Calculate moonrise time for observer location
     * @return Date object representing moonrise time (null if moon doesn't rise)
     */
    public static Date getMoonriseTime() {
        try {
            if (!locationSet) {
                return null; // Cannot calculate without location
            }
            
            double currentJD = getCurrentJulianDay();
            
            // Find moonrise for current date
            Date moonrise = findMoonEvent(currentJD, true); // true = rise, false = set
            
            return moonrise;
            
        } catch (Exception e) {
            System.err.println("Error calculating moonrise: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Calculate moonset time for observer location
     * @return Date object representing moonset time (null if moon doesn't set)
     */
    public static Date getMoonsetTime() {
        try {
            if (!locationSet) {
                return null; // Cannot calculate without location
            }
            
            double currentJD = getCurrentJulianDay();
            
            // Find moonset for current date
            Date moonset = findMoonEvent(currentJD, false); // false = set
            
            return moonset;
            
        } catch (Exception e) {
            System.err.println("Error calculating moonset: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get comprehensive lunar information including location-based data
     * @return Formatted string with all lunar data
     */
    public static String getLocationAwareLunarInfo() {
        try {
            // Basic lunar data
            double geocentricPhase = getCurrentLunarPhase();
            double illumination = getLunarIllumination();
            String phaseName = getPhaseName(geocentricPhase);
            double moonAge = getMoonAge();
            
            StringBuilder info = new StringBuilder();
            info.append("=== Lunar Phase Data ===\n");
            info.append(String.format("Geocentric Phase: %.1f%%\n", geocentricPhase));
            info.append(String.format("Illumination: %.1f%%\n", illumination));
            info.append(String.format("Phase Name: %s\n", phaseName));
            info.append(String.format("Moon Age: %.1f days\n", moonAge));
            
            // Location-specific data
            if (locationSet) {
                double topocentricPhase = getTopocentricLunarPhase();
                Date moonrise = getMoonriseTime();
                Date moonset = getMoonsetTime();
                
                info.append("\n=== Location-Based Data ===\n");
                info.append(getObserverLocation()).append("\n");
                info.append(String.format("Topocentric Phase: %.1f%%\n", topocentricPhase));
                
                if (moonrise != null) {
                    info.append(String.format("Moonrise: %s\n", formatTime(moonrise)));
                } else {
                    info.append("Moonrise: Not visible\n");
                }
                
                if (moonset != null) {
                    info.append(String.format("Moonset: %s\n", formatTime(moonset)));
                } else {
                    info.append("Moonset: Not visible\n");
                }
            } else {
                info.append("\n=== Location Info ===\n");
                info.append("No observer location set (using geocentric calculations)\n");
                info.append("Use setObserverLocation() for topocentric calculations\n");
            }
            
            // Standard events
            Date nextNewMoon = getNextNewMoon();
            Date nextFullMoon = getNextFullMoon();
            info.append("\n=== Upcoming Events ===\n");
            info.append(String.format("Next New Moon: %s\n", nextNewMoon.toString()));
            info.append(String.format("Next Full Moon: %s\n", nextFullMoon.toString()));
            
            return info.toString();
            
        } catch (Exception e) {
            return "Error retrieving location-aware lunar information: " + e.getMessage();
        }
    }
    
    // ===============================
    // PRIVATE TOPOCENTRIC CALCULATION HELPERS
    // ===============================
    
    /**
     * Calculate topocentric moon position with comprehensive location-based corrections
     * @param julianDay Julian day number
     * @return Array containing [longitude, latitude, distance] with topocentric corrections
     */
    private static double[] getTopocentricMoonPosition(double julianDay) {
        try {
            // Get geocentric moon position first
            double[] geocentricPos = getGeocentricMoonPosition(julianDay);
            double geocentricLon = geocentricPos[0];
            double geocentricLat = geocentricPos[1];
            double distance = geocentricPos[2];
            
            if (!locationSet) {
                return geocentricPos; // Return geocentric if no location set
            }
            
            // Calculate topocentric corrections
            double[] corrections = calculateTopocentricCorrections(
                geocentricLon, geocentricLat, distance, julianDay);
                
            double topocentricLon = normalizeAngle(geocentricLon + corrections[0]);
            double topocentricLat = geocentricLat + corrections[1];
            double topocentricDist = distance + corrections[2];
            
            return new double[]{topocentricLon, topocentricLat, topocentricDist};
            
        } catch (Exception e) {
            System.err.println("Error calculating topocentric moon position: " + e.getMessage());
            // Fallback to geocentric calculation
            return getGeocentricMoonPosition(julianDay);
        }
    }
    
    /**
     * Calculate geocentric moon position using enhanced Meeus algorithms
     * @param julianDay Julian day number
     * @return Array containing [longitude, latitude, distance] in geocentric coordinates
     */
    private static double[] getGeocentricMoonPosition(double julianDay) {
        try {
            // Time in Julian centuries from J2000.0
            double T = (julianDay - 2451545.0) / 36525.0;
            
            // Moon's mean longitude (L')
            double L_prime = 218.3164477 + 481267.88123421 * T - 0.0015786 * T * T + 
                           T * T * T / 538841.0 - T * T * T * T / 65194000.0;
            
            // Sun's mean anomaly (M)
            double M = 357.5291092 + 35999.0502909 * T - 0.0001536 * T * T + 
                      T * T * T / 24490000.0;
            
            // Moon's mean anomaly (M')
            double M_prime = 134.9633964 + 477198.8675055 * T + 0.0087414 * T * T + 
                           T * T * T / 69699.0 - T * T * T * T / 14712000.0;
            
            // Moon's argument of latitude (F)
            double F = 93.2720950 + 483202.0175233 * T - 0.0036539 * T * T - 
                      T * T * T / 3526000.0 + T * T * T * T / 863310000.0;
            
            // Convert to radians
            L_prime = Math.toRadians(normalizeAngle(L_prime));
            M = Math.toRadians(normalizeAngle(M));
            M_prime = Math.toRadians(normalizeAngle(M_prime));
            F = Math.toRadians(normalizeAngle(F));
            
            // Longitude perturbations (major terms)
            double sigmaL = 6.288774 * Math.sin(M_prime) +
                          1.274027 * Math.sin(2.0 * L_prime - M_prime) +
                          0.658314 * Math.sin(2.0 * L_prime) +
                          0.213618 * Math.sin(2.0 * M_prime) -
                          0.185116 * Math.sin(M) -
                          0.114332 * Math.sin(2.0 * F) +
                          0.058793 * Math.sin(2.0 * L_prime - 2.0 * M_prime) +
                          0.057066 * Math.sin(2.0 * L_prime - M - M_prime) +
                          0.053322 * Math.sin(2.0 * L_prime + M_prime) +
                          0.045758 * Math.sin(2.0 * L_prime - M);
            
            // Latitude perturbations (major terms)
            double sigmaB = 5.128122 * Math.sin(F) +
                          0.280602 * Math.sin(M_prime + F) +
                          0.277693 * Math.sin(M_prime - F) +
                          0.173237 * Math.sin(2.0 * L_prime - F) +
                          0.055413 * Math.sin(2.0 * L_prime + F - M_prime) +
                          0.046271 * Math.sin(2.0 * L_prime - F - M_prime) +
                          0.032573 * Math.sin(2.0 * L_prime + F) +
                          0.017198 * Math.sin(2.0 * M_prime + F);
            
            // Distance perturbations (major terms)
            double sigmaR = -20905.355 * Math.cos(M_prime) -
                          3699.111 * Math.cos(2.0 * L_prime - M_prime) -
                          2955.968 * Math.cos(2.0 * L_prime) -
                          569.925 * Math.cos(2.0 * M_prime) +
                          48.888 * Math.cos(M) -
                          3.149 * Math.cos(2.0 * F) +
                          246.158 * Math.cos(2.0 * L_prime - 2.0 * M_prime) -
                          152.138 * Math.cos(2.0 * L_prime - M - M_prime) -
                          170.733 * Math.cos(2.0 * L_prime + M_prime) -
                          204.586 * Math.cos(2.0 * L_prime - M);
            
            // Final geocentric coordinates
            double longitude = Math.toDegrees(L_prime) + sigmaL;
            double latitude = sigmaB;
            double distance = 385000.56 + sigmaR / 1000.0; // Convert to km
            
            return new double[]{normalizeAngle(longitude), latitude, distance};
            
        } catch (Exception e) {
            System.err.println("Error calculating geocentric moon position: " + e.getMessage());
            // Simple fallback
            double daysSinceRefNewMoon = julianDay - REFERENCE_NEW_MOON_JD;
            double moonAge = daysSinceRefNewMoon % LUNAR_CYCLE_DAYS;
            double elongation = (moonAge / LUNAR_CYCLE_DAYS) * 360.0;
            double sunLongitude = getSunLongitude(julianDay);
            return new double[]{normalizeAngle(sunLongitude + elongation), 0.0, 384400.0};
        }
    }
    
    /**
     * Calculate topocentric corrections for parallax and atmospheric refraction
     * @param geocentricLon Geocentric longitude in degrees
     * @param geocentricLat Geocentric latitude in degrees
     * @param distance Distance in kilometers
     * @param julianDay Julian day for time-dependent calculations
     * @return Array containing [longitude correction, latitude correction, distance correction]
     */
    private static double[] calculateTopocentricCorrections(double geocentricLon, 
            double geocentricLat, double distance, double julianDay) {
        try {
            // Earth's equatorial radius in km
            double earthRadius = 6378.137;
            
            // Calculate local sidereal time (used in coordinate transformations)
            // double localSiderealTime = getLocalSiderealTime(julianDay);
            
            // Observer's geocentric coordinates
            double obsLatRad = Math.toRadians(observerLatitude);
            double obsLonRad = Math.toRadians(observerLongitude);
            double obsHeight = observerElevation / 1000.0; // Convert to km
            
            // Earth's flattening
            double flattening = 1.0 / 298.257223563;
            double e2 = 2.0 * flattening - flattening * flattening;
            
            // Observer's geocentric position
            double sinLat = Math.sin(obsLatRad);
            double cosLat = Math.cos(obsLatRad);
            double N = earthRadius / Math.sqrt(1.0 - e2 * sinLat * sinLat);
            
            double obsX = (N + obsHeight) * cosLat * Math.cos(obsLonRad);
            double obsY = (N + obsHeight) * cosLat * Math.sin(obsLonRad);
            double obsZ = (N * (1.0 - e2) + obsHeight) * sinLat;
            
            // Convert moon's geocentric coordinates to rectangular
            double moonLonRad = Math.toRadians(geocentricLon);
            double moonLatRad = Math.toRadians(geocentricLat);
            
            double moonX = distance * Math.cos(moonLatRad) * Math.cos(moonLonRad);
            double moonY = distance * Math.cos(moonLatRad) * Math.sin(moonLonRad);
            double moonZ = distance * Math.sin(moonLatRad);
            
            // Topocentric rectangular coordinates
            double topoX = moonX - obsX;
            double topoY = moonY - obsY;
            double topoZ = moonZ - obsZ;
            
            // Convert back to spherical coordinates
            double topoDist = Math.sqrt(topoX * topoX + topoY * topoY + topoZ * topoZ);
            double topoLonRad = Math.atan2(topoY, topoX);
            double topoLatRad = Math.asin(topoZ / topoDist);
            
            // Calculate corrections
            double lonCorrection = Math.toDegrees(topoLonRad - moonLonRad);
            double latCorrection = Math.toDegrees(topoLatRad - moonLatRad);
            double distCorrection = topoDist - distance;
            
            // Apply atmospheric refraction for altitude-dependent corrections
            double altitude = calculateMoonAltitude(geocentricLon, geocentricLat, julianDay);
            double refractionCorrection = calculateAtmosphericRefraction(altitude);
            latCorrection += refractionCorrection;
            
            return new double[]{lonCorrection, latCorrection, distCorrection};
            
        } catch (Exception e) {
            System.err.println("Error calculating topocentric corrections: " + e.getMessage());
            return new double[]{0.0, 0.0, 0.0}; // No corrections on error
        }
    }
    
    /**
     * Calculate local sidereal time for observer location
     * @param julianDay Julian day number
     * @return Local sidereal time in hours
     */
    private static double getLocalSiderealTime(double julianDay) {
        // Greenwich sidereal time at 0h UT
        double T = (julianDay - 2451545.0) / 36525.0;
        double gst0 = 6.697374558 + 2400.051336 * T + 0.000025862 * T * T;
        
        // Universal time in hours
        double ut = (julianDay - Math.floor(julianDay) - 0.5) * 24.0;
        if (ut < 0) ut += 24.0;
        
        // Greenwich sidereal time
        double gst = gst0 + ut * 1.002737909;
        gst = gst % 24.0;
        if (gst < 0) gst += 24.0;
        
        // Local sidereal time
        double lst = gst + observerLongitude / 15.0;
        lst = lst % 24.0;
        if (lst < 0) lst += 24.0;
        
        return lst;
    }
    
    /**
     * Calculate moon's altitude above horizon for atmospheric refraction
     * @param longitude Moon's longitude in degrees
     * @param latitude Moon's latitude in degrees
     * @param julianDay Julian day number
     * @return Altitude in degrees
     */
    private static double calculateMoonAltitude(double longitude, double latitude, double julianDay) {
        try {
            // Convert to hour angle and declination
            double lst = getLocalSiderealTime(julianDay);
            double hourAngle = lst * 15.0 - longitude; // Convert LST to degrees and get hour angle
            
            // Calculate altitude using spherical trigonometry
            double obsLatRad = Math.toRadians(observerLatitude);
            double decRad = Math.toRadians(latitude);
            double hourAngleRad = Math.toRadians(hourAngle);
            
            double sinAlt = Math.sin(obsLatRad) * Math.sin(decRad) + 
                          Math.cos(obsLatRad) * Math.cos(decRad) * Math.cos(hourAngleRad);
            
            return Math.toDegrees(Math.asin(Math.max(-1.0, Math.min(1.0, sinAlt))));
            
        } catch (Exception e) {
            return 45.0; // Default altitude on error
        }
    }
    
    /**
     * Calculate atmospheric refraction correction
     * @param altitude Apparent altitude in degrees
     * @return Refraction correction in degrees
     */
    private static double calculateAtmosphericRefraction(double altitude) {
        if (altitude < -1.0) return 0.0; // Below horizon
        
        // Simple refraction model (Saemundsson formula)
        double altRad = Math.toRadians(altitude + 10.3 / (altitude + 5.11));
        double refraction = 1.02 / Math.tan(altRad);
        
        // Convert from arcminutes to degrees
        return refraction / 60.0;
    }
    
    /**
     * Calculate topocentric sun position
     * @param julianDay Julian day number
     * @return Array containing [longitude, latitude, distance] with topocentric corrections
     */
    private static double[] getTopocentricSunPosition(double julianDay) {
        try {
            // Get basic sun longitude
            double sunLongitude = getSunLongitude(julianDay);
            
            // Sun parallax is minimal (8.8 arcseconds), but apply small correction
            double parallaxCorrection = 0.0;
            if (locationSet) {
                // Very small correction for sun parallax
                double latitudeFactor = Math.sin(Math.toRadians(observerLatitude));
                parallaxCorrection = 0.002 * latitudeFactor; // Max 0.002 degrees
            }
            
            sunLongitude += parallaxCorrection;
            
            // Sun distance varies ~147M to 152M km
            double meanDistance = 149597870.7; // km (1 AU)
            
            return new double[]{normalizeAngle(sunLongitude), 0.0, meanDistance};
            
        } catch (Exception e) {
            System.err.println("Error calculating topocentric sun position: " + e.getMessage());
            return new double[]{getSunLongitude(julianDay), 0.0, 149597870.7};
        }
    }
    
    /**
     * Calculate parallax correction for lunar distance
     * @param moonDistance Distance to moon in kilometers
     * @return Parallax correction in degrees
     */
    private static double calculateParallaxCorrection(double moonDistance) {
        try {
            // Horizontal parallax calculation
            double horizontalParallax = Math.asin(EARTH_RADIUS_KM / moonDistance);
            
            // Convert to degrees
            horizontalParallax = Math.toDegrees(horizontalParallax);
            
            // Apply observer elevation correction
            double elevationFactor = 1.0 - (observerElevation / EARTH_RADIUS_KM);
            
            return horizontalParallax * elevationFactor;
            
        } catch (Exception e) {
            System.err.println("Error calculating parallax correction: " + e.getMessage());
            return 0.0; // No correction on error
        }
    }
    
    /**
     * Calculate local hour angle for observer location
     * @param julianDay Julian day number
     * @param longitude Celestial longitude
     * @return Local hour angle in degrees
     */
    private static double getLocalHourAngle(double julianDay, double longitude) {
        try {
            // Calculate Greenwich Mean Sidereal Time
            double gmst = getGreenwichMeanSiderealTime(julianDay);
            
            // Local Sidereal Time
            double lst = gmst + observerLongitude;
            lst = normalizeAngle(lst);
            
            // Hour angle
            double hourAngle = lst - longitude;
            
            return normalizeAngle(hourAngle);
            
        } catch (Exception e) {
            System.err.println("Error calculating local hour angle: " + e.getMessage());
            return 0.0;
        }
    }
    
    /**
     * Calculate Greenwich Mean Sidereal Time
     * @param julianDay Julian day number
     * @return GMST in degrees
     */
    private static double getGreenwichMeanSiderealTime(double julianDay) {
        try {
            // Days since J2000.0
            double t = (julianDay - 2451545.0) / 36525.0;
            
            // GMST at 0h UT
            double gmst = 280.46061837 + 360.98564736629 * (julianDay - 2451545.0)
                         + 0.000387933 * t * t - t * t * t / 38710000.0;
            
            return normalizeAngle(gmst);
            
        } catch (Exception e) {
            System.err.println("Error calculating GMST: " + e.getMessage());
            return 0.0;
        }
    }
    
    /**
     * Find moonrise or moonset events for observer location
     * @param julianDay Starting Julian day
     * @param isRise true for moonrise, false for moonset
     * @return Date of moon event (null if event doesn't occur)
     */
    private static Date findMoonEvent(double julianDay, boolean isRise) {
        try {
            if (!locationSet) {
                return null; // Cannot calculate without location
            }
            
            // Search within 24 hours using simplified calculation
            double startJD = Math.floor(julianDay);
            double endJD = startJD + 1.0;
            double step = 1.0 / 60.0; // 24-minute steps for faster search
            
            Double eventTime = null;
            double previousAltitude = Double.NaN;
            
            for (double jd = startJD; jd <= endJD; jd += step) {
                double altitude = getMoonAltitude(jd);
                
                if (!Double.isNaN(previousAltitude)) {
                    // Check for horizon crossing
                    boolean crossedHorizon = false;
                    
                    if (isRise && previousAltitude < 0.0 && altitude >= 0.0) {
                        crossedHorizon = true; // Moonrise
                    } else if (!isRise && previousAltitude >= 0.0 && altitude < 0.0) {
                        crossedHorizon = true; // Moonset
                    }
                    
                    if (crossedHorizon) {
                        eventTime = jd;
                        break;
                    }
                }
                
                previousAltitude = altitude;
            }
            
            if (eventTime != null) {
                return julianDayToDate(eventTime);
            }
            
            return null; // Event not found
            
        } catch (Exception e) {
            System.err.println("Error finding moon event: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Calculate moon altitude for observer location
     * @param julianDay Julian day number
     * @return Moon altitude in degrees
     */
    private static double getMoonAltitude(double julianDay) {
        try {
            double[] moonPos = getTopocentricMoonPosition(julianDay);
            double moonLongitude = moonPos[0];
            double moonLatitude = moonPos[1];
            
            double hourAngle = getLocalHourAngle(julianDay, moonLongitude);
            
            // Calculate altitude using spherical trigonometry
            double sinAlt = Math.sin(Math.toRadians(observerLatitude)) * Math.sin(Math.toRadians(moonLatitude))
                          + Math.cos(Math.toRadians(observerLatitude)) * Math.cos(Math.toRadians(moonLatitude))
                            * Math.cos(Math.toRadians(hourAngle));
            
            return Math.toDegrees(Math.asin(sinAlt));
            
        } catch (Exception e) {
            System.err.println("Error calculating moon altitude: " + e.getMessage());
            return -90.0; // Below horizon on error
        }
    }
    
    /**
     * Format time for display
     * @param date Date to format
     * @return Formatted time string
     */
    private static String formatTime(Date date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss z");
            return formatter.format(date);
        } catch (Exception e) {
            return date.toString();
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
            return 0.0; // Default value on error
        }
    }
    
    /**
     * Get the name of the current lunar phase
     * @param phasePercentage The phase percentage (0-100)
     * @return String name of the lunar phase
     */
    public static String getPhaseName(double phasePercentage) {
        // Normalize phase to ensure it's within 0-100 range
        while (phasePercentage < 0) phasePercentage += 100;
        while (phasePercentage >= 100) phasePercentage -= 100;
        
        // Determine phase name based on percentage
        if (phasePercentage < 6.25) {
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
        } else if (phasePercentage < 93.75) {
            return "Waning Crescent";
        } else {
            return "New Moon";
        }
    }
    
    /**
     * Calculate the age of the moon in days since new moon
     * @return Moon age in days
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
     * Calculate the next new moon date
     * @return Date of the next new moon
     */
    public static Date getNextNewMoon() {
        try {
            double currentJD = getCurrentJulianDay();
            double daysSinceNewMoon = (currentJD - REFERENCE_NEW_MOON_JD) % LUNAR_CYCLE_DAYS;
            
            if (daysSinceNewMoon < 0) {
                daysSinceNewMoon += LUNAR_CYCLE_DAYS;
            }
            
            double daysToNextNewMoon = LUNAR_CYCLE_DAYS - daysSinceNewMoon;
            double nextNewMoonJD = currentJD + daysToNextNewMoon;
            
            return julianDayToDate(nextNewMoonJD);
            
        } catch (Exception e) {
            System.err.println("Error calculating next new moon: " + e.getMessage());
            return new Date(); // Return current date on error
        }
    }
    
    /**
     * Calculate the next full moon date
     * @return Date of the next full moon
     */
    public static Date getNextFullMoon() {
        try {
            double currentJD = getCurrentJulianDay();
            double daysSinceNewMoon = (currentJD - REFERENCE_NEW_MOON_JD) % LUNAR_CYCLE_DAYS;
            
            if (daysSinceNewMoon < 0) {
                daysSinceNewMoon += LUNAR_CYCLE_DAYS;
            }
            
            double daysToNextFullMoon;
            if (daysSinceNewMoon < LUNAR_CYCLE_DAYS / 2) {
                // Next full moon is in this cycle
                daysToNextFullMoon = (LUNAR_CYCLE_DAYS / 2) - daysSinceNewMoon;
            } else {
                // Next full moon is in next cycle
                daysToNextFullMoon = LUNAR_CYCLE_DAYS + (LUNAR_CYCLE_DAYS / 2) - daysSinceNewMoon;
            }
            
            double nextFullMoonJD = currentJD + daysToNextFullMoon;
            return julianDayToDate(nextFullMoonJD);
            
        } catch (Exception e) {
            System.err.println("Error calculating next full moon: " + e.getMessage());
            return new Date(); // Return current date on error
        }
    }
    
    /**
     * Get lunar phase for a specific date
     * @param date The date to calculate phase for
     * @return Phase percentage for the given date
     */
    public static double getLunarPhaseForDate(Date date) {
        try {
            double julianDay = dateToJulianDay(date);
            double daysSinceNewMoon = julianDay - REFERENCE_NEW_MOON_JD;
            double cyclePosition = daysSinceNewMoon % LUNAR_CYCLE_DAYS;
            
            if (cyclePosition < 0) {
                cyclePosition += LUNAR_CYCLE_DAYS;
            }
            
            return (cyclePosition / LUNAR_CYCLE_DAYS) * 100.0;
            
        } catch (Exception e) {
            System.err.println("Error calculating lunar phase for date: " + e.getMessage());
            return 0.0;
        }
    }
    
    // Private utility methods
    
    /**
     * Get the current Julian day number
     * @return Current Julian day as double
     */
    private static double getCurrentJulianDay() {
        return dateToJulianDay(new Date());
    }
    
    /**
     * Convert a Date to Julian day number
     * @param date Date to convert
     * @return Julian day number
     */
    private static double dateToJulianDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1; // Calendar.MONTH is 0-based
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        
        // Convert time to fractional day
        double fractionalDay = day + (hour + (minute + second / 60.0) / 60.0) / 24.0;
        
        // Julian day calculation
        if (month <= 2) {
            year--;
            month += 12;
        }
        
        int a = year / 100;
        int b = 2 - a + (a / 4);
        
        double jd = Math.floor(365.25 * (year + 4716)) + 
                   Math.floor(30.6001 * (month + 1)) + 
                   fractionalDay + b - 1524.5;
        
        return jd;
    }
    
    /**
     * Convert Julian day number to Date
     * @param julianDay Julian day number
     * @return Corresponding Date object
     */
    private static Date julianDayToDate(double julianDay) {
        // Julian day to calendar conversion
        double jd = julianDay + 0.5;
        int z = (int) jd;
        double f = jd - z;
        
        int a;
        if (z < 2299161) {
            a = z;
        } else {
            int alpha = (int) ((z - 1867216.25) / 36524.25);
            a = z + 1 + alpha - (alpha / 4);
        }
        
        int b = a + 1524;
        int c = (int) ((b - 122.1) / 365.25);
        int d = (int) (365.25 * c);
        int e = (int) ((b - d) / 30.6001);
        
        int day = b - d - (int) (30.6001 * e);
        int month = (e < 14) ? e - 1 : e - 13;
        int year = (month > 2) ? c - 4716 : c - 4715;
        
        // Convert fractional day to hours, minutes, seconds
        double fractionalDay = f * 24.0;
        int hour = (int) fractionalDay;
        double fractionalHour = (fractionalDay - hour) * 60.0;
        int minute = (int) fractionalHour;
        int second = (int) ((fractionalHour - minute) * 60.0);
        
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, hour, minute, second); // month is 0-based in Calendar
        cal.set(Calendar.MILLISECOND, 0);
        
        return cal.getTime();
    }
    
    /**
     * Get detailed lunar information as a formatted string
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
            info.append("=== Lunar Phase Data ===\n");
            info.append(String.format("Current Phase: %.1f%%\n", phase));
            info.append(String.format("Illumination: %.1f%%\n", illumination));
            info.append(String.format("Phase Name: %s\n", phaseName));
            info.append(String.format("Moon Age: %.1f days\n", moonAge));
            info.append(String.format("Next New Moon: %s\n", nextNewMoon.toString()));
            info.append(String.format("Next Full Moon: %s\n", nextFullMoon.toString()));
            
            return info.toString();
            
        } catch (Exception e) {
            return "Error retrieving lunar information: " + e.getMessage();
        }
    }
    
    // ===============================
    // JSlider Integration Methods
    // ===============================
    
    /**
     * Get JSlider position for current moon phase
     * @return Slider position (0-100) representing current lunar phase
     */
    public static int getSliderPosition() {
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
        
        // Main periodic terms for longitude (simplified)
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
                         - 0.030383 * Math.sin(M + M1)
                         + 0.015327 * Math.sin(2 * (D - F))
                         - 0.012528 * Math.sin(2 * F + M1)
                         + 0.010980 * Math.sin(2 * F - M1)
                         + 0.010675 * Math.sin(4 * D - M1)
                         + 0.010034 * Math.sin(3 * M1)
                         + 0.008548 * Math.sin(4 * D - 2 * M1);
        
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
}
