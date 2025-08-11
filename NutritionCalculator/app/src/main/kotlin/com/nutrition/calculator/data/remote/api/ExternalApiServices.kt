package com.nutrition.calculator.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * External API definitions for nutrition lookups (placeholder implementation).
 * Replace endpoints with your real provider (e.g., USDA, Edamam, Spoonacular).
 */
interface ExternalApiServices {

	@GET("foods/search")
	suspend fun searchFoods(
		@Query("q") query: String,
		@Query("limit") limit: Int = 25
	): FoodSearchResponse

	@GET("foods/{id}")
	suspend fun getFood(@Path("id") id: String): FoodDetailResponse
}

data class FoodSearchResponse(
	val results: List<ExternalFood> = emptyList(),
	val total: Int = results.size
)

data class FoodDetailResponse(
	val food: ExternalFood?
)

data class ExternalFood(
	val id: String,
	val name: String,
	val brand: String? = null,
	val servingSize: Double? = null,
	val servingUnit: String? = null,
	val calories: Double? = null,
	val protein: Double? = null,
	val carbs: Double? = null,
	val fat: Double? = null,
	val fiber: Double? = null,
	val sodium: Double? = null,
	val imageUrl: String? = null
)
