package com.nutrition.calculator.domain.model

data class RecipeIngredient(
	val id: String,
	val recipeId: String,
	val ingredient: Ingredient,
	val quantityGrams: Double,
	val unit: String = "g",
	val notes: String? = null,
	val optional: Boolean = false,
	val sortOrder: Int = 0
) {
	fun getCalories(): Double = ingredient.caloriesPer100g * (quantityGrams / 100.0)
	fun getProtein(): Double = ingredient.proteinPer100g * (quantityGrams / 100.0)
	fun getCarbs(): Double = ingredient.carbsPer100g * (quantityGrams / 100.0)
	fun getFat(): Double = ingredient.fatPer100g * (quantityGrams / 100.0)
	fun getFiber(): Double = ingredient.fiberPer100g * (quantityGrams / 100.0)
	fun getSodium(): Double = ingredient.sodiumPer100g * (quantityGrams / 100.0)
}
