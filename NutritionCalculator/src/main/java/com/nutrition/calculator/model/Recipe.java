package com.nutrition.calculator.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a recipe containing multiple ingredients with calculated nutrition.
 * Core model for the nutrition calculator MVP.
 */
public class Recipe {
    private String id;
    private String name;
    private String description;
    private List<RecipeIngredient> ingredients;
    private int servings;
    private LocalDateTime createdDate;
    private LocalDateTime lastModified;
    
    // Default constructor for JSON deserialization
    public Recipe() {
        this.id = UUID.randomUUID().toString();
        this.ingredients = new ArrayList<>();
        this.servings = 1;
        this.createdDate = LocalDateTime.now();
        this.lastModified = LocalDateTime.now();
    }
    
    public Recipe(String name) {
        this();
        this.name = name;
    }
    
    public Recipe(String name, String description, int servings) {
        this(name);
        this.description = description;
        this.servings = servings;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
        updateLastModified();
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
        updateLastModified();
    }
    
    public List<RecipeIngredient> getIngredients() {
        return new ArrayList<>(ingredients); // Return defensive copy
    }
    
    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = new ArrayList<>(ingredients);
        updateLastModified();
    }
    
    public int getServings() {
        return servings;
    }
    
    public void setServings(int servings) {
        if (servings <= 0) {
            throw new IllegalArgumentException("Servings must be positive");
        }
        this.servings = servings;
        updateLastModified();
    }
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    
    public LocalDateTime getLastModified() {
        return lastModified;
    }
    
    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }
    
    // Recipe Management Methods
    
    /**
     * Add an ingredient to the recipe
     * @param ingredient The ingredient to add
     * @param quantity Amount of the ingredient
     */
    public void addIngredient(Ingredient ingredient, double quantity) {
        RecipeIngredient recipeIngredient = new RecipeIngredient(ingredient, quantity);
        ingredients.add(recipeIngredient);
        updateLastModified();
    }
    
    /**
     * Add a recipe ingredient directly
     * @param recipeIngredient The recipe ingredient to add
     */
    public void addIngredient(RecipeIngredient recipeIngredient) {
        ingredients.add(recipeIngredient);
        updateLastModified();
    }
    
    /**
     * Remove an ingredient from the recipe
     * @param recipeIngredient The ingredient to remove
     * @return true if removed, false if not found
     */
    public boolean removeIngredient(RecipeIngredient recipeIngredient) {
        boolean removed = ingredients.remove(recipeIngredient);
        if (removed) {
            updateLastModified();
        }
        return removed;
    }
    
    /**
     * Remove ingredient by index
     * @param index Index of ingredient to remove
     * @return The removed ingredient, or null if index invalid
     */
    public RecipeIngredient removeIngredient(int index) {
        if (index >= 0 && index < ingredients.size()) {
            RecipeIngredient removed = ingredients.remove(index);
            updateLastModified();
            return removed;
        }
        return null;
    }
    
    /**
     * Clear all ingredients from the recipe
     */
    public void clearIngredients() {
        ingredients.clear();
        updateLastModified();
    }
    
    // Nutrition Calculation Methods
    
    /**
     * Calculate total calories for the entire recipe
     * @return Total calories from all ingredients
     */
    public double getTotalCalories() {
        return ingredients.stream()
                .mapToDouble(RecipeIngredient::getTotalCalories)
                .sum();
    }
    
    /**
     * Calculate average calories per serving
     * @return Calories per serving
     */
    public double getCaloriesPerServing() {
        return getTotalCalories() / servings;
    }
    
    /**
     * Get number of ingredients in recipe
     * @return Number of ingredients
     */
    public int getIngredientCount() {
        return ingredients.size();
    }
    
    /**
     * Scale the entire recipe by a factor
     * @param factor Scaling factor (e.g., 2.0 to double, 0.5 to halve)
     * @return New scaled recipe
     */
    public Recipe scale(double factor) {
        Recipe scaledRecipe = new Recipe(name + " (scaled x" + factor + ")", description, servings);
        for (RecipeIngredient ingredient : ingredients) {
            scaledRecipe.addIngredient(ingredient.scale(factor));
        }
        return scaledRecipe;
    }
    
    /**
     * Validate that the recipe is complete and valid
     * @return true if recipe is valid
     */
    public boolean isValid() {
        return name != null && !name.trim().isEmpty() &&
               servings > 0 &&
               !ingredients.isEmpty() &&
               ingredients.stream().allMatch(RecipeIngredient::isValid);
    }
    
    private void updateLastModified() {
        this.lastModified = LocalDateTime.now();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(id, recipe.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("%s (%d ingredients, %d servings, %.0f cal/serving)", 
                name, getIngredientCount(), servings, getCaloriesPerServing());
    }
}
