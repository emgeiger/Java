package com.nutrition.calculator.model;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a recipe with ingredients, instructions, and nutritional information.
 * Supports recipe scaling, nutritional analysis, and meal planning integration.
 */
public class Recipe {
    
    private String id;
    private String name;
    private String description;
    private List<RecipeIngredient> ingredients;
    private List<String> instructions;
    private int servings;
    private int prepTimeMinutes;
    private int cookTimeMinutes;
    private String difficulty; // "Easy", "Medium", "Hard"
    private String category; // "Breakfast", "Lunch", "Dinner", "Snack", "Dessert"
    private List<String> tags; // "vegetarian", "gluten-free", "keto", etc.
    private String imageUrl;
    private boolean isFavorite;
    private double rating; // 0.0 to 5.0
    private String source; // "Personal", "Website", "Book", etc.
    private String sourceUrl;
    private LocalDateTime createdDate;
    private LocalDateTime lastModified;
    private String notes;
    private NutritionalInfo nutritionalInfoPerServing;
    
    /**
     * Default constructor
     */
    public Recipe() {
        this.id = UUID.randomUUID().toString();
        this.ingredients = new ArrayList<>();
        this.instructions = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.servings = 1;
        this.prepTimeMinutes = 0;
        this.cookTimeMinutes = 0;
        this.difficulty = "Medium";
        this.category = "Main Course";
        this.isFavorite = false;
        this.rating = 0.0;
        this.createdDate = LocalDateTime.now();
        this.lastModified = LocalDateTime.now();
    }
    
    /**
     * Constructor with basic parameters
     */
    public Recipe(String name, String description, int servings) {
        this();
        this.name = name;
        this.description = description;
        this.servings = servings;
        validateBasicParameters();
    }
    
    /**
     * Full constructor
     */
    public Recipe(String id, String name, String description, List<RecipeIngredient> ingredients,
                  List<String> instructions, int servings, int prepTimeMinutes, int cookTimeMinutes,
                  String difficulty, String category, List<String> tags, String imageUrl,
                  boolean isFavorite, double rating, String source, String sourceUrl, String notes) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.ingredients = ingredients != null ? new ArrayList<>(ingredients) : new ArrayList<>();
        this.instructions = instructions != null ? new ArrayList<>(instructions) : new ArrayList<>();
        this.servings = servings;
        this.prepTimeMinutes = prepTimeMinutes;
        this.cookTimeMinutes = cookTimeMinutes;
        this.difficulty = difficulty != null ? difficulty : "Medium";
        this.category = category != null ? category : "Main Course";
        this.tags = tags != null ? new ArrayList<>(tags) : new ArrayList<>();
        this.imageUrl = imageUrl;
        this.isFavorite = isFavorite;
        this.rating = rating;
        this.source = source;
        this.sourceUrl = sourceUrl;
        this.notes = notes;
        this.createdDate = LocalDateTime.now();
        this.lastModified = LocalDateTime.now();
        validateParameters();
    }
    
    /**
     * Validate constructor parameters
     */
    private void validateParameters() {
        validateBasicParameters();
        
        if (rating < 0.0 || rating > 5.0) {
            throw new IllegalArgumentException("Rating must be between 0.0 and 5.0");
        }
        
        if (prepTimeMinutes < 0) {
            throw new IllegalArgumentException("Prep time cannot be negative");
        }
        
        if (cookTimeMinutes < 0) {
            throw new IllegalArgumentException("Cook time cannot be negative");
        }
    }
    
    private void validateBasicParameters() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Recipe name cannot be null or empty");
        }
        
        if (servings <= 0) {
            throw new IllegalArgumentException("Servings must be positive");
        }
    }
    
    /**
     * Calculate total nutritional information for the entire recipe
     */
    public NutritionalInfo calculateTotalNutritionalInfo() {
        if (ingredients.isEmpty()) {
            return new NutritionalInfo.Builder().build();
        }
        
        return ingredients.stream()
            .map(RecipeIngredient::calculateNutritionalInfo)
            .reduce(new NutritionalInfo.Builder().build(), this::addNutritionalInfo);
    }
    
    /**
     * Calculate nutritional information per serving
     */
    public NutritionalInfo calculateNutritionalInfoPerServing() {
        NutritionalInfo total = calculateTotalNutritionalInfo();
        
        if (servings <= 0) {
            return total;
        }
        
        return new NutritionalInfo.Builder()
            .calories(total.getCalories() / servings)
            .protein(total.getProtein() / servings)
            .carbohydrates(total.getCarbohydrates() / servings)
            .fat(total.getFat() / servings)
            .fiber(total.getFiber() / servings)
            .sugar(total.getSugar() / servings)
            .sodium(total.getSodium() / servings)
            .build();
    }
    
    /**
     * Helper method to add nutritional information
     */
    private NutritionalInfo addNutritionalInfo(NutritionalInfo a, NutritionalInfo b) {
        return new NutritionalInfo.Builder()
            .calories(a.getCalories() + b.getCalories())
            .protein(a.getProtein() + b.getProtein())
            .carbohydrates(a.getCarbohydrates() + b.getCarbohydrates())
            .fat(a.getFat() + b.getFat())
            .fiber(a.getFiber() + b.getFiber())
            .sugar(a.getSugar() + b.getSugar())
            .sodium(a.getSodium() + b.getSodium())
            .build();
    }
    
    /**
     * Scale recipe for different number of servings
     */
    public Recipe scaleForServings(int newServings) {
        if (newServings <= 0) {
            throw new IllegalArgumentException("New servings must be positive");
        }
        
        double scaleFactor = (double) newServings / servings;
        
        List<RecipeIngredient> scaledIngredients = ingredients.stream()
            .map(ingredient -> ingredient.scaleForServings(scaleFactor))
            .collect(ArrayList::new, (list, item) -> list.add(item), ArrayList::addAll);
        
        Recipe scaledRecipe = new Recipe();
        scaledRecipe.id = UUID.randomUUID().toString();
        scaledRecipe.name = this.name + " (scaled for " + newServings + " servings)";
        scaledRecipe.description = this.description;
        scaledRecipe.ingredients = scaledIngredients;
        scaledRecipe.instructions = new ArrayList<>(this.instructions);
        scaledRecipe.servings = newServings;
        scaledRecipe.prepTimeMinutes = this.prepTimeMinutes;
        scaledRecipe.cookTimeMinutes = this.cookTimeMinutes;
        scaledRecipe.difficulty = this.difficulty;
        scaledRecipe.category = this.category;
        scaledRecipe.tags = new ArrayList<>(this.tags);
        scaledRecipe.imageUrl = this.imageUrl;
        scaledRecipe.source = this.source;
        scaledRecipe.sourceUrl = this.sourceUrl;
        scaledRecipe.notes = this.notes;
        scaledRecipe.createdDate = LocalDateTime.now();
        scaledRecipe.lastModified = LocalDateTime.now();
        
        return scaledRecipe;
    }
    
    /**
     * Add ingredient to recipe
     */
    public void addIngredient(RecipeIngredient ingredient) {
        if (ingredient == null) {
            throw new IllegalArgumentException("Ingredient cannot be null");
        }
        
        ingredients.add(ingredient);
        updateLastModified();
        clearCachedNutrition();
    }
    
    /**
     * Remove ingredient from recipe
     */
    public boolean removeIngredient(String ingredientId) {
        boolean removed = ingredients.removeIf(ingredient -> 
            Objects.equals(ingredient.getId(), ingredientId));
        
        if (removed) {
            updateLastModified();
            clearCachedNutrition();
        }
        
        return removed;
    }
    
    /**
     * Update ingredient in recipe
     */
    public boolean updateIngredient(String ingredientId, RecipeIngredient updatedIngredient) {
        for (int i = 0; i < ingredients.size(); i++) {
            if (Objects.equals(ingredients.get(i).getId(), ingredientId)) {
                ingredients.set(i, updatedIngredient);
                updateLastModified();
                clearCachedNutrition();
                return true;
            }
        }
        return false;
    }
    
    /**
     * Add instruction to recipe
     */
    public void addInstruction(String instruction) {
        if (instruction == null || instruction.trim().isEmpty()) {
            throw new IllegalArgumentException("Instruction cannot be null or empty");
        }
        
        instructions.add(instruction.trim());
        updateLastModified();
    }
    
    /**
     * Add instruction at specific position
     */
    public void addInstruction(int index, String instruction) {
        if (instruction == null || instruction.trim().isEmpty()) {
            throw new IllegalArgumentException("Instruction cannot be null or empty");
        }
        
        if (index < 0 || index > instructions.size()) {
            throw new IndexOutOfBoundsException("Invalid instruction index");
        }
        
        instructions.add(index, instruction.trim());
        updateLastModified();
    }
    
    /**
     * Remove instruction from recipe
     */
    public boolean removeInstruction(int index) {
        if (index >= 0 && index < instructions.size()) {
            instructions.remove(index);
            updateLastModified();
            return true;
        }
        return false;
    }
    
    /**
     * Update instruction at specific index
     */
    public boolean updateInstruction(int index, String newInstruction) {
        if (newInstruction == null || newInstruction.trim().isEmpty()) {
            throw new IllegalArgumentException("Instruction cannot be null or empty");
        }
        
        if (index >= 0 && index < instructions.size()) {
            instructions.set(index, newInstruction.trim());
            updateLastModified();
            return true;
        }
        return false;
    }
    
    /**
     * Get total cooking time (prep + cook)
     */
    public int getTotalTimeMinutes() {
        return prepTimeMinutes + cookTimeMinutes;
    }
    
    /**
     * Get formatted total time string
     */
    public String getFormattedTotalTime() {
        int totalMinutes = getTotalTimeMinutes();
        if (totalMinutes < 60) {
            return totalMinutes + " min";
        } else {
            int hours = totalMinutes / 60;
            int minutes = totalMinutes % 60;
            if (minutes == 0) {
                return hours + " hr";
            } else {
                return hours + " hr " + minutes + " min";
            }
        }
    }
    
    /**
     * Check if recipe is suitable for specific diet
     */
    public boolean isSuitableForDiet(String diet) {
        if (diet == null) {
            return false;
        }
        
        String lowerDiet = diet.toLowerCase();
        
        // Check tags first
        if (tags.stream().anyMatch(tag -> tag.toLowerCase().contains(lowerDiet))) {
            return true;
        }
        
        // Check ingredients for diet compatibility
        return ingredients.stream()
            .allMatch(recipeIngredient -> {
                Ingredient ingredient = recipeIngredient.getIngredient();
                return ingredient != null && ingredient.isSuitableForDiet(diet);
            });
    }
    
    /**
     * Get recipe allergens
     */
    public Set<String> getAllergens() {
        Set<String> allergens = new HashSet<>();
        
        for (RecipeIngredient recipeIngredient : ingredients) {
            Ingredient ingredient = recipeIngredient.getIngredient();
            if (ingredient != null) {
                allergens.addAll(ingredient.getAllergens());
            }
        }
        
        return allergens;
    }
    
    /**
     * Add tag to recipe
     */
    public void addTag(String tag) {
        if (tag != null && !tag.trim().isEmpty() && !tags.contains(tag.trim())) {
            tags.add(tag.trim());
            updateLastModified();
        }
    }
    
    /**
     * Remove tag from recipe
     */
    public boolean removeTag(String tag) {
        boolean removed = tags.remove(tag);
        if (removed) {
            updateLastModified();
        }
        return removed;
    }
    
    /**
     * Calculate recipe difficulty score based on various factors
     */
    public int calculateDifficultyScore() {
        int score = 0;
        
        // Base on number of ingredients
        score += ingredients.size() / 3;
        
        // Base on number of instructions
        score += instructions.size() / 2;
        
        // Base on cooking time
        score += getTotalTimeMinutes() / 30;
        
        // Base on preparation complexity
        long complexPreparations = ingredients.stream()
            .map(RecipeIngredient::getPreparation)
            .filter(prep -> prep != null && !prep.isEmpty())
            .count();
        score += complexPreparations / 2;
        
        return Math.min(score, 10); // Cap at 10
    }
    
    /**
     * Create a copy of this recipe
     */
    public Recipe copy() {
        Recipe copy = new Recipe();
        copy.id = UUID.randomUUID().toString();
        copy.name = this.name + " (Copy)";
        copy.description = this.description;
        copy.ingredients = this.ingredients.stream()
            .map(RecipeIngredient::copy)
            .collect(ArrayList::new, (list, item) -> list.add(item), ArrayList::addAll);
        copy.instructions = new ArrayList<>(this.instructions);
        copy.servings = this.servings;
        copy.prepTimeMinutes = this.prepTimeMinutes;
        copy.cookTimeMinutes = this.cookTimeMinutes;
        copy.difficulty = this.difficulty;
        copy.category = this.category;
        copy.tags = new ArrayList<>(this.tags);
        copy.imageUrl = this.imageUrl;
        copy.isFavorite = false; // Don't copy favorite status
        copy.rating = 0.0; // Reset rating for copy
        copy.source = this.source;
        copy.sourceUrl = this.sourceUrl;
        copy.notes = this.notes;
        copy.createdDate = LocalDateTime.now();
        copy.lastModified = LocalDateTime.now();
        
        return copy;
    }
    
    /**
     * Check if recipe is complete (has all required information)
     */
    public boolean isComplete() {
        return name != null && !name.trim().isEmpty() &&
               !ingredients.isEmpty() &&
               !instructions.isEmpty() &&
               servings > 0;
    }
    
    /**
     * Update last modified timestamp
     */
    private void updateLastModified() {
        this.lastModified = LocalDateTime.now();
    }
    
    /**
     * Clear cached nutritional information
     */
    private void clearCachedNutrition() {
        this.nutritionalInfoPerServing = null;
    }
    
    /**
     * Get formatted creation date
     */
    public String getFormattedCreatedDate() {
        return createdDate != null ? 
            createdDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")) : "";
    }
    
    /**
     * Get formatted last modified date
     */
    public String getFormattedLastModified() {
        return lastModified != null ? 
            lastModified.format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")) : "";
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
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Recipe name cannot be null or empty");
        }
        this.name = name.trim();
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
        return new ArrayList<>(ingredients);
    }
    
    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients != null ? new ArrayList<>(ingredients) : new ArrayList<>();
        updateLastModified();
        clearCachedNutrition();
    }
    
    public List<String> getInstructions() {
        return new ArrayList<>(instructions);
    }
    
    public void setInstructions(List<String> instructions) {
        this.instructions = instructions != null ? new ArrayList<>(instructions) : new ArrayList<>();
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
        clearCachedNutrition();
    }
    
    public int getPrepTimeMinutes() {
        return prepTimeMinutes;
    }
    
    public void setPrepTimeMinutes(int prepTimeMinutes) {
        if (prepTimeMinutes < 0) {
            throw new IllegalArgumentException("Prep time cannot be negative");
        }
        this.prepTimeMinutes = prepTimeMinutes;
        updateLastModified();
    }
    
    public int getCookTimeMinutes() {
        return cookTimeMinutes;
    }
    
    public void setCookTimeMinutes(int cookTimeMinutes) {
        if (cookTimeMinutes < 0) {
            throw new IllegalArgumentException("Cook time cannot be negative");
        }
        this.cookTimeMinutes = cookTimeMinutes;
        updateLastModified();
    }
    
    public String getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty != null ? difficulty : "Medium";
        updateLastModified();
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category != null ? category : "Main Course";
        updateLastModified();
    }
    
    public List<String> getTags() {
        return new ArrayList<>(tags);
    }
    
    public void setTags(List<String> tags) {
        this.tags = tags != null ? new ArrayList<>(tags) : new ArrayList<>();
        updateLastModified();
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        updateLastModified();
    }
    
    public boolean isFavorite() {
        return isFavorite;
    }
    
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
        updateLastModified();
    }
    
    public double getRating() {
        return rating;
    }
    
    public void setRating(double rating) {
        if (rating < 0.0 || rating > 5.0) {
            throw new IllegalArgumentException("Rating must be between 0.0 and 5.0");
        }
        this.rating = rating;
        updateLastModified();
    }
    
    public String getSource() {
        return source;
    }
    
    public void setSource(String source) {
        this.source = source;
        updateLastModified();
    }
    
    public String getSourceUrl() {
        return sourceUrl;
    }
    
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
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
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
        updateLastModified();
    }
    
    public NutritionalInfo getNutritionalInfoPerServing() {
        if (nutritionalInfoPerServing == null) {
            nutritionalInfoPerServing = calculateNutritionalInfoPerServing();
        }
        return nutritionalInfoPerServing;
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
        return "Recipe{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", servings=" + servings +
               ", totalTime=" + getFormattedTotalTime() +
               ", category='" + category + '\'' +
               ", difficulty='" + difficulty + '\'' +
               ", ingredientCount=" + ingredients.size() +
               ", instructionCount=" + instructions.size() +
               '}';
    }
}
