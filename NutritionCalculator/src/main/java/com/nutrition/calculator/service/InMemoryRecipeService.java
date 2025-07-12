package com.nutrition.calculator.service;

import com.nutrition.calculator.model.Recipe;
import java.util.*;

/**
 * In-memory implementation of RecipeService for MVP.
 * Stores recipes in memory for the current session only.
 */
public class InMemoryRecipeService implements RecipeService {
    
    private final Map<String, Recipe> recipes;
    
    public InMemoryRecipeService() {
        this.recipes = new HashMap<>();
        initializeWithSampleData();
    }
    
    @Override
    public boolean saveRecipe(Recipe recipe) {
        if (recipe != null && recipe.getName() != null) {
            recipes.put(recipe.getName(), recipe);
            return true;
        }
        return false;
    }
    
    @Override
    public List<Recipe> getAllRecipes() {
        return new ArrayList<>(recipes.values());
    }
    
    @Override
    public Recipe findRecipeByName(String name) {
        return recipes.get(name);
    }
    
    @Override
    public boolean deleteRecipe(String name) {
        return recipes.remove(name) != null;
    }
    
    @Override
    public boolean updateRecipe(Recipe recipe) {
        if (recipe != null && recipe.getName() != null && recipes.containsKey(recipe.getName())) {
            recipes.put(recipe.getName(), recipe);
            return true;
        }
        return false;
    }
    
    /**
     * Initialize with some sample recipes for demonstration
     */
    private void initializeWithSampleData() {
        // Add sample recipes here if needed
        // This keeps the service simple for now
    }
}
