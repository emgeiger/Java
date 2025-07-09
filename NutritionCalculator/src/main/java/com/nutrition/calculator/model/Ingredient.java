package com.nutrition.calculator.model;

import java.util.Objects;

/**
 * Represents a food ingredient with nutritional information and measurement unit.
 * Designed for cross-platform compatibility (future Android/Kotlin port).
 */
public class Ingredient {
    private String name;
    private double caloriesPerUnit;
    private MeasurementUnit unit;
    private String description;
    
    // Default constructor for JSON deserialization
    public Ingredient() {}
    
    public Ingredient(String name, double caloriesPerUnit, MeasurementUnit unit) {
        this.name = name;
        this.caloriesPerUnit = caloriesPerUnit;
        this.unit = unit;
        this.description = "";
    }
    
    public Ingredient(String name, double caloriesPerUnit, MeasurementUnit unit, String description) {
        this.name = name;
        this.caloriesPerUnit = caloriesPerUnit;
        this.unit = unit;
        this.description = description;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getCaloriesPerUnit() {
        return caloriesPerUnit;
    }
    
    public void setCaloriesPerUnit(double caloriesPerUnit) {
        this.caloriesPerUnit = caloriesPerUnit;
    }
    
    public MeasurementUnit getUnit() {
        return unit;
    }
    
    public void setUnit(MeasurementUnit unit) {
        this.unit = unit;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Calculate calories for a specific quantity of this ingredient
     * @param quantity Amount of ingredient
     * @return Total calories for the given quantity
     */
    public double calculateCalories(double quantity) {
        return quantity * caloriesPerUnit;
    }
    
    /**
     * Validate that the ingredient has all required fields
     * @return true if ingredient is valid
     */
    public boolean isValid() {
        return name != null && !name.trim().isEmpty() && 
               caloriesPerUnit >= 0 && 
               unit != null;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Double.compare(that.caloriesPerUnit, caloriesPerUnit) == 0 &&
                Objects.equals(name, that.name) &&
                unit == that.unit &&
                Objects.equals(description, that.description);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, caloriesPerUnit, unit, description);
    }
    
    @Override
    public String toString() {
        return String.format("%s (%.1f cal/%s)", name, caloriesPerUnit, unit.getAbbreviation());
    }
}
