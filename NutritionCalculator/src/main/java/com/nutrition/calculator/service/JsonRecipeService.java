package com.nutrition.calculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nutrition.calculator.model.Recipe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JSON-based implementation of RecipeService for local data persistence.
 * Designed for easy transition to Android/Kotlin with different storage backends.
 */
public class JsonRecipeService implements RecipeService {
    private static final String DATA_DIRECTORY = "nutrition_data";
    private static final String RECIPES_DIRECTORY = "recipes";
    private final ObjectMapper objectMapper;
    private final Path recipesPath;
    
    public JsonRecipeService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.recipesPath = Paths.get(System.getProperty("user.home"), DATA_DIRECTORY, RECIPES_DIRECTORY);
        initializeDataDirectory();
    }
    
    /**
     * Constructor with custom data directory (useful for testing)
     */
    public JsonRecipeService(String customDataDirectory) {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.recipesPath = Paths.get(customDataDirectory, RECIPES_DIRECTORY);
        initializeDataDirectory();
    }
    
    private void initializeDataDirectory() {
        try {
            Files.createDirectories(recipesPath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create data directory: " + recipesPath, e);
        }
    }
    
    @Override
    public boolean saveRecipe(Recipe recipe) {
        if (recipe == null || !recipe.isValid()) {
            return false;
        }
        
        try {
            File recipeFile = getRecipeFile(recipe.getId());
            objectMapper.writeValue(recipeFile, recipe);
            return true;
        } catch (IOException e) {
            System.err.println("Failed to save recipe: " + recipe.getId() + " - " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public Recipe loadRecipe(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        
        try {
            File recipeFile = getRecipeFile(id);
            if (!recipeFile.exists()) {
                return null;
            }
            return objectMapper.readValue(recipeFile, Recipe.class);
        } catch (IOException e) {
            System.err.println("Failed to load recipe: " + id + " - " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public List<Recipe> loadAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        
        try {
            Files.list(recipesPath)
                    .filter(path -> path.toString().endsWith(".json"))
                    .forEach(path -> {
                        try {
                            Recipe recipe = objectMapper.readValue(path.toFile(), Recipe.class);
                            if (recipe != null && recipe.isValid()) {
                                recipes.add(recipe);
                            }
                        } catch (IOException e) {
                            System.err.println("Failed to load recipe from: " + path + " - " + e.getMessage());
                        }
                    });
        } catch (IOException e) {
            System.err.println("Failed to list recipe files: " + e.getMessage());
        }
        
        return recipes;
    }
    
    @Override
    public boolean deleteRecipe(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        
        try {
            File recipeFile = getRecipeFile(id);
            return recipeFile.exists() && recipeFile.delete();
        } catch (Exception e) {
            System.err.println("Failed to delete recipe: " + id + " - " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public List<Recipe> searchRecipesByName(String namePattern) {
        if (namePattern == null || namePattern.trim().isEmpty()) {
            return loadAllRecipes();
        }
        
        String searchPattern = namePattern.toLowerCase().trim();
        return loadAllRecipes().stream()
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
    
    /**
     * Get the file path for a recipe
     */
    private File getRecipeFile(String recipeId) {
        return recipesPath.resolve(recipeId + ".json").toFile();
    }
    
    /**
     * Get the data directory path (useful for debugging)
     */
    public Path getDataPath() {
        return recipesPath;
    }
    
    /**
     * Clear all saved recipes (useful for testing)
     */
    public boolean clearAllRecipes() {
        try {
            Files.list(recipesPath)
                    .filter(path -> path.toString().endsWith(".json"))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            System.err.println("Failed to delete: " + path);
                        }
                    });
            return true;
        } catch (IOException e) {
            System.err.println("Failed to clear recipes: " + e.getMessage());
            return false;
        }
    }
}
