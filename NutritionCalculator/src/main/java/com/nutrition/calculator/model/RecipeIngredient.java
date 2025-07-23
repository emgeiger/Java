package com.nutrition.calculator.model;

import java.util.Objects;

/**
 * RecipeIngredient - Model class linking Recipe to Ingredient with amount
 * 
 * This class represents the relationship between a Recipe and an Ingredient,
 * including the specific amount and measurement unit used in that recipe.
 * It enables accurate nutrition calculations for recipe portions.
 * 
 * @author NutritionCalculator Development Team
 * @version 1.0
 */
public class RecipeIngredient {
    
    private String id;
    private String recipeId;
    private Ingredient ingredient;
    private double amount; // Amount in grams (standardized)
    private double displayAmount; // Amount as entered by user
    private MeasurementUnit unit;
    private String notes;
    private boolean isOptional;
    private int sortOrder;
    private long createdDate;
    private long lastModified;
    
    /**
     * Default constructor.
     */
    public RecipeIngredient() {
        this.createdDate = System.currentTimeMillis();
        this.lastModified = System.currentTimeMillis();
        this.isOptional = false;
        this.sortOrder = 0;
        this.amount = 0.0;
        this.displayAmount = 0.0;
        this.unit = MeasurementUnit.GRAM;
    }
    
    /**
     * Constructor with ingredient and amount.
     * 
     * @param ingredient The ingredient
     * @param amount Amount in grams
     */
    public RecipeIngredient(Ingredient ingredient, double amount) {
        this();
        this.ingredient = ingredient;
        this.amount = amount;
        this.displayAmount = amount;
        this.unit = MeasurementUnit.GRAM;
    }
    
    /**
     * Constructor with ingredient, display amount, and unit.
     * 
     * @param ingredient The ingredient
     * @param displayAmount Amount as displayed to user
     * @param unit Measurement unit
     */
    public RecipeIngredient(Ingredient ingredient, double displayAmount, MeasurementUnit unit) {
        this();
        this.ingredient = ingredient;
        this.displayAmount = displayAmount;
        this.unit = unit;
        this.amount = unit.convertToGrams(displayAmount);
    }
    
    /**
     * Copy constructor.
     * 
     * @param other RecipeIngredient to copy from
     */
    public RecipeIngredient(RecipeIngredient other) {
        this.id = other.id;
        this.recipeId = other.recipeId;
        this.ingredient = other.ingredient != null ? new Ingredient(other.ingredient) : null;
        this.amount = other.amount;
        this.displayAmount = other.displayAmount;
        this.unit = other.unit;
        this.notes = other.notes;
        this.isOptional = other.isOptional;
        this.sortOrder = other.sortOrder;
        this.createdDate = other.createdDate;
        this.lastModified = System.currentTimeMillis();
    }
    
    // Getters and Setters
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
        this.lastModified = System.currentTimeMillis();
    }
    
    public String getRecipeId() {
        return recipeId;
    }
    
    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
        this.lastModified = System.currentTimeMillis();
    }
    
    public Ingredient getIngredient() {
        return ingredient;
    }
    
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = Math.max(0, amount);
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getDisplayAmount() {
        return displayAmount;
    }
    
    public void setDisplayAmount(double displayAmount) {
        this.displayAmount = Math.max(0, displayAmount);
        this.amount = unit != null ? unit.convertToGrams(displayAmount) : displayAmount;
        this.lastModified = System.currentTimeMillis();
    }
    
    public MeasurementUnit getUnit() {
        return unit;
    }
    
    public void setUnit(MeasurementUnit unit) {
        this.unit = unit != null ? unit : MeasurementUnit.GRAM;
        this.amount = this.unit.convertToGrams(displayAmount);
        this.lastModified = System.currentTimeMillis();
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
        this.lastModified = System.currentTimeMillis();
    }
    
    public boolean isOptional() {
        return isOptional;
    }
    
    public void setOptional(boolean optional) {
        isOptional = optional;
        this.lastModified = System.currentTimeMillis();
    }
    
    public int getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
        this.lastModified = System.currentTimeMillis();
    }
    
    public long getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }
    
    public long getLastModified() {
        return lastModified;
    }
    
    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
    
    // Convenience Methods
    
    /**
     * Update both display amount and unit, recalculating grams.
     * 
     * @param displayAmount New display amount
     * @param unit New measurement unit
     */
    public void updateAmountAndUnit(double displayAmount, MeasurementUnit unit) {
        this.displayAmount = Math.max(0, displayAmount);
        this.unit = unit != null ? unit : MeasurementUnit.GRAM;
        this.amount = this.unit.convertToGrams(this.displayAmount);
        this.lastModified = System.currentTimeMillis();
    }
    
    /**
     * Get the ingredient name safely.
     * 
     * @return Ingredient name or "Unknown Ingredient"
     */
    public String getIngredientName() {
        return ingredient != null ? ingredient.getName() : "Unknown Ingredient";
    }
    
    /**
     * Get formatted display string for amount and unit.
     * 
     * @return Formatted amount string
     */
    public String getFormattedAmount() {
        if (unit == null) {
            return String.format("%.1fg", amount);
        }
        
        // Format display amount based on unit type
        if (displayAmount == Math.floor(displayAmount)) {
            return String.format("%.0f %s", displayAmount, unit.getDisplayName());
        } else {
            return String.format("%.1f %s", displayAmount, unit.getDisplayName());
        }
    }
    
    /**
     * Calculate calories for this ingredient amount.
     * 
     * @return Calories
     */
    public double getCalories() {
        if (ingredient == null) return 0.0;
        double factor = amount / 100.0;
        return ingredient.getCaloriesPer100g() * factor;
    }
    
    /**
     * Calculate protein for this ingredient amount.
     * 
     * @return Protein in grams
     */
    public double getProtein() {
        if (ingredient == null) return 0.0;
        double factor = amount / 100.0;
        return ingredient.getProteinPer100g() * factor;
    }
    
    /**
     * Calculate carbohydrates for this ingredient amount.
     * 
     * @return Carbohydrates in grams
     */
    public double getCarbohydrates() {
        if (ingredient == null) return 0.0;
        double factor = amount / 100.0;
        return ingredient.getCarbsPer100g() * factor;
    }
    
    /**
     * Calculate fat for this ingredient amount.
     * 
     * @return Fat in grams
     */
    public double getFat() {
        if (ingredient == null) return 0.0;
        double factor = amount / 100.0;
        return ingredient.getFatPer100g() * factor;
    }
    
    /**
     * Calculate fiber for this ingredient amount.
     * 
     * @return Fiber in grams
     */
    public double getFiber() {
        if (ingredient == null) return 0.0;
        double factor = amount / 100.0;
        return ingredient.getFiberPer100g() * factor;
    }
    
    /**
     * Calculate sodium for this ingredient amount.
     * 
     * @return Sodium in mg
     */
    public double getSodium() {
        if (ingredient == null) return 0.0;
        double factor = amount / 100.0;
        return ingredient.getSodiumPer100g() * factor;
    }
    
    /**
     * Get all nutrition values for this ingredient amount.
     * 
     * @return Array of [calories, protein, carbs, fat, fiber, sodium]
     */
    public double[] getNutritionValues() {
        if (ingredient == null) {
            return new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        }
        
        double factor = amount / 100.0;
        return new double[]{
            ingredient.getCaloriesPer100g() * factor,
            ingredient.getProteinPer100g() * factor,
            ingredient.getCarbsPer100g() * factor,
            ingredient.getFatPer100g() * factor,
            ingredient.getFiberPer100g() * factor,
            ingredient.getSodiumPer100g() * factor
        };
    }
    
    /**
     * Get nutrition summary for this ingredient amount.
     * 
     * @return Formatted nutrition summary
     */
    public String getNutritionSummary() {
        return String.format("%.0f cal, %.1fg protein, %.1fg carbs, %.1fg fat",
                getCalories(), getProtein(), getCarbohydrates(), getFat());
    }
    
    /**
     * Scale this ingredient by a factor.
     * 
     * @param factor Scaling factor
     * @return New scaled RecipeIngredient
     */
    public RecipeIngredient scale(double factor) {
        RecipeIngredient scaled = new RecipeIngredient(this);
        scaled.setDisplayAmount(displayAmount * factor);
        return scaled;
    }
    
    /**
     * Convert to a different unit.
     * 
     * @param newUnit New measurement unit
     * @return New RecipeIngredient with converted unit
     */
    public RecipeIngredient convertToUnit(MeasurementUnit newUnit) {
        if (newUnit == null || newUnit == this.unit) {
            return new RecipeIngredient(this);
        }
        
        RecipeIngredient converted = new RecipeIngredient(this);
        double gramsToConvert = this.amount;
        double newDisplayAmount = newUnit.convertFromGrams(gramsToConvert);
        converted.updateAmountAndUnit(newDisplayAmount, newUnit);
        return converted;
    }
    
    /**
     * Check if this ingredient is suitable for dietary restrictions.
     * 
     * @param restriction Dietary restriction
     * @return true if suitable
     */
    public boolean isSuitableFor(String restriction) {
        if (ingredient == null || restriction == null) return false;
        
        String lowerRestriction = restriction.toLowerCase();
        switch (lowerRestriction) {
            case "vegan":
                return ingredient.isVegan();
            case "vegetarian":
                return ingredient.isVegetarian();
            case "gluten-free":
            case "gluten free":
                return ingredient.isGlutenFree();
            case "dairy-free":
            case "dairy free":
                return ingredient.isDairyFree();
            case "organic":
                return ingredient.isOrganic();
            default:
                return ingredient.hasTag(restriction);
        }
    }
    
    /**
     * Check if ingredient contains allergen.
     * 
     * @param allergen Allergen to check
     * @return true if contains allergen
     */
    public boolean containsAllergen(String allergen) {
        return ingredient != null && ingredient.containsAllergen(allergen);
    }
    
    /**
     * Validate this recipe ingredient.
     * 
     * @return true if valid
     */
    public boolean isValid() {
        return ingredient != null && 
               ingredient.isValid() && 
               amount >= 0 && 
               displayAmount >= 0 && 
               unit != null;
    }
    
    /**
     * Get detailed ingredient line for recipe display.
     * 
     * @return Formatted ingredient line
     */
    public String getIngredientLine() {
        StringBuilder sb = new StringBuilder();
        sb.append(getFormattedAmount()).append(" ");
        sb.append(getIngredientName());
        
        if (notes != null && !notes.trim().isEmpty()) {
            sb.append(" (").append(notes.trim()).append(")");
        }
        
        if (isOptional) {
            sb.append(" [optional]");
        }
        
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RecipeIngredient that = (RecipeIngredient) obj;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return getIngredientLine();
    }
    
    /**
     * Compare by sort order for recipe display.
     * 
     * @param other Other RecipeIngredient
     * @return Comparison result
     */
    public int compareTo(RecipeIngredient other) {
        if (other == null) return 1;
        
        int orderCompare = Integer.compare(this.sortOrder, other.sortOrder);
        if (orderCompare != 0) return orderCompare;
        
        // If same sort order, compare by ingredient name
        String thisName = this.getIngredientName();
        String otherName = other.getIngredientName();
        return thisName.compareToIgnoreCase(otherName);
    }
}
