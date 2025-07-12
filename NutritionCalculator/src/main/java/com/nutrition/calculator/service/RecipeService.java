package com.nutrition.calculator.service;

import com.nutrition.calculator.model.Recipe;
import java.util.List;

/**
 * Service interface for managing recipes.
 * Designed for easy swapping between in-memory and persistent storage.
 */
public interface RecipeService {
    
    /**
     * Save a recipe to storage
     */
    boolean saveRecipe(Recipe recipe);
    
    /**
     * Get all saved recipes
     */
    List<Recipe> getAllRecipes();
    
    /**
     * Find a recipe by name
     */
    Recipe findRecipeByName(String name);
    
    /**
     * Delete a recipe
     */
    boolean deleteRecipe(String name);
    
    /**
     * Update an existing recipe
     */
    boolean updateRecipe(Recipe recipe);
}
