package com.nutrition.calculator.domain.model

/** Basic ingredient domain model (simplified). */
data class Ingredient(
	val id: String,
	val name: String,
	val caloriesPer100g: Double = 0.0,
	val proteinPer100g: Double = 0.0,
	val carbsPer100g: Double = 0.0,
	val fatPer100g: Double = 0.0,
	val fiberPer100g: Double = 0.0,
	val sodiumPer100g: Double = 0.0,
	val tags: List<String> = emptyList(),
	val allergens: List<String> = emptyList(),
	val isVegan: Boolean = false,
	val isVegetarian: Boolean = false,
	val isGlutenFree: Boolean = false,
	val isDairyFree: Boolean = false,
	val isOrganic: Boolean = false
) {
	fun isValid(): Boolean = name.isNotBlank()
	fun hasTag(tag: String?) = tag != null && tags.any { it.equals(tag, true) }
	fun containsAllergen(allergen: String?) = allergen != null && allergens.any { it.equals(allergen, true) }
}
