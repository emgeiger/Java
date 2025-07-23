package com.nutrition.calculator.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Recipe - Model class representing a nutrition recipe
 * 
 * This class represents a complete recipe with ingredients, servings,
 * and metadata. It supports nutrition calculation and recipe management
 * operations throughout the Nutrition Calculator application.
 * 
 * @author NutritionCalculator Development Team
 * @version 1.0
 */
public class Recipe {
    
    private String id;
    private String name;
    private String description;
    private int servings;
    private List<RecipeIngredient> ingredients;
    private String category;
    private int prepTimeMinutes;
    private int cookTimeMinutes;
    private String difficulty;
    private List<String> instructions;
    private String createdBy;
    private long createdDate;
    private long lastModified;
    private boolean isPrivate;
    private double rating;
    private String imageUrl;
    private List<String> tags;
    
    /**
     * Default constructor.
     */
    public Recipe() {
        this.ingredients = new ArrayList<>();
        this.instructions = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.servings = 1;
        this.createdDate = System.currentTimeMillis();
        this.lastModified = System.currentTimeMillis();
        this.rating = 0.0;
        this.isPrivate = false;
        this.difficulty = "Medium";
    }
    
    /**
     * Copy constructor.
     * 
     * @param other Recipe to copy from
     */
    public Recipe(Recipe other) {
        this.id = other.id;
        this.name = other.name;
        this.description = other.description;
        this.servings = other.servings;
        this.ingredients = new ArrayList<>(other.ingredients);
        this.category = other.category;
        this.prepTimeMinutes = other.prepTimeMinutes;
        this.cookTimeMinutes = other.cookTimeMinutes;
        this.difficulty = other.difficulty;
        this.instructions = new ArrayList<>(other.instructions);
        this.createdBy = other.createdBy;
        this.createdDate = other.createdDate;
        this.lastModified = System.currentTimeMillis();
        this.isPrivate = other.isPrivate;
        this.rating = other.rating;
        this.imageUrl = other.imageUrl;
        this.tags = new ArrayList<>(other.tags);
    }
    
    /**
     * Constructor with basic required fields.
     * 
     * @param name Recipe name
     * @param description Recipe description
     * @param servings Number of servings
     */
    public Recipe(String name, String description, int servings) {
        this();
        this.name = name;
        this.description = description;
        this.servings = servings;
    }
    
    // Getters and Setters
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
        this.lastModified = System.currentTimeMillis();
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
        this.lastModified = System.currentTimeMillis();
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
        this.lastModified = System.currentTimeMillis();
    }
    
    public int getServings() {
        return servings;
    }
    
    public void setServings(int servings) {
        this.servings = servings;
        this.lastModified = System.currentTimeMillis();
    }
    
    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }
    
    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients != null ? ingredients : new ArrayList<>();
        this.lastModified = System.currentTimeMillis();
    }
    
    public void addIngredient(RecipeIngredient ingredient) {
        if (ingredient != null) {
            this.ingredients.add(ingredient);
            this.lastModified = System.currentTimeMillis();
        }
    }
    
    public void removeIngredient(RecipeIngredient ingredient) {
        if (this.ingredients.remove(ingredient)) {
            this.lastModified = System.currentTimeMillis();
        }
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
        this.lastModified = System.currentTimeMillis();
    }
    
    public int getPrepTimeMinutes() {
        return prepTimeMinutes;
    }
    
    public void setPrepTimeMinutes(int prepTimeMinutes) {
        this.prepTimeMinutes = prepTimeMinutes;
        this.lastModified = System.currentTimeMillis();
    }
    
    public int getCookTimeMinutes() {
        return cookTimeMinutes;
    }
    
    public void setCookTimeMinutes(int cookTimeMinutes) {
        this.cookTimeMinutes = cookTimeMinutes;
        this.lastModified = System.currentTimeMillis();
    }
    
    public int getTotalTimeMinutes() {
        return prepTimeMinutes + cookTimeMinutes;
    }
    
    public String getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        this.lastModified = System.currentTimeMillis();
    }
    
    public List<String> getInstructions() {
        return instructions;
    }
    
    public void setInstructions(List<String> instructions) {
        this.instructions = instructions != null ? instructions : new ArrayList<>();
        this.lastModified = System.currentTimeMillis();
    }
    
    public void addInstruction(String instruction) {
        if (instruction != null && !instruction.trim().isEmpty()) {
            this.instructions.add(instruction.trim());
            this.lastModified = System.currentTimeMillis();
        }
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
    
    public boolean isPrivate() {
        return isPrivate;
    }
    
    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getRating() {
        return rating;
    }
    
    public void setRating(double rating) {
        this.rating = Math.max(0.0, Math.min(5.0, rating)); // Clamp between 0-5
        this.lastModified = System.currentTimeMillis();
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        this.lastModified = System.currentTimeMillis();
    }
    
    public List<String> getTags() {
        return tags;
    }
    
    public void setTags(List<String> tags) {
        this.tags = tags != null ? tags : new ArrayList<>();
        this.lastModified = System.currentTimeMillis();
    }
    
    public void addTag(String tag) {
        if (tag != null && !tag.trim().isEmpty() && !this.tags.contains(tag.trim())) {
            this.tags.add(tag.trim().toLowerCase());
            this.lastModified = System.currentTimeMillis();
        }
    }
    
    public void removeTag(String tag) {
        if (this.tags.remove(tag)) {
            this.lastModified = System.currentTimeMillis();
        }
    }
    
    // Utility Methods
    
    /**
     * Calculate total calories for the entire recipe.
     * 
     * @return Total calories
     */
    public double getTotalCalories() {
        return ingredients.stream()
                .mapToDouble(ing -> {
                    double factor = ing.getAmount() / 100.0;
                    return ing.getIngredient().getCaloriesPer100g() * factor;
                })
                .sum();
    }
    
    /**
     * Calculate calories per serving.
     * 
     * @return Calories per serving
     */
    public double getCaloriesPerServing() {
        return servings > 0 ? getTotalCalories() / servings : 0;
    }
    
    /**
     * Calculate total protein for the entire recipe.
     * 
     * @return Total protein in grams
     */
    public double getTotalProtein() {
        return ingredients.stream()
                .mapToDouble(ing -> {
                    double factor = ing.getAmount() / 100.0;
                    return ing.getIngredient().getProteinPer100g() * factor;
                })
                .sum();
    }
    
    /**
     * Calculate protein per serving.
     * 
     * @return Protein per serving in grams
     */
    public double getProteinPerServing() {
        return servings > 0 ? getTotalProtein() / servings : 0;
    }
    
    /**
     * Calculate total carbohydrates for the entire recipe.
     * 
     * @return Total carbohydrates in grams
     */
    public double getTotalCarbohydrates() {
        return ingredients.stream()
                .mapToDouble(ing -> {
                    double factor = ing.getAmount() / 100.0;
                    return ing.getIngredient().getCarbsPer100g() * factor;
                })
                .sum();
    }
    
    /**
     * Calculate carbohydrates per serving.
     * 
     * @return Carbohydrates per serving in grams
     */
    public double getCarbohydratesPerServing() {
        return servings > 0 ? getTotalCarbohydrates() / servings : 0;
    }
    
    /**
     * Calculate total fat for the entire recipe.
     * 
     * @return Total fat in grams
     */
    public double getTotalFat() {
        return ingredients.stream()
                .mapToDouble(ing -> {
                    double factor = ing.getAmount() / 100.0;
                    return ing.getIngredient().getFatPer100g() * factor;
                })
                .sum();
    }
    
    /**
     * Calculate fat per serving.
     * 
     * @return Fat per serving in grams
     */
    public double getFatPerServing() {
        return servings > 0 ? getTotalFat() / servings : 0;
    }
    
    /**
     * Check if recipe is suitable for a specific dietary restriction.
     * 
     * @param restriction Dietary restriction to check
     * @return true if suitable, false otherwise
     */
    public boolean isSuitableFor(String restriction) {
        if (restriction == null) return false;
        
        String lowerRestriction = restriction.toLowerCase();
        return tags.stream().anyMatch(tag -> tag.toLowerCase().contains(lowerRestriction));
    }
    
    /**
     * Get nutrition summary as formatted string.
     * 
     * @return Formatted nutrition summary
     */
    public String getNutritionSummary() {
        return String.format("Per Serving: %.0f cal, %.1fg protein, %.1fg carbs, %.1fg fat",
                getCaloriesPerServing(), getProteinPerServing(), 
                getCarbohydratesPerServing(), getFatPerServing());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Recipe recipe = (Recipe) obj;
        return Objects.equals(id, recipe.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return name != null ? name : "Unnamed Recipe";
    }
    
    /**
     * Validate recipe data integrity.
     * 
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        return name != null && !name.trim().isEmpty() && 
               servings > 0 && 
               ingredients != null;
    }
    
    /**
     * Get detailed recipe information for display.
     * 
     * @return Detailed recipe string
     */
    public String getDetailedInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Recipe: ").append(name).append("\n");
        sb.append("Description: ").append(description != null ? description : "No description").append("\n");
        sb.append("Servings: ").append(servings).append("\n");
        sb.append("Prep Time: ").append(prepTimeMinutes).append(" min\n");
        sb.append("Cook Time: ").append(cookTimeMinutes).append(" min\n");
        sb.append("Difficulty: ").append(difficulty).append("\n");
        sb.append("Rating: ").append(rating).append("/5\n");
        sb.append("Ingredients: ").append(ingredients.size()).append("\n");
        sb.append(getNutritionSummary());
        return sb.toString();
    }
}
