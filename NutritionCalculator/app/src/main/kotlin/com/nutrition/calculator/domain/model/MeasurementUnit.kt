package com.nutrition.calculator.domain.model

import androidx.annotation.StringRes
import com.nutrition.calculator.R
import kotlin.math.roundToInt

/**
 * Measurement unit categories for nutrition calculations
 */
enum class UnitCategory {
    MASS,           // Weight/mass units (grams, ounces, pounds)
    VOLUME,         // Volume units (milliliters, cups, tablespoons)
    ENERGY,         // Energy units (calories, kilojoules)
    SERVING,        // Serving-based units (pieces, slices, portions)
    PERCENTAGE      // Percentage-based units (daily value %)
}

/**
 * Comprehensive measurement unit system for nutrition calculations
 * 
 * Supports conversion between different units within the same category
 * and provides localized display names for international users.
 */
enum class MeasurementUnit(
    val category: UnitCategory,
    val symbol: String,
    @StringRes val displayNameRes: Int,
    val baseUnitMultiplier: Double, // Conversion factor to base unit
    val precision: Int = 1, // Decimal places for display
    val isMetric: Boolean = true
) {
    
    // MASS UNITS (base unit: gram)
    GRAM(
        category = UnitCategory.MASS,
        symbol = "g",
        displayNameRes = R.string.unit_gram,
        baseUnitMultiplier = 1.0,
        precision = 1
    ),
    
    KILOGRAM(
        category = UnitCategory.MASS,
        symbol = "kg",
        displayNameRes = R.string.unit_kilogram,
        baseUnitMultiplier = 1000.0,
        precision = 3
    ),
    
    MILLIGRAM(
        category = UnitCategory.MASS,
        symbol = "mg",
        displayNameRes = R.string.unit_milligram,
        baseUnitMultiplier = 0.001,
        precision = 0
    ),
    
    MICROGRAM(
        category = UnitCategory.MASS,
        symbol = "μg",
        displayNameRes = R.string.unit_microgram,
        baseUnitMultiplier = 0.000001,
        precision = 0
    ),
    
    OUNCE(
        category = UnitCategory.MASS,
        symbol = "oz",
        displayNameRes = R.string.unit_ounce,
        baseUnitMultiplier = 28.3495,
        precision = 2,
        isMetric = false
    ),
    
    POUND(
        category = UnitCategory.MASS,
        symbol = "lb",
        displayNameRes = R.string.unit_pound,
        baseUnitMultiplier = 453.592,
        precision = 2,
        isMetric = false
    ),
    
    // VOLUME UNITS (base unit: milliliter)
    MILLILITER(
        category = UnitCategory.VOLUME,
        symbol = "mL",
        displayNameRes = R.string.unit_milliliter,
        baseUnitMultiplier = 1.0,
        precision = 0
    ),
    
    LITER(
        category = UnitCategory.VOLUME,
        symbol = "L",
        displayNameRes = R.string.unit_liter,
        baseUnitMultiplier = 1000.0,
        precision = 2
    ),
    
    FLUID_OUNCE(
        category = UnitCategory.VOLUME,
        symbol = "fl oz",
        displayNameRes = R.string.unit_fluid_ounce,
        baseUnitMultiplier = 29.5735,
        precision = 1,
        isMetric = false
    ),
    
    CUP(
        category = UnitCategory.VOLUME,
        symbol = "cup",
        displayNameRes = R.string.unit_cup,
        baseUnitMultiplier = 236.588,
        precision = 2,
        isMetric = false
    ),
    
    TABLESPOON(
        category = UnitCategory.VOLUME,
        symbol = "tbsp",
        displayNameRes = R.string.unit_tablespoon,
        baseUnitMultiplier = 14.7868,
        precision = 1,
        isMetric = false
    ),
    
    TEASPOON(
        category = UnitCategory.VOLUME,
        symbol = "tsp",
        displayNameRes = R.string.unit_teaspoon,
        baseUnitMultiplier = 4.92892,
        precision = 1,
        isMetric = false
    ),
    
    PINT(
        category = UnitCategory.VOLUME,
        symbol = "pt",
        displayNameRes = R.string.unit_pint,
        baseUnitMultiplier = 473.176,
        precision = 2,
        isMetric = false
    ),
    
    QUART(
        category = UnitCategory.VOLUME,
        symbol = "qt",
        displayNameRes = R.string.unit_quart,
        baseUnitMultiplier = 946.353,
        precision = 2,
        isMetric = false
    ),
    
    GALLON(
        category = UnitCategory.VOLUME,
        symbol = "gal",
        displayNameRes = R.string.unit_gallon,
        baseUnitMultiplier = 3785.41,
        precision = 2,
        isMetric = false
    ),
    
    // ENERGY UNITS (base unit: calorie)
    CALORIE(
        category = UnitCategory.ENERGY,
        symbol = "cal",
        displayNameRes = R.string.unit_calorie,
        baseUnitMultiplier = 1.0,
        precision = 0
    ),
    
    KILOCALORIE(
        category = UnitCategory.ENERGY,
        symbol = "kcal",
        displayNameRes = R.string.unit_kilocalorie,
        baseUnitMultiplier = 1000.0,
        precision = 0
    ),
    
    KILOJOULE(
        category = UnitCategory.ENERGY,
        symbol = "kJ",
        displayNameRes = R.string.unit_kilojoule,
        baseUnitMultiplier = 239.006, // 1 kJ = 239.006 cal
        precision = 0
    ),
    
    // SERVING UNITS (base unit: piece)
    PIECE(
        category = UnitCategory.SERVING,
        symbol = "pc",
        displayNameRes = R.string.unit_piece,
        baseUnitMultiplier = 1.0,
        precision = 0
    ),
    
    SLICE(
        category = UnitCategory.SERVING,
        symbol = "slice",
        displayNameRes = R.string.unit_slice,
        baseUnitMultiplier = 1.0,
        precision = 0
    ),
    
    PORTION(
        category = UnitCategory.SERVING,
        symbol = "portion",
        displayNameRes = R.string.unit_portion,
        baseUnitMultiplier = 1.0,
        precision = 1
    ),
    
    SERVING(
        category = UnitCategory.SERVING,
        symbol = "serving",
        displayNameRes = R.string.unit_serving,
        baseUnitMultiplier = 1.0,
        precision = 1
    ),
    
    // PERCENTAGE UNITS (base unit: percent)
    PERCENT(
        category = UnitCategory.PERCENTAGE,
        symbol = "%",
        displayNameRes = R.string.unit_percent,
        baseUnitMultiplier = 1.0,
        precision = 1
    ),
    
    DAILY_VALUE_PERCENT(
        category = UnitCategory.PERCENTAGE,
        symbol = "% DV",
        displayNameRes = R.string.unit_daily_value_percent,
        baseUnitMultiplier = 1.0,
        precision = 0
    );
    
    companion object {
        
        /**
         * Get all units for a specific category
         */
        fun getUnitsForCategory(category: UnitCategory): List<MeasurementUnit> {
            return values().filter { it.category == category }
        }
        
        /**
         * Get metric units for a category
         */
        fun getMetricUnits(category: UnitCategory): List<MeasurementUnit> {
            return getUnitsForCategory(category).filter { it.isMetric }
        }
        
        /**
         * Get imperial units for a category
         */
        fun getImperialUnits(category: UnitCategory): List<MeasurementUnit> {
            return getUnitsForCategory(category).filter { !it.isMetric }
        }
        
        /**
         * Get the base unit for a category
         */
        fun getBaseUnit(category: UnitCategory): MeasurementUnit {
            return when (category) {
                UnitCategory.MASS -> GRAM
                UnitCategory.VOLUME -> MILLILITER
                UnitCategory.ENERGY -> CALORIE
                UnitCategory.SERVING -> PIECE
                UnitCategory.PERCENTAGE -> PERCENT
            }
        }
        
        /**
         * Get default display units for different regions
         */
        fun getDefaultUnitsForRegion(useMetric: Boolean): Map<UnitCategory, MeasurementUnit> {
            return if (useMetric) {
                mapOf(
                    UnitCategory.MASS to GRAM,
                    UnitCategory.VOLUME to MILLILITER,
                    UnitCategory.ENERGY to CALORIE,
                    UnitCategory.SERVING to SERVING,
                    UnitCategory.PERCENTAGE to PERCENT
                )
            } else {
                mapOf(
                    UnitCategory.MASS to OUNCE,
                    UnitCategory.VOLUME to FLUID_OUNCE,
                    UnitCategory.ENERGY to CALORIE,
                    UnitCategory.SERVING to SERVING,
                    UnitCategory.PERCENTAGE to PERCENT
                )
            }
        }
    }
    
    /**
     * Convert a value from this unit to another unit in the same category
     */
    fun convertTo(value: Double, targetUnit: MeasurementUnit): Double {
        require(this.category == targetUnit.category) {
            "Cannot convert between different unit categories: ${this.category} -> ${targetUnit.category}"
        }
        
        // Convert to base unit, then to target unit
        val baseValue = value * this.baseUnitMultiplier
        return baseValue / targetUnit.baseUnitMultiplier
    }
    
    /**
     * Convert a value to the base unit for this category
     */
    fun toBaseUnit(value: Double): Double {
        return value * baseUnitMultiplier
    }
    
    /**
     * Convert a value from the base unit to this unit
     */
    fun fromBaseUnit(value: Double): Double {
        return value / baseUnitMultiplier
    }
    
    /**
     * Format a value with appropriate precision for this unit
     */
    fun formatValue(value: Double): String {
        return when (precision) {
            0 -> value.roundToInt().toString()
            else -> "%.${precision}f".format(value)
        }
    }
    
    /**
     * Get display string with value and unit symbol
     */
    fun getDisplayString(value: Double): String {
        return "${formatValue(value)} $symbol"
    }
    
    /**
     * Check if this unit is suitable for the given value range
     * (e.g., use mg for small values, kg for large values)
     */
    fun isSuitableForValue(value: Double): Boolean {
        val baseValue = toBaseUnit(value)
        return when (category) {
            UnitCategory.MASS -> when (this) {
                MICROGRAM -> baseValue < 0.001
                MILLIGRAM -> baseValue >= 0.001 && baseValue < 1.0
                GRAM -> baseValue >= 1.0 && baseValue < 1000.0
                KILOGRAM -> baseValue >= 1000.0
                OUNCE -> baseValue >= 14.0 && baseValue < 450.0
                POUND -> baseValue >= 450.0
                else -> true
            }
            UnitCategory.VOLUME -> when (this) {
                MILLILITER -> baseValue < 1000.0
                LITER -> baseValue >= 1000.0
                TEASPOON -> baseValue >= 2.0 && baseValue < 15.0
                TABLESPOON -> baseValue >= 7.0 && baseValue < 60.0
                FLUID_OUNCE -> baseValue >= 15.0 && baseValue < 240.0
                CUP -> baseValue >= 120.0 && baseValue < 950.0
                else -> true
            }
            else -> true
        }
    }
    
    /**
     * Get the best unit for displaying a value (automatic unit selection)
     */
    fun getBestUnitForValue(value: Double): MeasurementUnit {
        val unitsInCategory = getUnitsForCategory(category)
            .filter { it.isMetric == this.isMetric }
            .sortedBy { it.baseUnitMultiplier }
        
        return unitsInCategory.firstOrNull { it.isSuitableForValue(value) } ?: this
    }
}

/**
 * Data class for representing a measured value with its unit
 */
data class MeasuredValue(
    val value: Double,
    val unit: MeasurementUnit
) {
    
    /**
     * Convert this measured value to a different unit
     */
    fun convertTo(targetUnit: MeasurementUnit): MeasuredValue {
        val convertedValue = unit.convertTo(value, targetUnit)
        return MeasuredValue(convertedValue, targetUnit)
    }
    
    /**
     * Get the value in base units for calculations
     */
    fun getBaseValue(): Double {
        return unit.toBaseUnit(value)
    }
    
    /**
     * Format for display
     */
    fun getDisplayString(): String {
        return unit.getDisplayString(value)
    }
    
    /**
     * Add two measured values (converts to common unit if needed)
     */
    operator fun plus(other: MeasuredValue): MeasuredValue {
        require(unit.category == other.unit.category) {
            "Cannot add values with different unit categories"
        }
        
        val otherConverted = other.convertTo(unit)
        return MeasuredValue(value + otherConverted.value, unit)
    }
    
    /**
     * Subtract two measured values
     */
    operator fun minus(other: MeasuredValue): MeasuredValue {
        require(unit.category == other.unit.category) {
            "Cannot subtract values with different unit categories"
        }
        
        val otherConverted = other.convertTo(unit)
        return MeasuredValue(value - otherConverted.value, unit)
    }
    
    /**
     * Multiply by a scalar
     */
    operator fun times(scalar: Double): MeasuredValue {
        return MeasuredValue(value * scalar, unit)
    }
    
    /**
     * Divide by a scalar
     */
    operator fun div(scalar: Double): MeasuredValue {
        return MeasuredValue(value / scalar, unit)
    }
    
    /**
     * Check if this value is greater than another
     */
    operator fun compareTo(other: MeasuredValue): Int {
        require(unit.category == other.unit.category) {
            "Cannot compare values with different unit categories"
        }
        
        val thisBase = getBaseValue()
        val otherBase = other.getBaseValue()
        return thisBase.compareTo(otherBase)
    }
}

/**
 * Extension functions for common conversions
 */
fun Double.grams() = MeasuredValue(this, MeasurementUnit.GRAM)
fun Double.kilograms() = MeasuredValue(this, MeasurementUnit.KILOGRAM)
fun Double.milligrams() = MeasuredValue(this, MeasurementUnit.MILLIGRAM)
fun Double.ounces() = MeasuredValue(this, MeasurementUnit.OUNCE)
fun Double.pounds() = MeasuredValue(this, MeasurementUnit.POUND)

fun Double.milliliters() = MeasuredValue(this, MeasurementUnit.MILLILITER)
fun Double.liters() = MeasuredValue(this, MeasurementUnit.LITER)
fun Double.cups() = MeasuredValue(this, MeasurementUnit.CUP)
fun Double.tablespoons() = MeasuredValue(this, MeasurementUnit.TABLESPOON)
fun Double.teaspoons() = MeasuredValue(this, MeasurementUnit.TEASPOON)

fun Double.calories() = MeasuredValue(this, MeasurementUnit.CALORIE)
fun Double.kilocalories() = MeasuredValue(this, MeasurementUnit.KILOCALORIE)
fun Double.kilojoules() = MeasuredValue(this, MeasurementUnit.KILOJOULE)

fun Double.pieces() = MeasuredValue(this, MeasurementUnit.PIECE)
fun Double.servings() = MeasuredValue(this, MeasurementUnit.SERVING)
fun Double.percent() = MeasuredValue(this, MeasurementUnit.PERCENT)
