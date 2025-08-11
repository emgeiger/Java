package com.nutrition.calculator.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents an ingredient as used in a recipe, including the specific amount
 * and measurement unit. This class links a base ingredient with a quantity
 * for recipe calculations and nutritional analysis.
 */
public class RecipeIngredient {
    
    private String id;
    private String ingredientId;
    private Ingredient ingredient;
    private double amount;
    private MeasurementUnit unit;
    private String notes;
    private boolean isOptional;
    private String preparation; // e.g., "chopped", "diced", "sliced"
    private int displayOrder;
    
    /**
     * Default constructor
     */
    public RecipeIngredient() {
        this.id = UUID.randomUUID().toString();
        this.isOptional = false;
        this.displayOrder = 0;
    }
    
    /**
     * Constructor with basic parameters
     */
    public RecipeIngredient(Ingredient ingredient, double amount, MeasurementUnit unit) {
        this();
        this.ingredient = ingredient;
        this.ingredientId = ingredient != null ? ingredient.getId() : null;
        this.amount = amount;
        this.unit = unit;
        validateParameters();
    }
    
    /**
     * Full constructor
     */
    public RecipeIngredient(String id, Ingredient ingredient, double amount, 
                           MeasurementUnit unit, String notes, boolean isOptional, 
                           String preparation, int displayOrder) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.ingredient = ingredient;
        this.ingredientId = ingredient != null ? ingredient.getId() : null;
        this.amount = amount;
        this.unit = unit;
        this.notes = notes;
        this.isOptional = isOptional;
        this.preparation = preparation;
        this.displayOrder = displayOrder;
        validateParameters();
    }
    
    /**
     * Constructor for database restoration (when ingredient is loaded separately)
     */
    public RecipeIngredient(String id, String ingredientId, double amount, 
                           String unitSymbol, String notes, boolean isOptional, 
                           String preparation, int displayOrder) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.ingredientId = ingredientId;
        this.amount = amount;
        // Unit will need to be resolved separately
        this.notes = notes;
        this.isOptional = isOptional;
        this.preparation = preparation;
        this.displayOrder = displayOrder;
        validateBasicParameters();
    }
    
    /**
     * Validate constructor parameters
     */
    private void validateParameters() {
        validateBasicParameters();
        
        if (ingredient == null) {
            throw new IllegalArgumentException("Ingredient cannot be null");
        }
        
        if (unit == null) {
            throw new IllegalArgumentException("Measurement unit cannot be null");
        }
        
        // Check unit compatibility with ingredient
        if (!ingredient.isCompatibleWithUnit(unit)) {
            throw new IllegalArgumentException(
                "Unit " + unit.getSymbol() + " is not compatible with ingredient " + ingredient.getName()
            );
        }
    }
    
    private void validateBasicParameters() {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        
        if (displayOrder < 0) {
            throw new IllegalArgumentException("Display order cannot be negative");
        }
    }
    
    /**
     * Calculate the nutritional information for this recipe ingredient
     * based on the specified amount and unit
     */
    public NutritionalInfo calculateNutritionalInfo() {
        if (ingredient == null || unit == null) {
            return new NutritionalInfo.Builder().build();
        }
        
        return ingredient.calculateNutritionForAmount(amount, unit);
    }
    
    /**
     * Calculate calories for this recipe ingredient
     */
    public double calculateCalories() {
        if (ingredient == null || unit == null) {
            return 0.0;
        }
        
        return ingredient.calculateCaloriesForAmount(amount, unit);
    }
    
    /**
     * Scale this ingredient for a different recipe serving size
     */
    public RecipeIngredient scaleForServings(double scaleFactor) {
        if (scaleFactor <= 0) {
            throw new IllegalArgumentException("Scale factor must be positive");
        }
        
        return new RecipeIngredient(
            UUID.randomUUID().toString(),
            this.ingredient,
            this.amount * scaleFactor,
            this.unit,
            this.notes,
            this.isOptional,
            this.preparation,
            this.displayOrder
        );
    }
    
    /**
     * Convert this ingredient to a different compatible unit
     */
    public RecipeIngredient convertToUnit(MeasurementUnit newUnit) {
        if (ingredient == null || unit == null) {
            throw new IllegalStateException("Cannot convert without ingredient and current unit");
        }
        
        if (!ingredient.isCompatibleWithUnit(newUnit)) {
            throw new IllegalArgumentException("New unit is not compatible with ingredient");
        }
        
        double convertedAmount = MeasurementUnit.convert(amount, unit, newUnit);
        
        return new RecipeIngredient(
            this.id,
            this.ingredient,
            convertedAmount,
            newUnit,
            this.notes,
            this.isOptional,
            this.preparation,
            this.displayOrder
        );
    }
    
    /**
     * Get display string for this recipe ingredient
     */
    public String getDisplayString() {
        StringBuilder sb = new StringBuilder();
        
        if (unit != null) {
            sb.append(unit.formatAmount(amount));
        } else {
            sb.append(String.format("%.1f", amount));
        }
        
        if (ingredient != null) {
            sb.append(" ").append(ingredient.getName());
        }
        
        if (preparation != null && !preparation.trim().isEmpty()) {
            sb.append(", ").append(preparation);
        }
        
        if (isOptional) {
            sb.append(" (optional)");
        }
        
        return sb.toString();
    }
    
    /**
     * Get detailed display string with nutritional info
     */
    public String getDetailedDisplayString() {
        StringBuilder sb = new StringBuilder(getDisplayString());
        
        if (ingredient != null && unit != null) {
            double calories = calculateCalories();
            sb.append(String.format(" - %.0f cal", calories));
        }
        
        if (notes != null && !notes.trim().isEmpty()) {
            sb.append("\nNotes: ").append(notes);
        }
        
        return sb.toString();
    }
    
    /**
     * Check if this ingredient can be substituted with another
     */
    public boolean canSubstituteWith(Ingredient otherIngredient) {
        if (ingredient == null || otherIngredient == null) {
            return false;
        }
        
        // Basic substitution rules - same category and compatible units
        return ingredient.getCategory().equals(otherIngredient.getCategory()) &&
               otherIngredient.isCompatibleWithUnit(unit);
    }
    
    /**
     * Create a substitution with another ingredient
     */
    public RecipeIngredient createSubstitution(Ingredient substitutionIngredient, double substitutionAmount) {
        if (!canSubstituteWith(substitutionIngredient)) {
            throw new IllegalArgumentException("Cannot substitute with the provided ingredient");
        }
        
        return new RecipeIngredient(
            UUID.randomUUID().toString(),
            substitutionIngredient,
            substitutionAmount,
            this.unit,
            "Substitution for " + (ingredient != null ? ingredient.getName() : "unknown ingredient"),
            this.isOptional,
            this.preparation,
            this.displayOrder
        );
    }
    
    /**
     * Get the cost per serving if ingredient has cost information
     */
    public double calculateCost() {
        if (ingredient == null || unit == null) {
            return 0.0;
        }
        
        // This would require cost information in the ingredient
        // For now, return 0 as placeholder
        return 0.0;
    }
    
    /**
     * Check if this recipe ingredient has all required information
     */
    public boolean isComplete() {
        return ingredient != null && 
               unit != null && 
               amount > 0 && 
               ingredientId != null;
    }
    
    /**
     * Create a copy of this recipe ingredient
     */
    public RecipeIngredient copy() {
        return new RecipeIngredient(
            UUID.randomUUID().toString(),
            this.ingredient,
            this.amount,
            this.unit,
            this.notes,
            this.isOptional,
            this.preparation,
            this.displayOrder
        );
    }
    
    // Getters and Setters
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getIngredientId() {
        return ingredientId;
    }
    
    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }
    
    public Ingredient getIngredient() {
        return ingredient;
    }
    
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
        this.ingredientId = ingredient != null ? ingredient.getId() : null;
        if (ingredient != null && unit != null && !ingredient.isCompatibleWithUnit(unit)) {
            throw new IllegalArgumentException("Unit is not compatible with ingredient");
        }
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.amount = amount;
    }
    
    public MeasurementUnit getUnit() {
        return unit;
    }
    
    public void setUnit(MeasurementUnit unit) {
        if (ingredient != null && unit != null && !ingredient.isCompatibleWithUnit(unit)) {
            throw new IllegalArgumentException("Unit is not compatible with ingredient");
        }
        this.unit = unit;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public boolean isOptional() {
        return isOptional;
    }
    
    public void setOptional(boolean optional) {
        isOptional = optional;
    }
    
    public String getPreparation() {
        return preparation;
    }
    
    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }
    
    public int getDisplayOrder() {
        return displayOrder;
    }
    
    public void setDisplayOrder(int displayOrder) {
        if (displayOrder < 0) {
            throw new IllegalArgumentException("Display order cannot be negative");
        }
        this.displayOrder = displayOrder;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredient that = (RecipeIngredient) o;
        return Double.compare(that.amount, amount) == 0 &&
               isOptional == that.isOptional &&
               displayOrder == that.displayOrder &&
               Objects.equals(id, that.id) &&
               Objects.equals(ingredientId, that.ingredientId) &&
               Objects.equals(ingredient, that.ingredient) &&
               Objects.equals(unit, that.unit) &&
               Objects.equals(notes, that.notes) &&
               Objects.equals(preparation, that.preparation);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, ingredientId, ingredient, amount, unit, notes, 
                          isOptional, preparation, displayOrder);
    }
    
    @Override
    public String toString() {
        return "RecipeIngredient{" +
               "id='" + id + '\'' +
               ", ingredient=" + (ingredient != null ? ingredient.getName() : "null") +
               ", amount=" + amount +
               ", unit=" + (unit != null ? unit.getSymbol() : "null") +
               ", isOptional=" + isOptional +
               ", preparation='" + preparation + '\'' +
               ", displayOrder=" + displayOrder +
               '}';
    }
}
