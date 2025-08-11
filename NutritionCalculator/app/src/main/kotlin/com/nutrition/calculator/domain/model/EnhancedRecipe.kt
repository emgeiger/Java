package com.nutrition.calculator.domain.model

/** Wrapper adding computed fields to Recipe. */
data class EnhancedRecipe(
	val recipe: Recipe,
	val nutritionFacts: NutritionFacts,
	val averageRating: Double? = null,
	val ratingCount: Int = 0,
	val tags: List<String> = emptyList()
)
