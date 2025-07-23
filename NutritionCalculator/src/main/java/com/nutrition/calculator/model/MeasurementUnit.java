package com.nutrition.calculator.model;

/**
 * MeasurementUnit - Enum representing cooking measurement units
 * 
 * This enum provides standard cooking measurement units with conversion
 * capabilities to grams (the standardized base unit for nutrition calculations).
 * It supports both metric and imperial units commonly used in recipes.
 * 
 * @author NutritionCalculator Development Team
 * @version 1.0
 */
public enum MeasurementUnit {
    
    // Weight Units (most accurate for nutrition)
    GRAM("g", "gram", "grams", 1.0),
    KILOGRAM("kg", "kilogram", "kilograms", 1000.0),
    OUNCE("oz", "ounce", "ounces", 28.3495),
    POUND("lb", "pound", "pounds", 453.592),
    
    // Volume Units (liquid)
    MILLILITER("ml", "milliliter", "milliliters", 1.0), // 1ml water = 1g
    LITER("L", "liter", "liters", 1000.0),
    FLUID_OUNCE("fl oz", "fluid ounce", "fluid ounces", 29.5735),
    CUP("cup", "cup", "cups", 236.588),
    PINT("pt", "pint", "pints", 473.176),
    QUART("qt", "quart", "quarts", 946.353),
    GALLON("gal", "gallon", "gallons", 3785.41),
    
    // Volume Units (dry/cooking)
    TEASPOON("tsp", "teaspoon", "teaspoons", 4.92892), // ~5ml
    TABLESPOON("tbsp", "tablespoon", "tablespoons", 14.7868), // ~15ml
    
    // Count/Piece Units (approximate weights)
    PIECE("pc", "piece", "pieces", 100.0), // Default 100g per piece
    SLICE("slice", "slice", "slices", 30.0), // Default 30g per slice
    CLOVE("clove", "clove", "cloves", 3.0), // Garlic clove ~3g
    LARGE("large", "large", "large", 150.0), // Large fruit/vegetable
    MEDIUM("medium", "medium", "medium", 100.0), // Medium fruit/vegetable
    SMALL("small", "small", "small", 50.0), // Small fruit/vegetable
    
    // Special Units
    PINCH("pinch", "pinch", "pinches", 0.5), // Very small amount
    DASH("dash", "dash", "dashes", 1.0), // Small amount
    BUNCH("bunch", "bunch", "bunches", 100.0), // Herbs/greens
    HEAD("head", "head", "heads", 500.0), // Lettuce, cabbage
    STALK("stalk", "stalk", "stalks", 40.0), // Celery
    SPRIG("sprig", "sprig", "sprigs", 5.0), // Fresh herbs
    
    // Specialty Baking Units
    PACKAGE("pkg", "package", "packages", 250.0), // Standard package
    CAN("can", "can", "cans", 400.0), // Standard can
    JAR("jar", "jar", "jars", 300.0), // Standard jar
    BOTTLE("bottle", "bottle", "bottles", 500.0), // Standard bottle
    
    // International Units
    STONE("st", "stone", "stones", 6350.29); // British unit
    
    private final String abbreviation;
    private final String singular;
    private final String plural;
    private final double gramsPerUnit;
    
    /**
     * Constructor for measurement unit.
     * 
     * @param abbreviation Short form (e.g., "g", "kg")
     * @param singular Singular form (e.g., "gram")
     * @param plural Plural form (e.g., "grams")
     * @param gramsPerUnit Conversion factor to grams
     */
    MeasurementUnit(String abbreviation, String singular, String plural, double gramsPerUnit) {
        this.abbreviation = abbreviation;
        this.singular = singular;
        this.plural = plural;
        this.gramsPerUnit = gramsPerUnit;
    }
    
    // Getters
    
    public String getAbbreviation() {
        return abbreviation;
    }
    
    public String getSingular() {
        return singular;
    }
    
    public String getPlural() {
        return plural;
    }
    
    public double getGramsPerUnit() {
        return gramsPerUnit;
    }
    
    /**
     * Get display name based on amount (singular/plural).
     * 
     * @param amount Amount to determine singular/plural
     * @return Appropriate display name
     */
    public String getDisplayName(double amount) {
        return Math.abs(amount) == 1.0 ? singular : plural;
    }
    
    /**
     * Get display name (defaults to plural).
     * 
     * @return Plural display name
     */
    public String getDisplayName() {
        return plural;
    }
    
    // Conversion Methods
    
    /**
     * Convert amount in this unit to grams.
     * 
     * @param amount Amount in this unit
     * @return Amount in grams
     */
    public double convertToGrams(double amount) {
        return amount * gramsPerUnit;
    }
    
    /**
     * Convert grams to this unit.
     * 
     * @param grams Amount in grams
     * @return Amount in this unit
     */
    public double convertFromGrams(double grams) {
        return grams / gramsPerUnit;
    }
    
    /**
     * Convert from one unit to another.
     * 
     * @param amount Amount in source unit
     * @param sourceUnit Source measurement unit
     * @param targetUnit Target measurement unit
     * @return Amount in target unit
     */
    public static double convert(double amount, MeasurementUnit sourceUnit, MeasurementUnit targetUnit) {
        if (sourceUnit == null || targetUnit == null) {
            return amount;
        }
        
        if (sourceUnit == targetUnit) {
            return amount;
        }
        
        double grams = sourceUnit.convertToGrams(amount);
        return targetUnit.convertFromGrams(grams);
    }
    
    // Unit Category Methods
    
    /**
     * Check if this is a weight unit.
     * 
     * @return true if weight unit
     */
    public boolean isWeightUnit() {
        return this == GRAM || this == KILOGRAM || this == OUNCE || this == POUND || this == STONE;
    }
    
    /**
     * Check if this is a volume unit.
     * 
     * @return true if volume unit
     */
    public boolean isVolumeUnit() {
        return this == MILLILITER || this == LITER || this == FLUID_OUNCE || 
               this == CUP || this == PINT || this == QUART || this == GALLON ||
               this == TEASPOON || this == TABLESPOON;
    }
    
    /**
     * Check if this is a count/piece unit.
     * 
     * @return true if count unit
     */
    public boolean isCountUnit() {
        return this == PIECE || this == SLICE || this == CLOVE || 
               this == LARGE || this == MEDIUM || this == SMALL ||
               this == BUNCH || this == HEAD || this == STALK || this == SPRIG;
    }
    
    /**
     * Check if this unit is metric.
     * 
     * @return true if metric unit
     */
    public boolean isMetric() {
        return this == GRAM || this == KILOGRAM || this == MILLILITER || this == LITER;
    }
    
    /**
     * Check if this unit is imperial.
     * 
     * @return true if imperial unit
     */
    public boolean isImperial() {
        return this == OUNCE || this == POUND || this == FLUID_OUNCE || 
               this == CUP || this == PINT || this == QUART || this == GALLON ||
               this == TEASPOON || this == TABLESPOON || this == STONE;
    }
    
    // Utility Methods
    
    /**
     * Parse measurement unit from string.
     * 
     * @param unitString String representation of unit
     * @return MeasurementUnit or null if not found
     */
    public static MeasurementUnit fromString(String unitString) {
        if (unitString == null || unitString.trim().isEmpty()) {
            return GRAM; // Default unit
        }
        
        String normalized = unitString.trim().toLowerCase();
        
        for (MeasurementUnit unit : values()) {
            if (unit.abbreviation.toLowerCase().equals(normalized) ||
                unit.singular.toLowerCase().equals(normalized) ||
                unit.plural.toLowerCase().equals(normalized)) {
                return unit;
            }
        }
        
        // Handle common variations
        switch (normalized) {
            case "gm": case "gr": return GRAM;
            case "kilo": case "kilos": return KILOGRAM;
            case "oz.": case "ozs": return OUNCE;
            case "lbs": case "lb.": case "#": return POUND;
            case "ml.": case "mls": return MILLILITER;
            case "l.": case "lt": case "ltr": return LITER;
            case "fl.oz": case "floz": return FLUID_OUNCE;
            case "c": case "c.": case "cups": return CUP;
            case "pt.": case "pts": return PINT;
            case "qt.": case "qts": return QUART;
            case "gal.": case "gals": return GALLON;
            case "t": case "t.": case "tsp.": return TEASPOON;
            case "T": case "T.": case "tbsp.": case "tbs": return TABLESPOON;
            case "pcs": case "pieces": return PIECE;
            case "slices": return SLICE;
            case "cloves": return CLOVE;
            case "lg": case "large": return LARGE;
            case "med": case "medium": return MEDIUM;
            case "sm": case "small": return SMALL;
            case "pinches": return PINCH;
            case "dashes": return DASH;
            case "bunches": return BUNCH;
            case "heads": return HEAD;
            case "stalks": return STALK;
            case "sprigs": return SPRIG;
            case "pkg.": case "pkgs": case "packages": return PACKAGE;
            case "cans": return CAN;
            case "jars": return JAR;
            case "bottles": return BOTTLE;
            default: return null;
        }
    }
    
    /**
     * Get all weight units.
     * 
     * @return Array of weight units
     */
    public static MeasurementUnit[] getWeightUnits() {
        return new MeasurementUnit[]{GRAM, KILOGRAM, OUNCE, POUND};
    }
    
    /**
     * Get all volume units.
     * 
     * @return Array of volume units
     */
    public static MeasurementUnit[] getVolumeUnits() {
        return new MeasurementUnit[]{
            MILLILITER, LITER, FLUID_OUNCE, CUP, PINT, QUART, GALLON,
            TEASPOON, TABLESPOON
        };
    }
    
    /**
     * Get all count/piece units.
     * 
     * @return Array of count units
     */
    public static MeasurementUnit[] getCountUnits() {
        return new MeasurementUnit[]{
            PIECE, SLICE, CLOVE, LARGE, MEDIUM, SMALL,
            BUNCH, HEAD, STALK, SPRIG
        };
    }
    
    /**
     * Get common cooking units.
     * 
     * @return Array of common units
     */
    public static MeasurementUnit[] getCommonUnits() {
        return new MeasurementUnit[]{
            GRAM, KILOGRAM, CUP, TABLESPOON, TEASPOON, 
            PIECE, OUNCE, POUND, MILLILITER, LITER
        };
    }
    
    /**
     * Get metric units only.
     * 
     * @return Array of metric units
     */
    public static MeasurementUnit[] getMetricUnits() {
        return new MeasurementUnit[]{GRAM, KILOGRAM, MILLILITER, LITER};
    }
    
    /**
     * Get imperial units only.
     * 
     * @return Array of imperial units
     */
    public static MeasurementUnit[] getImperialUnits() {
        return new MeasurementUnit[]{
            OUNCE, POUND, FLUID_OUNCE, CUP, PINT, QUART, GALLON,
            TEASPOON, TABLESPOON
        };
    }
    
    /**
     * Format amount with appropriate unit display.
     * 
     * @param amount Amount value
     * @return Formatted string
     */
    public String formatAmount(double amount) {
        String unitDisplay = getDisplayName(amount);
        
        if (amount == Math.floor(amount)) {
            return String.format("%.0f %s", amount, unitDisplay);
        } else if (amount < 1.0) {
            return String.format("%.2f %s", amount, unitDisplay);
        } else {
            return String.format("%.1f %s", amount, unitDisplay);
        }
    }
    
    /**
     * Get best unit for displaying a gram amount.
     * 
     * @param grams Amount in grams
     * @param preferMetric Whether to prefer metric units
     * @return Best unit for display
     */
    public static MeasurementUnit getBestUnitForGrams(double grams, boolean preferMetric) {
        if (grams <= 0) return GRAM;
        
        if (preferMetric) {
            if (grams >= 1000) return KILOGRAM;
            if (grams >= 1) return GRAM;
            return GRAM; // Still use grams for small amounts
        } else {
            if (grams >= 453.592) return POUND;
            if (grams >= 28.3495) return OUNCE;
            return GRAM;
        }
    }
    
    @Override
    public String toString() {
        return plural;
    }
}
