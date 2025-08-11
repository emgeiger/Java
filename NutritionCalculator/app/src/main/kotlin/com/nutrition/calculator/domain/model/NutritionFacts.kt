package com.nutrition.calculator.domain.model

data class NutritionFacts(
	val calories: Double = 0.0,
	val proteinG: Double = 0.0,
	val carbohydratesG: Double = 0.0,
	val fatG: Double = 0.0,
	val fiberG: Double = 0.0,
	val sodiumMg: Double = 0.0
) {
	val proteinCalories get() = proteinG * 4
	val carbCalories get() = carbohydratesG * 4
	val fatCalories get() = fatG * 9
	val macroDistribution: Map<String, Double> get() {
		val total = proteinCalories + carbCalories + fatCalories
		if (total <= 0) return emptyMap()
		return mapOf(
			"protein" to proteinCalories / total,
			"carbs" to carbCalories / total,
			"fat" to fatCalories / total
		)
	}
}
