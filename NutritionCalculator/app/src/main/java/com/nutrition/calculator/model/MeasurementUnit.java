package com.nutrition.calculator.model;

import java.util.*;
import java.text.DecimalFormat;

/**
 * Represents a measurement unit used in nutrition calculations.
 * Supports different unit types (weight, volume, count) with conversion capabilities.
 */
public class MeasurementUnit {
    
    /**
     * Enumeration of supported unit types
     */
    public enum UnitType {
        WEIGHT,    // grams, kilograms, ounces, pounds
        VOLUME,    // milliliters, liters, cups, tablespoons, teaspoons
        COUNT      // pieces, servings, items
    }
    
    private String symbol;
    private String name;
    private UnitType type;
    private double baseConversionFactor;
    private String singularName;
    private String pluralName;
    
    /**
     * Constructor for MeasurementUnit
     * 
     * @param symbol Short symbol for the unit (e.g., "g", "ml", "cup")
     * @param name Display name for the unit (e.g., "grams", "milliliters")
     * @param type The type of measurement (WEIGHT, VOLUME, COUNT)
     * @param baseConversionFactor Conversion factor to base unit for this type
     */
    public MeasurementUnit(String symbol, String name, UnitType type, double baseConversionFactor) {
        validateParameters(symbol, name, type, baseConversionFactor);
        
        this.symbol = symbol.trim();
        this.name = name.trim();
        this.type = type;
        this.baseConversionFactor = baseConversionFactor;
        
        // Auto-generate singular/plural forms
        generateSingularPluralForms(name);
    }
    
    /**
     * Constructor with explicit singular and plural forms
     */
    public MeasurementUnit(String symbol, String name, UnitType type, double baseConversionFactor,
                          String singularName, String pluralName) {
        validateParameters(symbol, name, type, baseConversionFactor);
        
        this.symbol = symbol.trim();
        this.name = name.trim();
        this.type = type;
        this.baseConversionFactor = baseConversionFactor;
        this.singularName = singularName != null ? singularName.trim() : name.trim();
        this.pluralName = pluralName != null ? pluralName.trim() : name.trim();
    }
    
    /**
     * Validate constructor parameters
     */
    private void validateParameters(String symbol, String name, UnitType type, double baseConversionFactor) {
        if (symbol == null || symbol.trim().isEmpty()) {
            throw new IllegalArgumentException("Symbol cannot be null or empty");
        }
        
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        
        if (type == null) {
            throw new IllegalArgumentException("Unit type cannot be null");
        }
        
        if (baseConversionFactor <= 0) {
            throw new IllegalArgumentException("Base conversion factor must be positive");
        }
    }
    
    /**
     * Generate singular and plural forms from name
     */
    private void generateSingularPluralForms(String name) {
        String lowerName = name.toLowerCase();
        
        if (lowerName.endsWith("s")) {
            // Name is likely already plural
            this.pluralName = name;
            // Try to create singular by removing 's'
            if (lowerName.endsWith("ies")) {
                this.singularName = name.substring(0, name.length() - 3) + "y";
            } else if (lowerName.endsWith("es")) {
                this.singularName = name.substring(0, name.length() - 2);
            } else {
                this.singularName = name.substring(0, name.length() - 1);
            }
        } else {
            // Name is likely singular
            this.singularName = name;
            // Create plural form
            if (lowerName.endsWith("y")) {
                this.pluralName = name.substring(0, name.length() - 1) + "ies";
            } else if (lowerName.endsWith("s") || lowerName.endsWith("sh") || 
                      lowerName.endsWith("ch") || lowerName.endsWith("x") || lowerName.endsWith("z")) {
                this.pluralName = name + "es";
            } else {
                this.pluralName = name + "s";
            }
        }
    }
    
    /**
     * Convert amount from this unit to base unit for the type
     */
    public double convertToBaseUnit(double amount) {
        if (!isValidAmount(amount)) {
            throw new IllegalArgumentException("Amount must be non-negative and finite");
        }
        return amount * baseConversionFactor;
    }
    
    /**
     * Convert amount from base unit to this unit
     */
    public double convertFromBaseUnit(double baseAmount) {
        if (!isValidAmount(baseAmount)) {
            throw new IllegalArgumentException("Base amount must be non-negative and finite");
        }
        return baseAmount / baseConversionFactor;
    }
    
    /**
     * Check if this unit is compatible with another unit (same type)
     */
    public boolean isCompatibleWith(MeasurementUnit other) {
        return other != null && this.type == other.type;
    }
    
    /**
     * Convert amount from this unit to another compatible unit
     */
    public static double convert(double amount, MeasurementUnit fromUnit, MeasurementUnit toUnit) {
        if (fromUnit == null || toUnit == null) {
            throw new IllegalArgumentException("Units cannot be null");
        }
        
        if (!fromUnit.isCompatibleWith(toUnit)) {
            throw new IllegalArgumentException(
                "Cannot convert between incompatible unit types: " + 
                fromUnit.type + " and " + toUnit.type
            );
        }
        
        // Convert to base unit, then to target unit
        double baseAmount = fromUnit.convertToBaseUnit(amount);
        return toUnit.convertFromBaseUnit(baseAmount);
    }
    
    /**
     * Format amount with unit symbol
     */
    public String formatAmount(double amount) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(amount) + " " + symbol;
    }
    
    /**
     * Format amount with full unit name (singular/plural as appropriate)
     */
    public String formatAmountWithName(double amount) {
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedAmount = df.format(amount);
        String unitName = (amount == 1.0) ? singularName : pluralName;
        return formattedAmount + " " + unitName;
    }
    
    /**
     * Validate that an amount is valid for calculations
     */
    public static boolean isValidAmount(double amount) {
        return amount >= 0 && Double.isFinite(amount);
    }
    
    /**
     * Get standard weight units
     */
    public static List<MeasurementUnit> getStandardWeightUnits() {
        return Arrays.asList(
            new MeasurementUnit("g", "grams", UnitType.WEIGHT, 1.0, "gram", "grams"),
            new MeasurementUnit("kg", "kilograms", UnitType.WEIGHT, 1000.0, "kilogram", "kilograms"),
            new MeasurementUnit("oz", "ounces", UnitType.WEIGHT, 28.3495, "ounce", "ounces"),
            new MeasurementUnit("lb", "pounds", UnitType.WEIGHT, 453.592, "pound", "pounds"),
            new MeasurementUnit("mg", "milligrams", UnitType.WEIGHT, 0.001, "milligram", "milligrams")
        );
    }
    
    /**
     * Get standard volume units
     */
    public static List<MeasurementUnit> getStandardVolumeUnits() {
        return Arrays.asList(
            new MeasurementUnit("ml", "milliliters", UnitType.VOLUME, 1.0, "milliliter", "milliliters"),
            new MeasurementUnit("l", "liters", UnitType.VOLUME, 1000.0, "liter", "liters"),
            new MeasurementUnit("cup", "cups", UnitType.VOLUME, 236.588, "cup", "cups"),
            new MeasurementUnit("tbsp", "tablespoons", UnitType.VOLUME, 14.7868, "tablespoon", "tablespoons"),
            new MeasurementUnit("tsp", "teaspoons", UnitType.VOLUME, 4.92892, "teaspoon", "teaspoons"),
            new MeasurementUnit("fl oz", "fluid ounces", UnitType.VOLUME, 29.5735, "fluid ounce", "fluid ounces"),
            new MeasurementUnit("pt", "pints", UnitType.VOLUME, 473.176, "pint", "pints"),
            new MeasurementUnit("qt", "quarts", UnitType.VOLUME, 946.353, "quart", "quarts"),
            new MeasurementUnit("gal", "gallons", UnitType.VOLUME, 3785.41, "gallon", "gallons")
        );
    }
    
    /**
     * Get standard count units
     */
    public static List<MeasurementUnit> getStandardCountUnits() {
        return Arrays.asList(
            new MeasurementUnit("pcs", "pieces", UnitType.COUNT, 1.0, "piece", "pieces"),
            new MeasurementUnit("serving", "servings", UnitType.COUNT, 1.0, "serving", "servings"),
            new MeasurementUnit("item", "items", UnitType.COUNT, 1.0, "item", "items"),
            new MeasurementUnit("each", "each", UnitType.COUNT, 1.0, "each", "each")
        );
    }
    
    /**
     * Get all standard units
     */
    public static List<MeasurementUnit> getAllStandardUnits() {
        List<MeasurementUnit> allUnits = new ArrayList<>();
        allUnits.addAll(getStandardWeightUnits());
        allUnits.addAll(getStandardVolumeUnits());
        allUnits.addAll(getStandardCountUnits());
        return allUnits;
    }
    
    /**
     * Find unit by symbol
     */
    public static Optional<MeasurementUnit> findBySymbol(String symbol) {
        if (symbol == null) {
            return Optional.empty();
        }
        
        return getAllStandardUnits().stream()
            .filter(unit -> unit.getSymbol().equalsIgnoreCase(symbol.trim()))
            .findFirst();
    }
    
    /**
     * Find units by type
     */
    public static List<MeasurementUnit> findByType(UnitType type) {
        if (type == null) {
            return Collections.emptyList();
        }
        
        return getAllStandardUnits().stream()
            .filter(unit -> unit.getType() == type)
            .collect(ArrayList::new, (list, item) -> list.add(item), ArrayList::addAll);
    }
    
    /**
     * Get the base unit for a given type
     */
    public static MeasurementUnit getBaseUnit(UnitType type) {
        switch (type) {
            case WEIGHT:
                return new MeasurementUnit("g", "grams", UnitType.WEIGHT, 1.0);
            case VOLUME:
                return new MeasurementUnit("ml", "milliliters", UnitType.VOLUME, 1.0);
            case COUNT:
                return new MeasurementUnit("pcs", "pieces", UnitType.COUNT, 1.0);
            default:
                throw new IllegalArgumentException("Unknown unit type: " + type);
        }
    }
    
    /**
     * Round conversion result to reasonable precision for nutrition calculations
     */
    public static double roundForNutrition(double value) {
        // Round to 2 decimal places for nutrition calculations
        return Math.round(value * 100.0) / 100.0;
    }
    
    /**
     * Create a custom unit
     */
    public static MeasurementUnit createCustomUnit(String symbol, String name, UnitType type, 
                                                   double conversionToBase) {
        return new MeasurementUnit(symbol, name, type, conversionToBase);
    }
    
    // Getters
    
    public String getSymbol() {
        return symbol;
    }
    
    public String getName() {
        return name;
    }
    
    public UnitType getType() {
        return type;
    }
    
    public double getBaseConversionFactor() {
        return baseConversionFactor;
    }
    
    public String getSingularName() {
        return singularName;
    }
    
    public String getPluralName() {
        return pluralName;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementUnit that = (MeasurementUnit) o;
        return Double.compare(that.baseConversionFactor, baseConversionFactor) == 0 &&
               Objects.equals(symbol, that.symbol) &&
               Objects.equals(name, that.name) &&
               type == that.type;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(symbol, name, type, baseConversionFactor);
    }
    
    @Override
    public String toString() {
        return "MeasurementUnit{" +
               "symbol='" + symbol + '\'' +
               ", name='" + name + '\'' +
               ", type=" + type +
               ", baseConversionFactor=" + baseConversionFactor +
               '}';
    }
}
