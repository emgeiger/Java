package com.nutrition.calculator.model;

/**
 * Enumeration of supported measurement units for ingredients.
 * Designed for easy expansion and cross-platform compatibility.
 */
public enum MeasurementUnit {
    // Weight measurements
    GRAMS("g", "Grams", MeasurementType.WEIGHT),
    OUNCES("oz", "Ounces", MeasurementType.WEIGHT),
    POUNDS("lb", "Pounds", MeasurementType.WEIGHT),
    KILOGRAMS("kg", "Kilograms", MeasurementType.WEIGHT),
    
    // Volume measurements  
    MILLILITERS("mL", "Milliliters", MeasurementType.VOLUME),
    FLUID_OUNCES("fl oz", "Fluid Ounces", MeasurementType.VOLUME),
    CUPS("cup", "Cups", MeasurementType.VOLUME),
    TABLESPOONS("tbsp", "Tablespoons", MeasurementType.VOLUME),
    TEASPOONS("tsp", "Teaspoons", MeasurementType.VOLUME),
    LITERS("L", "Liters", MeasurementType.VOLUME),
    
    // Count measurements
    PIECES("pc", "Pieces", MeasurementType.COUNT),
    ITEMS("item", "Items", MeasurementType.COUNT);
    
    private final String abbreviation;
    private final String fullName;
    private final MeasurementType type;
    
    MeasurementUnit(String abbreviation, String fullName, MeasurementType type) {
        this.abbreviation = abbreviation;
        this.fullName = fullName;
        this.type = type;
    }
    
    public String getAbbreviation() {
        return abbreviation;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public MeasurementType getType() {
        return type;
    }
    
    /**
     * Get display name for UI components
     * @return Formatted display name
     */
    public String getDisplayName() {
        return String.format("%s (%s)", fullName, abbreviation);
    }
    
    /**
     * Find measurement unit by abbreviation
     * @param abbreviation The abbreviation to search for
     * @return MeasurementUnit if found, null otherwise
     */
    public static MeasurementUnit fromAbbreviation(String abbreviation) {
        for (MeasurementUnit unit : values()) {
            if (unit.abbreviation.equalsIgnoreCase(abbreviation)) {
                return unit;
            }
        }
        return null;
    }
    
    /**
     * Get all units of a specific type
     * @param type The measurement type to filter by
     * @return Array of units matching the type
     */
    public static MeasurementUnit[] getByType(MeasurementType type) {
        return java.util.Arrays.stream(values())
                .filter(unit -> unit.type == type)
                .toArray(MeasurementUnit[]::new);
    }
    
    public enum MeasurementType {
        WEIGHT, VOLUME, COUNT
    }
}
