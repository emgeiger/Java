package com.nutrition.calculator.model;

import java.util.Objects;

/**
 * Represents a quantity of an ingredient used in a recipe.
 * Links an ingredient with its specific amount.
 */
public class RecipeIngredient {
    private Ingredient ingredient;
    private double quantity;
    
    // Default constructor for JSON deserialization
    public RecipeIngredient() {}
    
    public RecipeIngredient(Ingredient ingredient, double quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
    }
    
    // Getters and Setters
    public Ingredient getIngredient() {
        return ingredient;
    }
    
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
    
    public double getQuantity() {
        return quantity;
    }
    
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    
    /**
     * Calculate total calories for this recipe ingredient
     * @return Total calories (quantity * calories per unit)
     */
    public double getTotalCalories() {
        if (ingredient == null) return 0.0;
        return ingredient.calculateCalories(quantity);
    }
    
    /**
     * Get formatted quantity string with unit
     * @return Formatted string like "2.5 cups"
     */
    public String getFormattedQuantity() {
        if (ingredient == null) return String.valueOf(quantity);
        return String.format("%.1f %s", quantity, ingredient.getUnit().getAbbreviation());
    }
    
    /**
     * Validate that this recipe ingredient is complete and valid
     * @return true if valid
     */
    public boolean isValid() {
        return ingredient != null && 
               ingredient.isValid() && 
               quantity > 0;
    }
    
    /**
     * Scale this recipe ingredient by a factor
     * @param factor Scaling factor (e.g., 2.0 to double, 0.5 to halve)
     * @return New RecipeIngredient with scaled quantity
     */
    public RecipeIngredient scale(double factor) {
        return new RecipeIngredient(ingredient, quantity * factor);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredient that = (RecipeIngredient) o;
        return Double.compare(that.quantity, quantity) == 0 &&
                Objects.equals(ingredient, that.ingredient);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(ingredient, quantity);
    }
    
    @Override
    public String toString() {
        if (ingredient == null) return "Invalid ingredient";
        return String.format("%s %s (%s)", 
                getFormattedQuantity(), 
                ingredient.getName(),
                String.format("%.0f cal", getTotalCalories()));
    }
}
