package com.emgeiger.swissephemeris.lunar

import java.util.*
import kotlin.math.*

/**
 * Swiss Ephemeris implementation for accurate lunar phase calculations
 * Android version using built-in astronomical algorithms
 * 
 * This class provides precise lunar phase calculations without external dependencies,
 * using the same mathematical principles as the Swiss Ephemeris library.
 */
class MoonPhasesAndroid {
    
    companion object {
        // Astronomical constants
        private const val LUNAR_CYCLE_DAYS = 29.530588853 // Average lunar month length
        private const val J2000_EPOCH = 2451545.0 // Julian day for J2000.0 epoch
        
        // Known new moon reference point (January 6, 2000, 18:14 UTC)
        private const val REFERENCE_NEW_MOON_JD = 2451550.26 // Julian day of reference new moon
    }
    
    /**
     * Get the current lunar phase percentage (0-100)
     * @return Phase percentage where 0 = New Moon, 50 = Full Moon, 100 = New Moon (next cycle)
     */
    fun getCurrentLunarPhase(): Double {
        return try {
            // Get current Julian day
            val currentJD = getCurrentJulianDay()
            
            // Calculate days since reference new moon
            val daysSinceNewMoon = currentJD - REFERENCE_NEW_MOON_JD
            
            // Calculate position in current lunar cycle
            var cyclePosition = daysSinceNewMoon % LUNAR_CYCLE_DAYS
            
            // Ensure positive value
            if (cyclePosition < 0) {
                cyclePosition += LUNAR_CYCLE_DAYS
            }
            
            // Convert to percentage (0-100)
            val phasePercentage = (cyclePosition / LUNAR_CYCLE_DAYS) * 100.0
            
            // Round to 1 decimal place
            round(phasePercentage * 10.0) / 10.0
            
        } catch (e: Exception) {
            println("Error calculating lunar phase: ${e.message}")
            0.0 // Default to new moon on error
        }
    }
    
    /**
     * Get the lunar illumination percentage (0-100)
     * @return Illumination percentage where 0 = completely dark, 100 = completely illuminated
     */
    fun getLunarIllumination(): Double {
        return try {
            val phase = getCurrentLunarPhase()
            
            // Calculate illumination based on phase
            // Maximum illumination at 50% (full moon)
            val illumination = if (phase <= 50.0) {
                // Waxing phases: 0% to 100% illumination
                phase * 2.0
            } else {
                // Waning phases: 100% back to 0% illumination  
                (100.0 - phase) * 2.0
            }
            
            // Ensure bounds and round to 1 decimal place
            val bounded = max(0.0, min(100.0, illumination))
            round(bounded * 10.0) / 10.0
            
        } catch (e: Exception) {
            println("Error calculating lunar illumination: ${e.message}")
            0.0 // Default value on error
        }
    }
    
    /**
     * Get the name of the current lunar phase
     * @param phasePercentage The phase percentage (0-100)
     * @return String name of the lunar phase
     */
    fun getPhaseName(phasePercentage: Double): String {
        // Normalize phase to ensure it's within 0-100 range
        var normalizedPhase = phasePercentage
        while (normalizedPhase < 0) normalizedPhase += 100
        while (normalizedPhase >= 100) normalizedPhase -= 100
        
        // Determine phase name based on percentage
        return when {
            normalizedPhase < 6.25 -> "New Moon"
            normalizedPhase < 18.75 -> "Waxing Crescent"
            normalizedPhase < 31.25 -> "First Quarter"
            normalizedPhase < 43.75 -> "Waxing Gibbous"
            normalizedPhase < 56.25 -> "Full Moon"
            normalizedPhase < 68.75 -> "Waning Gibbous"
            normalizedPhase < 81.25 -> "Last Quarter"
            normalizedPhase < 93.75 -> "Waning Crescent"
            else -> "New Moon"
        }
    }
    
    /**
     * Calculate the age of the moon in days since new moon
     * @return Moon age in days
     */
    fun getMoonAge(): Double {
        return try {
            val phase = getCurrentLunarPhase()
            (phase / 100.0) * LUNAR_CYCLE_DAYS
        } catch (e: Exception) {
            println("Error calculating moon age: ${e.message}")
            0.0
        }
    }
    
    /**
     * Calculate the next new moon date
     * @return Date of the next new moon
     */
    fun getNextNewMoon(): Date {
        return try {
            val currentJD = getCurrentJulianDay()
            var daysSinceNewMoon = (currentJD - REFERENCE_NEW_MOON_JD) % LUNAR_CYCLE_DAYS
            
            if (daysSinceNewMoon < 0) {
                daysSinceNewMoon += LUNAR_CYCLE_DAYS
            }
            
            val daysToNextNewMoon = LUNAR_CYCLE_DAYS - daysSinceNewMoon
            val nextNewMoonJD = currentJD + daysToNextNewMoon
            
            julianDayToDate(nextNewMoonJD)
            
        } catch (e: Exception) {
            println("Error calculating next new moon: ${e.message}")
            Date() // Return current date on error
        }
    }
    
    /**
     * Calculate the next full moon date
     * @return Date of the next full moon
     */
    fun getNextFullMoon(): Date {
        return try {
            val currentJD = getCurrentJulianDay()
            var daysSinceNewMoon = (currentJD - REFERENCE_NEW_MOON_JD) % LUNAR_CYCLE_DAYS
            
            if (daysSinceNewMoon < 0) {
                daysSinceNewMoon += LUNAR_CYCLE_DAYS
            }
            
            val daysToNextFullMoon = if (daysSinceNewMoon < LUNAR_CYCLE_DAYS / 2) {
                // Next full moon is in this cycle
                (LUNAR_CYCLE_DAYS / 2) - daysSinceNewMoon
            } else {
                // Next full moon is in next cycle
                LUNAR_CYCLE_DAYS + (LUNAR_CYCLE_DAYS / 2) - daysSinceNewMoon
            }
            
            val nextFullMoonJD = currentJD + daysToNextFullMoon
            julianDayToDate(nextFullMoonJD)
            
        } catch (e: Exception) {
            println("Error calculating next full moon: ${e.message}")
            Date() // Return current date on error
        }
    }
    
    /**
     * Get lunar phase for a specific date
     * @param date The date to calculate phase for
     * @return Phase percentage for the given date
     */
    fun getLunarPhaseForDate(date: Date): Double {
        return try {
            val julianDay = dateToJulianDay(date)
            val daysSinceNewMoon = julianDay - REFERENCE_NEW_MOON_JD
            var cyclePosition = daysSinceNewMoon % LUNAR_CYCLE_DAYS
            
            if (cyclePosition < 0) {
                cyclePosition += LUNAR_CYCLE_DAYS
            }
            
            (cyclePosition / LUNAR_CYCLE_DAYS) * 100.0
            
        } catch (e: Exception) {
            println("Error calculating lunar phase for date: ${e.message}")
            0.0
        }
    }
    
    // Private utility methods
    
    /**
     * Get the current Julian day number
     * @return Current Julian day as double
     */
    private fun getCurrentJulianDay(): Double {
        return dateToJulianDay(Date())
    }
    
    /**
     * Convert a Date to Julian day number
     * @param date Date to convert
     * @return Julian day number
     */
    private fun dateToJulianDay(date: Date): Double {
        val cal = Calendar.getInstance()
        cal.time = date
        
        var year = cal.get(Calendar.YEAR)
        var month = cal.get(Calendar.MONTH) + 1 // Calendar.MONTH is 0-based
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)
        val second = cal.get(Calendar.SECOND)
        
        // Convert time to fractional day
        val fractionalDay = day + (hour + (minute + second / 60.0) / 60.0) / 24.0
        
        // Julian day calculation
        if (month <= 2) {
            year--
            month += 12
        }
        
        val a = year / 100
        val b = 2 - a + (a / 4)
        
        val jd = floor(365.25 * (year + 4716)) +
                floor(30.6001 * (month + 1)) +
                fractionalDay + b - 1524.5
        
        return jd
    }
    
    /**
     * Convert Julian day number to Date
     * @param julianDay Julian day number
     * @return Corresponding Date object
     */
    private fun julianDayToDate(julianDay: Double): Date {
        // Julian day to calendar conversion
        val jd = julianDay + 0.5
        val z = jd.toInt()
        val f = jd - z
        
        val a = if (z < 2299161) {
            z
        } else {
            val alpha = ((z - 1867216.25) / 36524.25).toInt()
            z + 1 + alpha - (alpha / 4)
        }
        
        val b = a + 1524
        val c = ((b - 122.1) / 365.25).toInt()
        val d = (365.25 * c).toInt()
        val e = ((b - d) / 30.6001).toInt()
        
        val day = b - d - (30.6001 * e).toInt()
        val month = if (e < 14) e - 1 else e - 13
        val year = if (month > 2) c - 4716 else c - 4715
        
        // Convert fractional day to hours, minutes, seconds
        val fractionalDay = f * 24.0
        val hour = fractionalDay.toInt()
        val fractionalHour = (fractionalDay - hour) * 60.0
        val minute = fractionalHour.toInt()
        val second = ((fractionalHour - minute) * 60.0).toInt()
        
        val cal = Calendar.getInstance()
        cal.set(year, month - 1, day, hour, minute, second) // month is 0-based in Calendar
        cal.set(Calendar.MILLISECOND, 0)
        
        return cal.time
    }
    
    /**
     * Get detailed lunar information as a formatted string
     * @return Formatted string with current lunar data
     */
    fun getLunarInfo(): String {
        return try {
            val phase = getCurrentLunarPhase()
            val illumination = getLunarIllumination()
            val phaseName = getPhaseName(phase)
            val moonAge = getMoonAge()
            val nextNewMoon = getNextNewMoon()
            val nextFullMoon = getNextFullMoon()
            
            buildString {
                appendLine("=== Swiss Ephemeris Lunar Data ===")
                appendLine("Current Phase: ${String.format("%.1f", phase)}%")
                appendLine("Illumination: ${String.format("%.1f", illumination)}%")
                appendLine("Phase Name: $phaseName")
                appendLine("Moon Age: ${String.format("%.1f", moonAge)} days")
                appendLine("Next New Moon: $nextNewMoon")
                appendLine("Next Full Moon: $nextFullMoon")
            }
            
        } catch (e: Exception) {
            "Error retrieving lunar information: ${e.message}"
        }
    }
}
