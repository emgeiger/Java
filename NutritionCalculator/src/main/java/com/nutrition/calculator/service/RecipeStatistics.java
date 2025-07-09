package com.nutrition.calculator.service;

/**
 * Statistics about recipes in the system
 */
public class RecipeStatistics {
    private int totalRecipes;
    private int totalIngredients;
    private double averageCaloriesPerRecipe;
    private double averageIngredientsPerRecipe;
    private double averageServingsPerRecipe;
    
    public RecipeStatistics() {}
    
    public RecipeStatistics(int totalRecipes, int totalIngredients, 
                          double averageCaloriesPerRecipe, 
                          double averageIngredientsPerRecipe,
                          double averageServingsPerRecipe) {
        this.totalRecipes = totalRecipes;
        this.totalIngredients = totalIngredients;
        this.averageCaloriesPerRecipe = averageCaloriesPerRecipe;
        this.averageIngredientsPerRecipe = averageIngredientsPerRecipe;
        this.averageServingsPerRecipe = averageServingsPerRecipe;
    }
    
    // Getters and Setters
    public int getTotalRecipes() {
        return totalRecipes;
    }
    
    public void setTotalRecipes(int totalRecipes) {
        this.totalRecipes = totalRecipes;
    }
    
    public int getTotalIngredients() {
        return totalIngredients;
    }
    
    public void setTotalIngredients(int totalIngredients) {
        this.totalIngredients = totalIngredients;
    }
    
    public double getAverageCaloriesPerRecipe() {
        return averageCaloriesPerRecipe;
    }
    
    public void setAverageCaloriesPerRecipe(double averageCaloriesPerRecipe) {
        this.averageCaloriesPerRecipe = averageCaloriesPerRecipe;
    }
    
    public double getAverageIngredientsPerRecipe() {
        return averageIngredientsPerRecipe;
    }
    
    public void setAverageIngredientsPerRecipe(double averageIngredientsPerRecipe) {
        this.averageIngredientsPerRecipe = averageIngredientsPerRecipe;
    }
    
    public double getAverageServingsPerRecipe() {
        return averageServingsPerRecipe;
    }
    
    public void setAverageServingsPerRecipe(double averageServingsPerRecipe) {
        this.averageServingsPerRecipe = averageServingsPerRecipe;
    }
    
    @Override
    public String toString() {
        return String.format("RecipeStatistics{totalRecipes=%d, totalIngredients=%d, " +
                "avgCalories=%.1f, avgIngredients=%.1f, avgServings=%.1f}", 
                totalRecipes, totalIngredients, averageCaloriesPerRecipe, 
                averageIngredientsPerRecipe, averageServingsPerRecipe);
    }
}
