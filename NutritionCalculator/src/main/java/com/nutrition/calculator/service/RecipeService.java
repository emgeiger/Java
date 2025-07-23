package com.nutrition.calculator.service;

import com.nutrition.calculator.model.Recipe;
import java.util.List;

/**
 * RecipeService - Interface for recipe management operations
 * 
 * This service interface defines the contract for recipe CRUD operations,
 * nutrition calculations, and recipe data management in the Nutrition Calculator.
 * Implementations can provide different storage backends (JSON, database, etc.).
 * 
 * @author NutritionCalculator Development Team
 * @version 1.0
 */
public interface RecipeService {
    
    /**
     * Save a recipe to the storage backend.
     * 
     * @param recipe The recipe to save
     * @return The saved recipe with assigned ID if new
     * @throws Exception if save operation fails
     */
    Recipe saveRecipe(Recipe recipe) throws Exception;
    
    /**
     * Retrieve a recipe by its unique identifier.
     * 
     * @param id The unique identifier of the recipe
     * @return The recipe if found, null otherwise
     * @throws Exception if retrieval operation fails
     */
    Recipe getRecipeById(String id) throws Exception;
    
    /**
     * Retrieve all recipes from the storage backend.
     * 
     * @return List of all recipes, empty list if none found
     * @throws Exception if retrieval operation fails
     */
    List<Recipe> getAllRecipes() throws Exception;
    
    /**
     * Update an existing recipe.
     * 
     * @param recipe The recipe with updated information
     * @return The updated recipe
     * @throws Exception if update operation fails
     */
    Recipe updateRecipe(Recipe recipe) throws Exception;
    
    /**
     * Delete a recipe by its unique identifier.
     * 
     * @param id The unique identifier of the recipe to delete
     * @return true if deletion was successful, false otherwise
     * @throws Exception if deletion operation fails
     */
    boolean deleteRecipe(String id) throws Exception;
    
    /**
     * Search recipes by name (case-insensitive partial matching).
     * 
     * @param name The name or partial name to search for
     * @return List of matching recipes, empty list if none found
     * @throws Exception if search operation fails
     */
    List<Recipe> searchRecipesByName(String name) throws Exception;
    
    /**
     * Search recipes by ingredient.
     * 
     * @param ingredientName The ingredient name to search for
     * @return List of recipes containing the ingredient, empty list if none found
     * @throws Exception if search operation fails
     */
    List<Recipe> searchRecipesByIngredient(String ingredientName) throws Exception;
    
    /**
     * Calculate total nutrition facts for a recipe.
     * 
     * @param recipe The recipe to calculate nutrition for
     * @return NutritionFacts object with calculated values
     * @throws Exception if calculation fails
     */
    default Object calculateNutrition(Recipe recipe) throws Exception {
        // Default implementation - can be overridden by concrete implementations
        double totalCalories = 0;
        double totalProtein = 0;
        double totalCarbs = 0;
        double totalFat = 0;
        
        if (recipe.getIngredients() != null) {
            for (var ingredient : recipe.getIngredients()) {
                double factor = ingredient.getAmount() / 100.0; // Assuming per 100g values
                totalCalories += ingredient.getIngredient().getCaloriesPer100g() * factor;
                totalProtein += ingredient.getIngredient().getProteinPer100g() * factor;
                totalCarbs += ingredient.getIngredient().getCarbsPer100g() * factor;
                totalFat += ingredient.getIngredient().getFatPer100g() * factor;
            }
        }
        
        // Return a simple nutrition summary as a formatted string
        return String.format("Calories: %.1f, Protein: %.1fg, Carbs: %.1fg, Fat: %.1fg",
                totalCalories, totalProtein, totalCarbs, totalFat);
    }
    
    /**
     * Get recipe statistics (total count, most used ingredients, etc.).
     * 
     * @return RecipeStatistics object with summary information
     * @throws Exception if statistics calculation fails
     */
    default Object getRecipeStatistics() throws Exception {
        List<Recipe> allRecipes = getAllRecipes();
        return String.format("Total Recipes: %d", allRecipes.size());
    }
    
    /**
     * Import recipes from external source (JSON file, API, etc.).
     * 
     * @param source The source identifier or path
     * @return Number of recipes imported
     * @throws Exception if import operation fails
     */
    int importRecipes(String source) throws Exception;
    
    /**
     * Export recipes to external format.
     * 
     * @param destination The destination path or identifier
     * @param recipes List of recipes to export, null for all recipes
     * @return Number of recipes exported
     * @throws Exception if export operation fails
     */
    int exportRecipes(String destination, List<Recipe> recipes) throws Exception;
    
    /**
     * Check if the service is properly initialized and ready for operations.
     * 
     * @return true if service is ready, false otherwise
     */
    boolean isServiceReady();
    
    /**
     * Initialize the service with any required setup.
     * 
     * @throws Exception if initialization fails
     */
    void initialize() throws Exception;
    
    /**
     * Clean up resources and close connections.
     * 
     * @throws Exception if cleanup fails
     */
    void shutdown() throws Exception;
}
