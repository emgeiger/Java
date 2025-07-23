package com.nutrition.calculator.model;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

/**
 * Ingredient - Model class representing a food ingredient
 * 
 * This class represents a food ingredient with comprehensive nutrition
 * information per 100g serving. It supports nutrition calculations
 * and ingredient management throughout the application.
 * 
 * @author NutritionCalculator Development Team
 * @version 1.0
 */
public class Ingredient {
    
    private String id;
    private String name;
    private String description;
    private String category;
    private String brand;
    
    // Nutrition per 100g
    private double caloriesPer100g;
    private double proteinPer100g;
    private double carbsPer100g;
    private double fatPer100g;
    private double fiberPer100g;
    private double sugarPer100g;
    private double sodiumPer100g;
    
    // Vitamins per 100g (in mg unless specified)
    private double vitaminAPer100g;
    private double vitaminCPer100g;
    private double vitaminDPer100g; // in IU
    private double vitaminEPer100g;
    private double vitaminKPer100g; // in mcg
    
    // B Vitamins per 100g (in mg unless specified)
    private double thiaminPer100g;
    private double riboflavinPer100g;
    private double niacinPer100g;
    private double vitaminB6Per100g;
    private double folatePer100g; // in mcg
    private double vitaminB12Per100g; // in mcg
    
    // Minerals per 100g (in mg unless specified)
    private double calciumPer100g;
    private double ironPer100g;
    private double magnesiumPer100g;
    private double phosphorusPer100g;
    private double potassiumPer100g;
    private double zincPer100g;
    
    // Additional properties
    private boolean isOrganic;
    private boolean isGlutenFree;
    private boolean isDairyFree;
    private boolean isVegan;
    private boolean isVegetarian;
    private List<String> allergens;
    private List<String> tags;
    private String barcode;
    private String imageUrl;
    private long createdDate;
    private long lastModified;
    private String source; // Where nutrition data came from
    private boolean isVerified;
    
    /**
     * Default constructor.
     */
    public Ingredient() {
        this.allergens = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.createdDate = System.currentTimeMillis();
        this.lastModified = System.currentTimeMillis();
        this.isVerified = false;
        this.isOrganic = false;
        this.isGlutenFree = false;
        this.isDairyFree = false;
        this.isVegan = false;
        this.isVegetarian = false;
    }
    
    /**
     * Constructor with basic nutrition information.
     * 
     * @param name Ingredient name
     * @param caloriesPer100g Calories per 100g
     * @param proteinPer100g Protein per 100g
     * @param carbsPer100g Carbohydrates per 100g
     * @param fatPer100g Fat per 100g
     */
    public Ingredient(String name, double caloriesPer100g, double proteinPer100g, 
                     double carbsPer100g, double fatPer100g) {
        this();
        this.name = name;
        this.caloriesPer100g = caloriesPer100g;
        this.proteinPer100g = proteinPer100g;
        this.carbsPer100g = carbsPer100g;
        this.fatPer100g = fatPer100g;
    }
    
    /**
     * Copy constructor.
     * 
     * @param other Ingredient to copy from
     */
    public Ingredient(Ingredient other) {
        this.id = other.id;
        this.name = other.name;
        this.description = other.description;
        this.category = other.category;
        this.brand = other.brand;
        
        this.caloriesPer100g = other.caloriesPer100g;
        this.proteinPer100g = other.proteinPer100g;
        this.carbsPer100g = other.carbsPer100g;
        this.fatPer100g = other.fatPer100g;
        this.fiberPer100g = other.fiberPer100g;
        this.sugarPer100g = other.sugarPer100g;
        this.sodiumPer100g = other.sodiumPer100g;
        
        this.vitaminAPer100g = other.vitaminAPer100g;
        this.vitaminCPer100g = other.vitaminCPer100g;
        this.vitaminDPer100g = other.vitaminDPer100g;
        this.vitaminEPer100g = other.vitaminEPer100g;
        this.vitaminKPer100g = other.vitaminKPer100g;
        
        this.thiaminPer100g = other.thiaminPer100g;
        this.riboflavinPer100g = other.riboflavinPer100g;
        this.niacinPer100g = other.niacinPer100g;
        this.vitaminB6Per100g = other.vitaminB6Per100g;
        this.folatePer100g = other.folatePer100g;
        this.vitaminB12Per100g = other.vitaminB12Per100g;
        
        this.calciumPer100g = other.calciumPer100g;
        this.ironPer100g = other.ironPer100g;
        this.magnesiumPer100g = other.magnesiumPer100g;
        this.phosphorusPer100g = other.phosphorusPer100g;
        this.potassiumPer100g = other.potassiumPer100g;
        this.zincPer100g = other.zincPer100g;
        
        this.isOrganic = other.isOrganic;
        this.isGlutenFree = other.isGlutenFree;
        this.isDairyFree = other.isDairyFree;
        this.isVegan = other.isVegan;
        this.isVegetarian = other.isVegetarian;
        this.allergens = new ArrayList<>(other.allergens);
        this.tags = new ArrayList<>(other.tags);
        this.barcode = other.barcode;
        this.imageUrl = other.imageUrl;
        this.createdDate = other.createdDate;
        this.lastModified = System.currentTimeMillis();
        this.source = other.source;
        this.isVerified = other.isVerified;
    }
    
    // Basic Getters and Setters
    
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
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
        this.lastModified = System.currentTimeMillis();
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
        this.lastModified = System.currentTimeMillis();
    }
    
    // Macronutrient Getters and Setters
    
    public double getCaloriesPer100g() {
        return caloriesPer100g;
    }
    
    public void setCaloriesPer100g(double caloriesPer100g) {
        this.caloriesPer100g = Math.max(0, caloriesPer100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getProteinPer100g() {
        return proteinPer100g;
    }
    
    public void setProteinPer100g(double proteinPer100g) {
        this.proteinPer100g = Math.max(0, proteinPer100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getCarbsPer100g() {
        return carbsPer100g;
    }
    
    public void setCarbsPer100g(double carbsPer100g) {
        this.carbsPer100g = Math.max(0, carbsPer100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getFatPer100g() {
        return fatPer100g;
    }
    
    public void setFatPer100g(double fatPer100g) {
        this.fatPer100g = Math.max(0, fatPer100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getFiberPer100g() {
        return fiberPer100g;
    }
    
    public void setFiberPer100g(double fiberPer100g) {
        this.fiberPer100g = Math.max(0, fiberPer100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getSugarPer100g() {
        return sugarPer100g;
    }
    
    public void setSugarPer100g(double sugarPer100g) {
        this.sugarPer100g = Math.max(0, sugarPer100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getSodiumPer100g() {
        return sodiumPer100g;
    }
    
    public void setSodiumPer100g(double sodiumPer100g) {
        this.sodiumPer100g = Math.max(0, sodiumPer100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    // Vitamin Getters and Setters
    
    public double getVitaminAPer100g() {
        return vitaminAPer100g;
    }
    
    public void setVitaminAPer100g(double vitaminAPer100g) {
        this.vitaminAPer100g = Math.max(0, vitaminAPer100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getVitaminCPer100g() {
        return vitaminCPer100g;
    }
    
    public void setVitaminCPer100g(double vitaminCPer100g) {
        this.vitaminCPer100g = Math.max(0, vitaminCPer100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getVitaminDPer100g() {
        return vitaminDPer100g;
    }
    
    public void setVitaminDPer100g(double vitaminDPer100g) {
        this.vitaminDPer100g = Math.max(0, vitaminDPer100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getVitaminEPer100g() {
        return vitaminEPer100g;
    }
    
    public void setVitaminEPer100g(double vitaminEPer100g) {
        this.vitaminEPer100g = Math.max(0, vitaminEPer100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getVitaminKPer100g() {
        return vitaminKPer100g;
    }
    
    public void setVitaminKPer100g(double vitaminKPer100g) {
        this.vitaminKPer100g = Math.max(0, vitaminKPer100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    // B Vitamin Getters and Setters
    
    public double getThiaminPer100g() {
        return thiaminPer100g;
    }
    
    public void setThiaminPer100g(double thiaminPer100g) {
        this.thiaminPer100g = Math.max(0, thiaminPer100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getRiboflavinPer100g() {
        return riboflavinPer100g;
    }
    
    public void setRiboflavinPer100g(double riboflavinPer100g) {
        this.riboflavinPer100g = Math.max(0, riboflavinPer100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getNiacinPer100g() {
        return niacinPer100g;
    }
    
    public void setNiacinPer100g(double niacinPer100g) {
        this.niacinPer100g = Math.max(0, niacinPer100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getVitaminB6Per100g() {
        return vitaminB6Per100g;
    }
    
    public void setVitaminB6Per100g(double vitaminB6Per100g) {
        this.vitaminB6Per100g = Math.max(0, vitaminB6Per100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getFolatePer100g() {
        return folatePer100g;
    }
    
    public void setFolatePer100g(double folatePer100g) {
        this.folatePer100g = Math.max(0, folatePer100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getVitaminB12Per100g() {
        return vitaminB12Per100g;
    }
    
    public void setVitaminB12Per100g(double vitaminB12Per100g) {
        this.vitaminB12Per100g = Math.max(0, vitaminB12Per100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    // Mineral Getters and Setters
    
    public double getCalciumPer100g() {
        return calciumPer100g;
    }
    
    public void setCalciumPer100g(double calciumPer100g) {
        this.calciumPer100g = Math.max(0, calciumPer100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getIronPer100g() {
        return ironPer100g;
    }
    
    public void setIronPer100g(double ironPer100g) {
        this.ironPer100g = Math.max(0, ironPer100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getMagnesiumPer100g() {
        return magnesiumPer100g;
    }
    
    public void setMagnesiumPer100g(double magnesiumPer100g) {
        this.magnesiumPer100g = Math.max(0, magnesiumPer100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getPhosphorusPer100g() {
        return phosphorusPer100g;
    }
    
    public void setPhosphorusPer100g(double phosphorusPer100g) {
        this.phosphorusPer100g = Math.max(0, phosphorusPer100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getPotassiumPer100g() {
        return potassiumPer100g;
    }
    
    public void setPotassiumPer100g(double potassiumPer100g) {
        this.potassiumPer100g = Math.max(0, potassiumPer100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    public double getZincPer100g() {
        return zincPer100g;
    }
    
    public void setZincPer100g(double zincPer100g) {
        this.zincPer100g = Math.max(0, zincPer100g);
        this.lastModified = System.currentTimeMillis();
    }
    
    // Dietary Properties Getters and Setters
    
    public boolean isOrganic() {
        return isOrganic;
    }
    
    public void setOrganic(boolean organic) {
        isOrganic = organic;
        this.lastModified = System.currentTimeMillis();
    }
    
    public boolean isGlutenFree() {
        return isGlutenFree;
    }
    
    public void setGlutenFree(boolean glutenFree) {
        isGlutenFree = glutenFree;
        this.lastModified = System.currentTimeMillis();
    }
    
    public boolean isDairyFree() {
        return isDairyFree;
    }
    
    public void setDairyFree(boolean dairyFree) {
        isDairyFree = dairyFree;
        this.lastModified = System.currentTimeMillis();
    }
    
    public boolean isVegan() {
        return isVegan;
    }
    
    public void setVegan(boolean vegan) {
        isVegan = vegan;
        this.lastModified = System.currentTimeMillis();
    }
    
    public boolean isVegetarian() {
        return isVegetarian;
    }
    
    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
        this.lastModified = System.currentTimeMillis();
    }
    
    // Collection Properties
    
    public List<String> getAllergens() {
        return allergens;
    }
    
    public void setAllergens(List<String> allergens) {
        this.allergens = allergens != null ? allergens : new ArrayList<>();
        this.lastModified = System.currentTimeMillis();
    }
    
    public void addAllergen(String allergen) {
        if (allergen != null && !allergen.trim().isEmpty() && !this.allergens.contains(allergen.trim())) {
            this.allergens.add(allergen.trim());
            this.lastModified = System.currentTimeMillis();
        }
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
    
    // Additional Properties
    
    public String getBarcode() {
        return barcode;
    }
    
    public void setBarcode(String barcode) {
        this.barcode = barcode;
        this.lastModified = System.currentTimeMillis();
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        this.lastModified = System.currentTimeMillis();
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
    
    public String getSource() {
        return source;
    }
    
    public void setSource(String source) {
        this.source = source;
        this.lastModified = System.currentTimeMillis();
    }
    
    public boolean isVerified() {
        return isVerified;
    }
    
    public void setVerified(boolean verified) {
        isVerified = verified;
        this.lastModified = System.currentTimeMillis();
    }
    
    // Utility Methods
    
    /**
     * Calculate nutrition values for a specific amount.
     * 
     * @param amount Amount in grams
     * @return Array of [calories, protein, carbs, fat]
     */
    public double[] calculateNutritionForAmount(double amount) {
        double factor = amount / 100.0;
        return new double[]{
            caloriesPer100g * factor,
            proteinPer100g * factor,
            carbsPer100g * factor,
            fatPer100g * factor
        };
    }
    
    /**
     * Get nutrition summary for 100g.
     * 
     * @return Formatted nutrition summary
     */
    public String getNutritionSummary() {
        return String.format("Per 100g: %.0f cal, %.1fg protein, %.1fg carbs, %.1fg fat",
                caloriesPer100g, proteinPer100g, carbsPer100g, fatPer100g);
    }
    
    /**
     * Check if ingredient contains specific allergen.
     * 
     * @param allergen Allergen to check
     * @return true if contains allergen
     */
    public boolean containsAllergen(String allergen) {
        return allergens.stream().anyMatch(a -> a.equalsIgnoreCase(allergen));
    }
    
    /**
     * Check if ingredient has specific tag.
     * 
     * @param tag Tag to check
     * @return true if has tag
     */
    public boolean hasTag(String tag) {
        return tags.stream().anyMatch(t -> t.equalsIgnoreCase(tag));
    }
    
    /**
     * Validate ingredient data.
     * 
     * @return true if valid
     */
    public boolean isValid() {
        return name != null && !name.trim().isEmpty() &&
               caloriesPer100g >= 0 && proteinPer100g >= 0 &&
               carbsPer100g >= 0 && fatPer100g >= 0;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ingredient that = (Ingredient) obj;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return name != null ? name : "Unnamed Ingredient";
    }
    
    /**
     * Get detailed ingredient information.
     * 
     * @return Detailed ingredient string
     */
    public String getDetailedInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ingredient: ").append(name).append("\n");
        if (brand != null) sb.append("Brand: ").append(brand).append("\n");
        if (category != null) sb.append("Category: ").append(category).append("\n");
        sb.append(getNutritionSummary()).append("\n");
        if (isOrganic) sb.append("Organic\n");
        if (isVegan) sb.append("Vegan\n");
        if (isVegetarian) sb.append("Vegetarian\n");
        if (isGlutenFree) sb.append("Gluten-Free\n");
        if (isDairyFree) sb.append("Dairy-Free\n");
        return sb.toString();
    }
}
