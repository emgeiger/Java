package com.nutrition.calculator.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MeasurementUnit Model Tests")
public class MeasurementUnitTest {
    
    private MeasurementUnit gramsUnit;
    private MeasurementUnit ouncesUnit;
    private MeasurementUnit cupsUnit;
    private MeasurementUnit tablespoonsUnit;
    
    @BeforeEach
    void setUp() {
        gramsUnit = new MeasurementUnit("g", "grams", MeasurementUnit.UnitType.WEIGHT, 1.0);
        ouncesUnit = new MeasurementUnit("oz", "ounces", MeasurementUnit.UnitType.WEIGHT, 28.3495);
        cupsUnit = new MeasurementUnit("cup", "cups", MeasurementUnit.UnitType.VOLUME, 236.588);
        tablespoonsUnit = new MeasurementUnit("tbsp", "tablespoons", MeasurementUnit.UnitType.VOLUME, 14.7868);
    }
    
    @Test
    @DisplayName("Should create measurement unit with valid parameters")
    void testMeasurementUnitCreation() {
        assertNotNull(gramsUnit);
        assertEquals("g", gramsUnit.getSymbol());
        assertEquals("grams", gramsUnit.getName());
        assertEquals(MeasurementUnit.UnitType.WEIGHT, gramsUnit.getType());
        assertEquals(1.0, gramsUnit.getBaseConversionFactor(), 0.001);
    }
    
    @Test
    @DisplayName("Should throw exception for null symbol")
    void testNullSymbolThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new MeasurementUnit(null, "grams", MeasurementUnit.UnitType.WEIGHT, 1.0);
        });
    }
    
    @Test
    @DisplayName("Should throw exception for empty symbol")
    void testEmptySymbolThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new MeasurementUnit("", "grams", MeasurementUnit.UnitType.WEIGHT, 1.0);
        });
    }
    
    @Test
    @DisplayName("Should throw exception for negative conversion factor")
    void testNegativeConversionFactorThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new MeasurementUnit("g", "grams", MeasurementUnit.UnitType.WEIGHT, -1.0);
        });
    }
    
    @Test
    @DisplayName("Should throw exception for zero conversion factor")
    void testZeroConversionFactorThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new MeasurementUnit("g", "grams", MeasurementUnit.UnitType.WEIGHT, 0.0);
        });
    }
    
    @ParameterizedTest
    @ValueSource(doubles = {1.0, 28.3495, 100.0, 0.5, 1000.0})
    @DisplayName("Should convert to base unit correctly")
    void testConvertToBaseUnit(double amount) {
        double expectedGrams = amount;
        double expectedFromOunces = amount * 28.3495;
        
        assertEquals(expectedGrams, gramsUnit.convertToBaseUnit(amount), 0.001);
        assertEquals(expectedFromOunces, ouncesUnit.convertToBaseUnit(amount), 0.001);
    }
    
    @ParameterizedTest
    @ValueSource(doubles = {28.3495, 56.699, 100.0, 14.1748})
    @DisplayName("Should convert from base unit correctly")
    void testConvertFromBaseUnit(double baseAmount) {
        double expectedGrams = baseAmount;
        double expectedOunces = baseAmount / 28.3495;
        
        assertEquals(expectedGrams, gramsUnit.convertFromBaseUnit(baseAmount), 0.001);
        assertEquals(expectedOunces, ouncesUnit.convertFromBaseUnit(baseAmount), 0.001);
    }
    
    @ParameterizedTest
    @CsvSource({
        "1.0, 28.3495",
        "2.0, 56.699",
        "0.5, 14.1748",
        "10.0, 283.495"
    })
    @DisplayName("Should convert between different units of same type")
    void testConvertBetweenUnits(double ouncesAmount, double expectedGrams) {
        double result = MeasurementUnit.convert(ouncesAmount, ouncesUnit, gramsUnit);
        assertEquals(expectedGrams, result, 0.001);
    }
    
    @Test
    @DisplayName("Should throw exception when converting between different unit types")
    void testConvertBetweenDifferentTypesThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            MeasurementUnit.convert(1.0, gramsUnit, cupsUnit);
        });
    }
    
    @Test
    @DisplayName("Should check if units are compatible")
    void testUnitsCompatibility() {
        assertTrue(gramsUnit.isCompatibleWith(ouncesUnit));
        assertTrue(cupsUnit.isCompatibleWith(tablespoonsUnit));
        assertFalse(gramsUnit.isCompatibleWith(cupsUnit));
        assertFalse(ouncesUnit.isCompatibleWith(tablespoonsUnit));
    }
    
    @Test
    @DisplayName("Should format display string correctly")
    void testDisplayString() {
        assertEquals("1.0 g", gramsUnit.formatAmount(1.0));
        assertEquals("2.5 oz", ouncesUnit.formatAmount(2.5));
        assertEquals("0.5 cup", cupsUnit.formatAmount(0.5));
    }
    
    @Test
    @DisplayName("Should handle plural forms correctly")
    void testPluralForms() {
        assertEquals("1 gram", gramsUnit.formatAmountWithName(1.0));
        assertEquals("2 grams", gramsUnit.formatAmountWithName(2.0));
        assertEquals("1 cup", cupsUnit.formatAmountWithName(1.0));
        assertEquals("2 cups", cupsUnit.formatAmountWithName(2.0));
    }
    
    @Test
    @DisplayName("Should implement equals and hashCode correctly")
    void testEqualsAndHashCode() {
        MeasurementUnit anotherGramsUnit = new MeasurementUnit("g", "grams", MeasurementUnit.UnitType.WEIGHT, 1.0);
        MeasurementUnit differentUnit = new MeasurementUnit("kg", "kilograms", MeasurementUnit.UnitType.WEIGHT, 1000.0);
        
        assertEquals(gramsUnit, anotherGramsUnit);
        assertNotEquals(gramsUnit, differentUnit);
        assertEquals(gramsUnit.hashCode(), anotherGramsUnit.hashCode());
        assertNotEquals(gramsUnit.hashCode(), differentUnit.hashCode());
    }
    
    @Test
    @DisplayName("Should implement toString correctly")
    void testToString() {
        String expected = "MeasurementUnit{symbol='g', name='grams', type=WEIGHT, baseConversionFactor=1.0}";
        assertEquals(expected, gramsUnit.toString());
    }
    
    @Test
    @DisplayName("Should get all standard weight units")
    void testGetStandardWeightUnits() {
        var weightUnits = MeasurementUnit.getStandardWeightUnits();
        assertFalse(weightUnits.isEmpty());
        assertTrue(weightUnits.stream().allMatch(unit -> unit.getType() == MeasurementUnit.UnitType.WEIGHT));
    }
    
    @Test
    @DisplayName("Should get all standard volume units")
    void testGetStandardVolumeUnits() {
        var volumeUnits = MeasurementUnit.getStandardVolumeUnits();
        assertFalse(volumeUnits.isEmpty());
        assertTrue(volumeUnits.stream().allMatch(unit -> unit.getType() == MeasurementUnit.UnitType.VOLUME));
    }
    
    @Test
    @DisplayName("Should validate measurement amounts")
    void testValidateMeasurementAmount() {
        assertTrue(MeasurementUnit.isValidAmount(1.0));
        assertTrue(MeasurementUnit.isValidAmount(0.0));
        assertFalse(MeasurementUnit.isValidAmount(-1.0));
        assertFalse(MeasurementUnit.isValidAmount(Double.NaN));
        assertFalse(MeasurementUnit.isValidAmount(Double.POSITIVE_INFINITY));
    }
    
    @Test
    @DisplayName("Should round conversion results appropriately")
    void testConversionRounding() {
        double result = MeasurementUnit.convert(1.0, ouncesUnit, gramsUnit);
        // Should round to reasonable decimal places for nutrition calculations
        assertEquals(28.35, Math.round(result * 100.0) / 100.0, 0.01);
    }
    
    @Test
    @DisplayName("Should handle edge case conversions")
    void testEdgeCaseConversions() {
        // Test very small amounts
        double smallAmount = 0.001;
        double result = MeasurementUnit.convert(smallAmount, ouncesUnit, gramsUnit);
        assertTrue(result > 0);
        
        // Test large amounts
        double largeAmount = 1000.0;
        result = MeasurementUnit.convert(largeAmount, gramsUnit, ouncesUnit);
        assertTrue(result > 0);
    }
}
