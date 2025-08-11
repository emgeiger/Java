package com.nutrition.calculator.domain.model

data class EnhancedIngredient(
	val ingredient: Ingredient,
	val nutritionFacts: NutritionFacts,
	val usageCount: Int = 0,
	val isFavorite: Boolean = false
)
