package com.nutrition.calculator.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.Map;
import java.util.HashMap;

@DisplayName("Ingredient Model Tests")
public class IngredientTest {
    
    private Ingredient appleIngredient;
    private Ingredient chickenBreastIngredient;
    private Ingredient oliveOilIngredient;
    private NutritionalInfo appleNutrition;
    private NutritionalInfo chickenNutrition;
    private NutritionalInfo oilNutrition;
    private MeasurementUnit gramsUnit;
    private MeasurementUnit ouncesUnit;
    
    @BeforeEach
    void setUp() {
        gramsUnit = new MeasurementUnit("g", "grams", MeasurementUnit.UnitType.WEIGHT, 1.0);
        ouncesUnit = new MeasurementUnit("oz", "ounces", MeasurementUnit.UnitType.WEIGHT, 28.3495);
        
        // Apple nutrition per 100g
        appleNutrition = new NutritionalInfo.Builder()
            .calories(52.0)
            .protein(0.3)
            .carbohydrates(13.8)
            .fat(0.2)
            .fiber(2.4)
            .sugar(10.4)
            .sodium(1.0)
            .build();
            
        // Chicken breast nutrition per 100g
        chickenNutrition = new NutritionalInfo.Builder()
            .calories(165.0)
            .protein(31.0)
            .carbohydrates(0.0)
            .fat(3.6)
            .fiber(0.0)
            .sugar(0.0)
            .sodium(74.0)
            .build();
            
        // Olive oil nutrition per 100g
        oilNutrition = new NutritionalInfo.Builder()
            .calories(884.0)
            .protein(0.0)
            .carbohydrates(0.0)
            .fat(100.0)
            .fiber(0.0)
            .sugar(0.0)
            .sodium(2.0)
            .build();
            
        appleIngredient = new Ingredient("Apple", "Fresh apple", appleNutrition, 
            100.0, gramsUnit, "Fruits", "USDA:09003");
        chickenBreastIngredient = new Ingredient("Chicken Breast", "Skinless, boneless chicken breast", 
            chickenNutrition, 100.0, gramsUnit, "Proteins", "USDA:05062");
        oliveOilIngredient = new Ingredient("Olive Oil", "Extra virgin olive oil", 
            oilNutrition, 100.0, gramsUnit, "Fats", "USDA:04053");
    }
    
    @Test
    @DisplayName("Should create ingredient with valid parameters")
    void testIngredientCreation() {
        assertNotNull(appleIngredient);
        assertEquals("Apple", appleIngredient.getName());
        assertEquals("Fresh apple", appleIngredient.getDescription());
        assertEquals(appleNutrition, appleIngredient.getNutritionalInfo());
        assertEquals(100.0, appleIngredient.getReferenceAmount(), 0.001);
        assertEquals(gramsUnit, appleIngredient.getReferenceUnit());
        assertEquals("Fruits", appleIngredient.getCategory());
        assertEquals("USDA:09003", appleIngredient.getDatabaseId());
    }
    
    @Test
    @DisplayName("Should throw exception for null name")
    void testNullNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ingredient(null, "Description", appleNutrition, 100.0, gramsUnit, "Category", "ID");
        });
    }
    
    @Test
    @DisplayName("Should throw exception for empty name")
    void testEmptyNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ingredient("", "Description", appleNutrition, 100.0, gramsUnit, "Category", "ID");
        });
    }
    
    @Test
    @DisplayName("Should throw exception for null nutritional info")
    void testNullNutritionalInfoThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ingredient("Name", "Description", null, 100.0, gramsUnit, "Category", "ID");
        });
    }
    
    @Test
    @DisplayName("Should throw exception for negative reference amount")
    void testNegativeReferenceAmountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ingredient("Name", "Description", appleNutrition, -100.0, gramsUnit, "Category", "ID");
        });
    }
    
    @Test
    @DisplayName("Should throw exception for zero reference amount")
    void testZeroReferenceAmountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ingredient("Name", "Description", appleNutrition, 0.0, gramsUnit, "Category", "ID");
        });
    }
    
    @ParameterizedTest
    @ValueSource(doubles = {50.0, 100.0, 150.0, 200.0})
    @DisplayName("Should calculate nutrition for different amounts")
    void testCalculateNutritionForAmount(double amount) {
        NutritionalInfo result = appleIngredient.calculateNutritionForAmount(amount, gramsUnit);
        
        double factor = amount / 100.0; // Apple nutrition is per 100g
        assertEquals(52.0 * factor, result.getCalories(), 0.001);
        assertEquals(0.3 * factor, result.getProtein(), 0.001);
        assertEquals(13.8 * factor, result.getCarbohydrates(), 0.001);
        assertEquals(0.2 * factor, result.getFat(), 0.001);
    }
    
    @Test
    @DisplayName("Should calculate nutrition with unit conversion")
    void testCalculateNutritionWithUnitConversion() {
        // Calculate nutrition for 1 oz of apple (should convert to grams first)
        NutritionalInfo result = appleIngredient.calculateNutritionForAmount(1.0, ouncesUnit);
        
        double gramsAmount = 28.3495; // 1 oz in grams
        double factor = gramsAmount / 100.0;
        assertEquals(52.0 * factor, result.getCalories(), 0.001);
        assertEquals(0.3 * factor, result.getProtein(), 0.001);
    }
    
    @Test
    @DisplayName("Should throw exception when calculating with incompatible unit")
    void testCalculateNutritionIncompatibleUnitThrowsException() {
        MeasurementUnit cupsUnit = new MeasurementUnit("cup", "cups", 
            MeasurementUnit.UnitType.VOLUME, 236.588);
        
        assertThrows(IllegalArgumentException.class, () -> {
            appleIngredient.calculateNutritionForAmount(1.0, cupsUnit);
        });
    }
    
    @ParameterizedTest
    @CsvSource({
        "100.0, 52.0",
        "200.0, 104.0", 
        "50.0, 26.0",
        "25.0, 13.0"
    })
    @DisplayName("Should calculate calories correctly for different amounts")
    void testCalculateCaloriesForAmount(double amount, double expectedCalories) {
        double calories = appleIngredient.calculateCaloriesForAmount(amount, gramsUnit);
        assertEquals(expectedCalories, calories, 0.001);
    }
    
    @Test
    @DisplayName("Should get macronutrient breakdown")
    void testGetMacronutrientBreakdown() {
        Map<String, Double> breakdown = chickenBreastIngredient.getMacronutrientBreakdown(100.0, gramsUnit);
        
        assertNotNull(breakdown);
        assertEquals(31.0, breakdown.get("protein"), 0.001);
        assertEquals(0.0, breakdown.get("carbohydrates"), 0.001);
        assertEquals(3.6, breakdown.get("fat"), 0.001);
    }
    
    @Test
    @DisplayName("Should calculate macronutrient percentages")
    void testGetMacronutrientPercentages() {
        Map<String, Double> percentages = chickenBreastIngredient.getMacronutrientPercentages(100.0, gramsUnit);
        
        // Chicken: 31g protein = 124 cal, 3.6g fat = 32.4 cal, 0g carbs = 0 cal
        // Total: 156.4 cal from macros (vs 165 total - some rounding in nutrition data)
        assertTrue(percentages.get("protein") > 70.0); // Should be around 75%
        assertTrue(percentages.get("fat") > 15.0); // Should be around 20%
        assertEquals(0.0, percentages.get("carbohydrates"), 0.001);
    }
    
    @Test
    @DisplayName("Should validate nutritional completeness")
    void testNutritionalCompleteness() {
        assertTrue(appleIngredient.hasCompleteNutritionalInfo());
        
        // Test with incomplete nutrition
        NutritionalInfo incompleteNutrition = new NutritionalInfo.Builder()
            .calories(100.0)
            .build();
        Ingredient incompleteIngredient = new Ingredient("Test", "Test", 
            incompleteNutrition, 100.0, gramsUnit, "Test", "TEST");
        
        assertFalse(incompleteIngredient.hasCompleteNutritionalInfo());
    }
    
    @Test
    @DisplayName("Should format serving information")
    void testFormatServingInfo() {
        String servingInfo = appleIngredient.formatServingInfo();
        assertTrue(servingInfo.contains("100.0"));
        assertTrue(servingInfo.contains("g"));
        assertTrue(servingInfo.contains("52"));
    }
    
    @Test
    @DisplayName("Should get alternative serving sizes")
    void testGetAlternativeServingSizes() {
        Map<String, Double> servingSizes = appleIngredient.getCommonServingSizes();
        
        assertNotNull(servingSizes);
        assertFalse(servingSizes.isEmpty());
        // Common apple serving sizes might include medium apple, cup sliced, etc.
    }
    
    @Test
    @DisplayName("Should check if ingredient is suitable for diet")
    void testIsSuitableForDiet() {
        assertTrue(appleIngredient.isSuitableForDiet("vegan"));
        assertTrue(appleIngredient.isSuitableForDiet("vegetarian"));
        assertTrue(appleIngredient.isSuitableForDiet("gluten-free"));
        
        assertFalse(chickenBreastIngredient.isSuitableForDiet("vegan"));
        assertFalse(chickenBreastIngredient.isSuitableForDiet("vegetarian"));
        assertTrue(chickenBreastIngredient.isSuitableForDiet("keto"));
    }
    
    @Test
    @DisplayName("Should get allergen information")
    void testGetAllergenInfo() {
        var allergens = appleIngredient.getAllergens();
        assertNotNull(allergens);
        assertTrue(allergens.isEmpty()); // Apples typically have no common allergens
        
        // Test ingredient with allergens
        Ingredient nutIngredient = new Ingredient("Almonds", "Raw almonds", 
            appleNutrition, 100.0, gramsUnit, "Nuts", "TEST");
        nutIngredient.addAllergen("tree nuts");
        
        var nutAllergens = nutIngredient.getAllergens();
        assertTrue(nutAllergens.contains("tree nuts"));
    }
    
    @Test
    @DisplayName("Should compare ingredients by nutrition density")
    void testCompareByNutritionDensity() {
        // Compare protein density (protein per calorie)
        double appleProteinDensity = appleIngredient.getProteinDensity();
        double chickenProteinDensity = chickenBreastIngredient.getProteinDensity();
        
        assertTrue(chickenProteinDensity > appleProteinDensity);
        
        // Compare calorie density (calories per gram)
        double appleCalorieDensity = appleIngredient.getCalorieDensity();
        double oilCalorieDensity = oliveOilIngredient.getCalorieDensity();
        
        assertTrue(oilCalorieDensity > appleCalorieDensity);
    }
    
    @Test
    @DisplayName("Should implement equals and hashCode correctly")
    void testEqualsAndHashCode() {
        Ingredient anotherApple = new Ingredient("Apple", "Fresh apple", appleNutrition, 
            100.0, gramsUnit, "Fruits", "USDA:09003");
        Ingredient differentIngredient = new Ingredient("Orange", "Fresh orange", appleNutrition, 
            100.0, gramsUnit, "Fruits", "USDA:09004");
        
        assertEquals(appleIngredient, anotherApple);
        assertNotEquals(appleIngredient, differentIngredient);
        assertEquals(appleIngredient.hashCode(), anotherApple.hashCode());
    }
    
    @Test
    @DisplayName("Should implement toString correctly")
    void testToString() {
        String result = appleIngredient.toString();
        assertTrue(result.contains("Apple"));
        assertTrue(result.contains("52.0"));
        assertTrue(result.contains("calories"));
    }
    
    @Test
    @DisplayName("Should search ingredients by nutritional criteria")
    void testSearchByNutritionalCriteria() {
        assertTrue(appleIngredient.meetsNutritionalCriteria("low-fat", 5.0)); // < 5g fat per 100g
        assertFalse(oliveOilIngredient.meetsNutritionalCriteria("low-fat", 5.0));
        
        assertTrue(chickenBreastIngredient.meetsNutritionalCriteria("high-protein", 20.0)); // > 20g protein per 100g
        assertFalse(appleIngredient.meetsNutritionalCriteria("high-protein", 20.0));
    }
    
    @Test
    @DisplayName("Should handle ingredient scaling for recipes")
    void testIngredientScaling() {
        Ingredient scaledIngredient = appleIngredient.scaleForRecipe(2.5);
        
        // Nutritional values should remain per reference amount, but tracking amount changes
        assertEquals(appleIngredient.getNutritionalInfo().getCalories(), 
                    scaledIngredient.getNutritionalInfo().getCalories(), 0.001);
        // The scaling factor should be stored for recipe calculations
        assertEquals(2.5, scaledIngredient.getRecipeScaleFactor(), 0.001);
    }
    
    @Test
    @DisplayName("Should validate measurement compatibility")
    void testMeasurementCompatibility() {
        assertTrue(appleIngredient.isCompatibleWithUnit(gramsUnit));
        assertTrue(appleIngredient.isCompatibleWithUnit(ouncesUnit));
        
        MeasurementUnit volumeUnit = new MeasurementUnit("cup", "cups", 
            MeasurementUnit.UnitType.VOLUME, 236.588);
        assertFalse(appleIngredient.isCompatibleWithUnit(volumeUnit));
    }
}
