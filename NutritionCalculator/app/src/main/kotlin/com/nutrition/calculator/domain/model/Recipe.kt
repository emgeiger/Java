package com.nutrition.calculator.domain.model

data class Recipe(
	val id: String,
	val name: String,
	val description: String? = null,
	val servings: Int = 1,
	val ingredients: List<RecipeIngredient> = emptyList()
) {
	val totalCalories: Double get() = ingredients.sumOf { it.getCalories() }
	val caloriesPerServing: Double get() = if (servings > 0) totalCalories / servings else totalCalories
}
