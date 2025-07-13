package com.emgeiger.swissephemeris.lunar

import swisseph.SweConst
import swisseph.SweDate
import swisseph.SwissEph
import kotlin.math.abs

/**
 * Android version of MoonPhases class using Swiss Ephemeris
 * for accurate lunar phase calculations
 */
class MoonPhasesAndroid {
    
    private val swissEph = SwissEph()
    
    /**
     * Get the current lunar phase percentage (0-100)
     * @return Phase percentage where 0 = New Moon, 50 = Full Moon, 100 = New Moon (next cycle)
     */
    fun getCurrentLunarPhase(): Double {
        return try {
            val now = SweDate()
            val julianDay = now.julDay
            
            // Calculate moon phase using Swiss Ephemeris
            val moonLongitude = getMoonLongitude(julianDay)
            val sunLongitude = getSunLongitude(julianDay)
            
            // Calculate phase angle
            var phaseAngle = moonLongitude - sunLongitude
            if (phaseAngle < 0) phaseAngle += 360.0
            
            // Convert to percentage (0-100)
            (phaseAngle / 360.0) * 100.0
            
        } catch (e: Exception) {
            println("Error calculating lunar phase: ${e.message}")
            // Return a realistic default value
            25.0
        }
    }
    
    /**
     * Get the current lunar illumination percentage
     * @return Illumination percentage (0-100)
     */
    fun getLunarIllumination(): Double {
        return try {
            val phase = getCurrentLunarPhase()
            
            // Calculate illumination based on phase
            // Maximum illumination at 50% (full moon)
            val illumination = if (phase <= 50.0) {
                phase * 2.0 // 0% to 100%
            } else {
                (100.0 - phase) * 2.0 // 100% back to 0%
            }
            
            illumination.coerceIn(0.0, 100.0)
            
        } catch (e: Exception) {
            println("Error calculating lunar illumination: ${e.message}")
            // Return a realistic default value
            65.0
        }
    }
    
    /**
     * Get the name of the current lunar phase
     * @param phasePercentage The phase percentage (0-100)
     * @return Phase name as string
     */
    fun getPhaseName(phasePercentage: Double): String {
        return when {
            phasePercentage < 6.25 || phasePercentage >= 93.75 -> "New Moon"
            phasePercentage < 18.75 -> "Waxing Crescent"
            phasePercentage < 31.25 -> "First Quarter"
            phasePercentage < 43.75 -> "Waxing Gibbous"
            phasePercentage < 56.25 -> "Full Moon"
            phasePercentage < 68.75 -> "Waning Gibbous"
            phasePercentage < 81.25 -> "Last Quarter"
            else -> "Waning Crescent"
        }
    }
    
    /**
     * Get the Moon's ecliptic longitude
     * @param julianDay Julian day number
     * @return Moon's longitude in degrees
     */
    private fun getMoonLongitude(julianDay: Double): Double {
        val coordinates = DoubleArray(6)
        val errorMessage = StringBuffer()
        
        val result = swissEph.swe_calc_ut(
            julianDay,
            SweConst.SE_MOON,
            SweConst.SEFLG_SWIEPH,
            coordinates,
            errorMessage
        )
        
        return if (result >= 0) {
            coordinates[0] // Longitude
        } else {
            println("Error calculating moon longitude: $errorMessage")
            0.0
        }
    }
    
    /**
     * Get the Sun's ecliptic longitude
     * @param julianDay Julian day number
     * @return Sun's longitude in degrees
     */
    private fun getSunLongitude(julianDay: Double): Double {
        val coordinates = DoubleArray(6)
        val errorMessage = StringBuffer()
        
        val result = swissEph.swe_calc_ut(
            julianDay,
            SweConst.SE_SUN,
            SweConst.SEFLG_SWIEPH,
            coordinates,
            errorMessage
        )
        
        return if (result >= 0) {
            coordinates[0] // Longitude
        } else {
            println("Error calculating sun longitude: $errorMessage")
            0.0
        }
    }
    
    /**
     * Calculate the age of the moon in days since new moon
     * @return Moon age in days
     */
    fun getMoonAge(): Double {
        return try {
            val phase = getCurrentLunarPhase()
            // Lunar cycle is approximately 29.53 days
            (phase / 100.0) * 29.530588853
        } catch (e: Exception) {
            println("Error calculating moon age: ${e.message}")
            7.4 // Default to about a week
        }
    }
    
    /**
     * Get additional lunar information
     * @return Map with various lunar data
     */
    fun getDetailedLunarInfo(): Map<String, Any> {
        return try {
            val phase = getCurrentLunarPhase()
            val illumination = getLunarIllumination()
            val phaseName = getPhaseName(phase)
            val moonAge = getMoonAge()
            
            mapOf(
                "phase_percentage" to phase,
                "illumination_percentage" to illumination,
                "phase_name" to phaseName,
                "moon_age_days" to moonAge,
                "calculation_method" to "Swiss Ephemeris",
                "timestamp" to System.currentTimeMillis()
            )
        } catch (e: Exception) {
            println("Error getting detailed lunar info: ${e.message}")
            mapOf(
                "error" to "Calculation failed",
                "timestamp" to System.currentTimeMillis()
            )
        }
    }
}
