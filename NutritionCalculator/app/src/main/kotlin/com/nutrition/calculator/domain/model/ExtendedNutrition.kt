package com.nutrition.calculator.domain.model

data class ExtendedNutrition(
	val base: NutritionFacts,
	val sugarsG: Double = 0.0,
	val saturatedFatG: Double = 0.0,
	val cholesterolMg: Double = 0.0,
	val potassiumMg: Double = 0.0,
	val calciumMg: Double = 0.0,
	val ironMg: Double = 0.0
)
