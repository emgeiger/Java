package com.nutrition.calculator.model;

import java.util.*;
import java.time.LocalDateTime;

/**
 * Represents a food ingredient with nutritional information and metadata.
 * Used as the base component for recipes and meal tracking in the nutrition calculator.
 */
public class Ingredient {
    
    private String id;
    private String name;
    private String description;
    private NutritionalInfo nutritionalInfo;
    private double referenceAmount;
    private MeasurementUnit referenceUnit;
    private String category;
    private String databaseId; // External database reference (e.g., USDA food ID)
    private Set<String> allergens;
    private Set<String> dietaryTags; // vegetarian, vegan, gluten-free, etc.
    private boolean isVerified;
    private String barcode;
    private String brand;
    private Map<String, Double> commonServingSizes; // "medium apple" -> 182g
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private double recipeScaleFactor;
    
    /**
     * Default constructor
     */
    public Ingredient() {
        this.id = UUID.randomUUID().toString();
        this.allergens = new HashSet<>();
        this.dietaryTags = new HashSet<>();
        this.commonServingSizes = new HashMap<>();
        this.isVerified = false;
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
        this.recipeScaleFactor = 1.0;
    }
    
    /**
     * Constructor with basic parameters
     */
    public Ingredient(String name, String description, NutritionalInfo nutritionalInfo,
                     double referenceAmount, MeasurementUnit referenceUnit, String category) {
        this();
        this.name = name;
        this.description = description;
        this.nutritionalInfo = nutritionalInfo;
        this.referenceAmount = referenceAmount;
        this.referenceUnit = referenceUnit;
        this.category = category;
        validateBasicParameters();
    }
    
    /**
     * Full constructor
     */
    public Ingredient(String name, String description, NutritionalInfo nutritionalInfo,
                     double referenceAmount, MeasurementUnit referenceUnit, String category,
                     String databaseId) {
        this(name, description, nutritionalInfo, referenceAmount, referenceUnit, category);
        this.databaseId = databaseId;
    }
    
    /**
     * Validate basic constructor parameters
     */
    private void validateBasicParameters() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Ingredient name cannot be null or empty");
        }
        
        if (nutritionalInfo == null) {
            throw new IllegalArgumentException("Nutritional information cannot be null");
        }
        
        if (referenceAmount <= 0) {
            throw new IllegalArgumentException("Reference amount must be positive");
        }
        
        if (referenceUnit == null) {
            throw new IllegalArgumentException("Reference unit cannot be null");
        }
    }
    
    /**
     * Calculate nutritional information for a specific amount and unit
     */
    public NutritionalInfo calculateNutritionForAmount(double amount, MeasurementUnit unit) {
        if (!isCompatibleWithUnit(unit)) {
            throw new IllegalArgumentException("Unit " + unit.getSymbol() + 
                " is not compatible with ingredient " + name);
        }
        
        // Convert to reference unit if different
        double convertedAmount = amount;
        if (!unit.equals(referenceUnit)) {
            convertedAmount = MeasurementUnit.convert(amount, unit, referenceUnit);
        }
        
        // Calculate scaling factor
        double scaleFactor = convertedAmount / referenceAmount;
        
        return new NutritionalInfo.Builder()
            .calories(nutritionalInfo.getCalories() * scaleFactor)
            .protein(nutritionalInfo.getProtein() * scaleFactor)
            .carbohydrates(nutritionalInfo.getCarbohydrates() * scaleFactor)
            .fat(nutritionalInfo.getFat() * scaleFactor)
            .fiber(nutritionalInfo.getFiber() * scaleFactor)
            .sugar(nutritionalInfo.getSugar() * scaleFactor)
            .sodium(nutritionalInfo.getSodium() * scaleFactor)
            .build();
    }
    
    /**
     * Calculate calories for a specific amount and unit
     */
    public double calculateCaloriesForAmount(double amount, MeasurementUnit unit) {
        NutritionalInfo nutrition = calculateNutritionForAmount(amount, unit);
        return nutrition.getCalories();
    }
    
    /**
     * Get macronutrient breakdown for specific amount
     */
    public Map<String, Double> getMacronutrientBreakdown(double amount, MeasurementUnit unit) {
        NutritionalInfo nutrition = calculateNutritionForAmount(amount, unit);
        Map<String, Double> breakdown = new HashMap<>();
        breakdown.put("protein", nutrition.getProtein());
        breakdown.put("carbohydrates", nutrition.getCarbohydrates());
        breakdown.put("fat", nutrition.getFat());
        return breakdown;
    }
    
    /**
     * Get macronutrient percentages by calories
     */
    public Map<String, Double> getMacronutrientPercentages(double amount, MeasurementUnit unit) {
        NutritionalInfo nutrition = calculateNutritionForAmount(amount, unit);
        
        double proteinCal = nutrition.getProtein() * 4; // 4 cal/g protein
        double carbCal = nutrition.getCarbohydrates() * 4; // 4 cal/g carbs
        double fatCal = nutrition.getFat() * 9; // 9 cal/g fat
        double totalMacroCal = proteinCal + carbCal + fatCal;
        
        Map<String, Double> percentages = new HashMap<>();
        if (totalMacroCal > 0) {
            percentages.put("protein", (proteinCal / totalMacroCal) * 100);
            percentages.put("carbohydrates", (carbCal / totalMacroCal) * 100);
            percentages.put("fat", (fatCal / totalMacroCal) * 100);
        } else {
            percentages.put("protein", 0.0);
            percentages.put("carbohydrates", 0.0);
            percentages.put("fat", 0.0);
        }
        
        return percentages;
    }
    
    /**
     * Check if ingredient has complete nutritional information
     */
    public boolean hasCompleteNutritionalInfo() {
        return nutritionalInfo != null &&
               nutritionalInfo.getCalories() >= 0 &&
               nutritionalInfo.getProtein() >= 0 &&
               nutritionalInfo.getCarbohydrates() >= 0 &&
               nutritionalInfo.getFat() >= 0;
    }
    
    /**
     * Check if ingredient is compatible with a measurement unit
     */
    public boolean isCompatibleWithUnit(MeasurementUnit unit) {
        if (unit == null || referenceUnit == null) {
            return false;
        }
        return referenceUnit.isCompatibleWith(unit);
    }
    
    /**
     * Format serving information
     */
    public String formatServingInfo() {
        return String.format("%.1f %s - %.0f calories", 
                           referenceAmount, 
                           referenceUnit.getSymbol(), 
                           nutritionalInfo.getCalories());
    }
    
    /**
     * Get common serving sizes for this ingredient
     */
    public Map<String, Double> getCommonServingSizes() {
        Map<String, Double> servings = new HashMap<>(commonServingSizes);
        
        // Add default serving based on category if not already present
        if (servings.isEmpty()) {
            addDefaultServingSizes(servings);
        }
        
        return servings;
    }
    
    /**
     * Add default serving sizes based on ingredient category
     */
    private void addDefaultServingSizes(Map<String, Double> servings) {
        String lowerCategory = category != null ? category.toLowerCase() : "";
        
        if (lowerCategory.contains("fruit")) {
            servings.put("medium piece", 150.0);
            servings.put("cup sliced", 165.0);
        } else if (lowerCategory.contains("vegetable")) {
            servings.put("cup raw", 100.0);
            servings.put("cup cooked", 120.0);
        } else if (lowerCategory.contains("meat") || lowerCategory.contains("protein")) {
            servings.put("3 oz serving", 85.0);
            servings.put("4 oz serving", 113.0);
        } else if (lowerCategory.contains("grain")) {
            servings.put("cup cooked", 200.0);
            servings.put("slice", 28.0);
        }
    }
    
    /**
     * Check if ingredient is suitable for a specific diet
     */
    public boolean isSuitableForDiet(String diet) {
        if (diet == null) {
            return true;
        }
        
        String lowerDiet = diet.toLowerCase();
        
        // Check dietary tags first
        if (dietaryTags.stream().anyMatch(tag -> tag.toLowerCase().contains(lowerDiet))) {
            return true;
        }
        
        // Basic diet compatibility based on category and common knowledge
        String lowerCategory = category != null ? category.toLowerCase() : "";
        String lowerName = name != null ? name.toLowerCase() : "";
        
        switch (lowerDiet) {
            case "vegan":
                return !lowerCategory.contains("meat") && 
                       !lowerCategory.contains("dairy") && 
                       !lowerCategory.contains("egg") &&
                       !lowerName.contains("cheese") &&
                       !lowerName.contains("milk") &&
                       !lowerName.contains("butter");
                       
            case "vegetarian":
                return !lowerCategory.contains("meat") && 
                       !lowerCategory.contains("fish") &&
                       !lowerName.contains("chicken") &&
                       !lowerName.contains("beef") &&
                       !lowerName.contains("pork");
                       
            case "gluten-free":
                return !lowerName.contains("wheat") &&
                       !lowerName.contains("barley") &&
                       !lowerName.contains("rye") &&
                       !lowerName.contains("bread") &&
                       !lowerName.contains("pasta");
                       
            case "keto":
                // High fat, very low carb
                return nutritionalInfo.getCarbohydrates() < 5.0;
                
            case "low-sodium":
                return nutritionalInfo.getSodium() < 140.0; // mg per serving
                
            default:
                return true;
        }
    }
    
    /**
     * Get allergen information
     */
    public Set<String> getAllergens() {
        return new HashSet<>(allergens);
    }
    
    /**
     * Add allergen
     */
    public void addAllergen(String allergen) {
        if (allergen != null && !allergen.trim().isEmpty()) {
            allergens.add(allergen.trim().toLowerCase());
            updateModificationTime();
        }
    }
    
    /**
     * Remove allergen
     */
    public boolean removeAllergen(String allergen) {
        boolean removed = allergens.remove(allergen);
        if (removed) {
            updateModificationTime();
        }
        return removed;
    }
    
    /**
     * Add dietary tag
     */
    public void addDietaryTag(String tag) {
        if (tag != null && !tag.trim().isEmpty()) {
            dietaryTags.add(tag.trim().toLowerCase());
            updateModificationTime();
        }
    }
    
    /**
     * Remove dietary tag
     */
    public boolean removeDietaryTag(String tag) {
        boolean removed = dietaryTags.remove(tag);
        if (removed) {
            updateModificationTime();
        }
        return removed;
    }
    
    /**
     * Get protein density (protein per calorie)
     */
    public double getProteinDensity() {
        if (nutritionalInfo.getCalories() > 0) {
            return nutritionalInfo.getProtein() / nutritionalInfo.getCalories();
        }
        return 0.0;
    }
    
    /**
     * Get calorie density (calories per gram)
     */
    public double getCalorieDensity() {
        if (referenceAmount > 0 && referenceUnit != null && 
            referenceUnit.getType() == MeasurementUnit.UnitType.WEIGHT) {
            // Convert to grams if not already
            double grams = referenceAmount;
            if (!referenceUnit.getSymbol().equals("g")) {
                grams = MeasurementUnit.convert(referenceAmount, referenceUnit, 
                    MeasurementUnit.getBaseUnit(MeasurementUnit.UnitType.WEIGHT));
            }
            return nutritionalInfo.getCalories() / grams;
        }
        return 0.0;
    }
    
    /**
     * Check if ingredient meets specific nutritional criteria
     */
    public boolean meetsNutritionalCriteria(String criteria, double threshold) {
        if (criteria == null) {
            return true;
        }
        
        String lowerCriteria = criteria.toLowerCase();
        
        switch (lowerCriteria) {
            case "low-fat":
                return nutritionalInfo.getFat() < threshold;
            case "high-protein":
                return nutritionalInfo.getProtein() > threshold;
            case "low-carb":
                return nutritionalInfo.getCarbohydrates() < threshold;
            case "high-fiber":
                return nutritionalInfo.getFiber() > threshold;
            case "low-calorie":
                return nutritionalInfo.getCalories() < threshold;
            case "low-sugar":
                return nutritionalInfo.getSugar() < threshold;
            default:
                return true;
        }
    }
    
    /**
     * Scale ingredient for recipe use
     */
    public Ingredient scaleForRecipe(double scaleFactor) {
        Ingredient scaled = new Ingredient();
        scaled.id = UUID.randomUUID().toString();
        scaled.name = this.name;
        scaled.description = this.description;
        scaled.nutritionalInfo = this.nutritionalInfo; // Keep per reference amount
        scaled.referenceAmount = this.referenceAmount;
        scaled.referenceUnit = this.referenceUnit;
        scaled.category = this.category;
        scaled.databaseId = this.databaseId;
        scaled.allergens = new HashSet<>(this.allergens);
        scaled.dietaryTags = new HashSet<>(this.dietaryTags);
        scaled.isVerified = this.isVerified;
        scaled.brand = this.brand;
        scaled.commonServingSizes = new HashMap<>(this.commonServingSizes);
        scaled.recipeScaleFactor = scaleFactor;
        scaled.createdDate = LocalDateTime.now();
        scaled.updatedDate = LocalDateTime.now();
        
        return scaled;
    }
    
    /**
     * Update modification timestamp
     */
    private void updateModificationTime() {
        this.updatedDate = LocalDateTime.now();
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
            throw new IllegalArgumentException("Ingredient name cannot be null or empty");
        }
        this.name = name.trim();
        updateModificationTime();
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
        updateModificationTime();
    }
    
    public NutritionalInfo getNutritionalInfo() {
        return nutritionalInfo;
    }
    
    public void setNutritionalInfo(NutritionalInfo nutritionalInfo) {
        if (nutritionalInfo == null) {
            throw new IllegalArgumentException("Nutritional information cannot be null");
        }
        this.nutritionalInfo = nutritionalInfo;
        updateModificationTime();
    }
    
    public double getReferenceAmount() {
        return referenceAmount;
    }
    
    public void setReferenceAmount(double referenceAmount) {
        if (referenceAmount <= 0) {
            throw new IllegalArgumentException("Reference amount must be positive");
        }
        this.referenceAmount = referenceAmount;
        updateModificationTime();
    }
    
    public MeasurementUnit getReferenceUnit() {
        return referenceUnit;
    }
    
    public void setReferenceUnit(MeasurementUnit referenceUnit) {
        if (referenceUnit == null) {
            throw new IllegalArgumentException("Reference unit cannot be null");
        }
        this.referenceUnit = referenceUnit;
        updateModificationTime();
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
        updateModificationTime();
    }
    
    public String getDatabaseId() {
        return databaseId;
    }
    
    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }
    
    public boolean isVerified() {
        return isVerified;
    }
    
    public void setVerified(boolean verified) {
        isVerified = verified;
        updateModificationTime();
    }
    
    public String getBarcode() {
        return barcode;
    }
    
    public void setBarcode(String barcode) {
        this.barcode = barcode;
        updateModificationTime();
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
        updateModificationTime();
    }
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }
    
    public double getRecipeScaleFactor() {
        return recipeScaleFactor;
    }
    
    public Set<String> getDietaryTags() {
        return new HashSet<>(dietaryTags);
    }
    
    public void setDietaryTags(Set<String> dietaryTags) {
        this.dietaryTags = dietaryTags != null ? new HashSet<>(dietaryTags) : new HashSet<>();
        updateModificationTime();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Ingredient{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", category='" + category + '\'' +
               ", calories=" + (nutritionalInfo != null ? nutritionalInfo.getCalories() : 0) +
               ", referenceAmount=" + referenceAmount +
               ", referenceUnit=" + (referenceUnit != null ? referenceUnit.getSymbol() : "null") +
               '}';
    }
}
