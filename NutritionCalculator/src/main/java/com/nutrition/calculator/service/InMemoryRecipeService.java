package com.nutrition.calculator.service;

import com.nutrition.calculator.model.Recipe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Simple in-memory implementation of RecipeService for testing without external dependencies.
 * This can be used while setting up the full JSON persistence later.
 */
public class InMemoryRecipeService implements RecipeService {
    private final Map<String, Recipe> recipes;
    
    public InMemoryRecipeService() {
        this.recipes = new HashMap<>();
    }
    
    @Override
    public boolean saveRecipe(Recipe recipe) {
        if (recipe == null || !recipe.isValid()) {
            return false;
        }
        recipes.put(recipe.getId(), recipe);
        return true;
    }
    
    @Override
    public Recipe loadRecipe(String id) {
        return recipes.get(id);
    }
    
    @Override
    public List<Recipe> loadAllRecipes() {
        return new ArrayList<>(recipes.values());
    }
    
    @Override
    public boolean deleteRecipe(String id) {
        return recipes.remove(id) != null;
    }
    
    @Override
    public List<Recipe> searchRecipesByName(String namePattern) {
        if (namePattern == null || namePattern.trim().isEmpty()) {
            return loadAllRecipes();
        }
        
        String searchPattern = namePattern.toLowerCase().trim();
        return recipes.values().stream()
                .filter(recipe -> recipe.getName().toLowerCase().contains(searchPattern))
                .collect(Collectors.toList());
    }
    
    @Override
    public RecipeStatistics getStatistics() {
        List<Recipe> allRecipes = loadAllRecipes();
        
        if (allRecipes.isEmpty()) {
            return new RecipeStatistics(0, 0, 0.0, 0.0, 0.0);
        }
        
        int totalRecipes = allRecipes.size();
        int totalIngredients = allRecipes.stream()
                .mapToInt(Recipe::getIngredientCount)
                .sum();
        
        double averageCaloriesPerRecipe = allRecipes.stream()
                .mapToDouble(Recipe::getTotalCalories)
                .average()
                .orElse(0.0);
        
        double averageIngredientsPerRecipe = (double) totalIngredients / totalRecipes;
        
        double averageServingsPerRecipe = allRecipes.stream()
                .mapToInt(Recipe::getServings)
                .average()
                .orElse(0.0);
        
        return new RecipeStatistics(totalRecipes, totalIngredients, 
                averageCaloriesPerRecipe, averageIngredientsPerRecipe, averageServingsPerRecipe);
    }
}
