package com.nutrition.calculator.service;

import com.nutrition.calculator.model.Recipe;
import com.nutrition.calculator.model.Ingredient;
import java.util.List;

/**
 * Service interface for recipe management operations.
 * Designed for easy testing and future Android/Kotlin compatibility.
 */
public interface RecipeService {
    
    /**
     * Save a recipe to persistent storage
     * @param recipe The recipe to save
     * @return true if saved successfully
     */
    boolean saveRecipe(Recipe recipe);
    
    /**
     * Load a recipe by ID
     * @param id The recipe ID
     * @return The recipe, or null if not found
     */
    Recipe loadRecipe(String id);
    
    /**
     * Load all saved recipes
     * @return List of all recipes
     */
    List<Recipe> loadAllRecipes();
    
    /**
     * Delete a recipe
     * @param id The recipe ID to delete
     * @return true if deleted successfully
     */
    boolean deleteRecipe(String id);
    
    /**
     * Search recipes by name
     * @param namePattern Pattern to search for (case-insensitive)
     * @return List of matching recipes
     */
    List<Recipe> searchRecipesByName(String namePattern);
    
    /**
     * Get recipe statistics
     * @return Statistics about saved recipes
     */
    RecipeStatistics getStatistics();
}
