package com.nutrition.calculator.data.network

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Supabase client configuration for Nutrition Calculator
 * 
 * This class provides a centralized configuration for all Supabase services
 * including authentication, database operations, and file storage.
 */
@Singleton
class SupabaseClient @Inject constructor() {
    
    companion object {
        private const val SUPABASE_URL = BuildConfig.SUPABASE_URL
        private const val SUPABASE_ANON_KEY = BuildConfig.SUPABASE_ANON_KEY
    }
    
    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_ANON_KEY
    ) {
        install(Auth) {
            // Auth configuration
            flowType = Auth.FlowType.PKCE
            scheme = "com.nutrition.calculator"
            host = "login"
        }
        
        install(Postgrest) {
            // PostgreSQL REST API configuration
        }
        
        install(Storage) {
            // File storage configuration
        }
    }
    
    // Convenient access to services
    val auth: Auth get() = client.auth
    val database: Postgrest get() = client.postgrest
    val storage: Storage get() = client.storage
}

/**
 * Data models for Supabase integration
 */

@Serializable
data class UserProfile(
    val id: String,
    val username: String? = null,
    val email: String,
    val fullName: String? = null,
    val age: Int? = null,
    val gender: String? = null,
    val heightCm: Double? = null,
    val weightKg: Double? = null,
    val activityLevel: String? = null,
    val dietaryRestrictions: List<String> = emptyList(),
    val healthGoals: List<String> = emptyList(),
    val dailyCalorieTarget: Int? = null,
    val dailyProteinTarget: Double? = null,
    val dailyCarbTarget: Double? = null,
    val dailyFatTarget: Double? = null,
    val dailyFiberTarget: Double? = null,
    val avatarUrl: String? = null,
    val timezone: String = "UTC",
    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class Food(
    val id: String,
    val name: String,
    val brandId: String? = null,
    val categoryId: String? = null,
    val barcode: String? = null,
    val servingSize: Double,
    val servingUnit: String,
    val servingsPerContainer: Double? = null,
    val calories: Double,
    val proteinG: Double = 0.0,
    val carbohydratesG: Double = 0.0,
    val dietaryFiberG: Double = 0.0,
    val sugarsG: Double = 0.0,
    val addedSugarsG: Double = 0.0,
    val totalFatG: Double = 0.0,
    val saturatedFatG: Double = 0.0,
    val transFatG: Double = 0.0,
    val cholesterolMg: Double = 0.0,
    val sodiumMg: Double = 0.0,
    val vitaminAIu: Double = 0.0,
    val vitaminCMg: Double = 0.0,
    val vitaminDIu: Double = 0.0,
    val calciumMg: Double = 0.0,
    val ironMg: Double = 0.0,
    val potassiumMg: Double = 0.0,
    val allergens: List<String> = emptyList(),
    val ingredients: String? = null,
    val dataSource: String? = null,
    val verified: Boolean = false,
    val imageUrl: String? = null,
    val thumbnailUrl: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class FoodCategory(
    val id: String,
    val name: String,
    val description: String? = null,
    val iconUrl: String? = null,
    val colorHex: String? = null,
    val parentCategoryId: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class Recipe(
    val id: String,
    val name: String,
    val description: String? = null,
    val categoryId: String? = null,
    val prepTimeMinutes: Int? = null,
    val cookTimeMinutes: Int? = null,
    val servings: Int,
    val difficultyLevel: String? = null,
    val instructions: String? = null, // JSON string
    val caloriesPerServing: Double? = null,
    val proteinPerServing: Double? = null,
    val carbsPerServing: Double? = null,
    val fatPerServing: Double? = null,
    val fiberPerServing: Double? = null,
    val createdBy: String? = null,
    val isPublic: Boolean = true,
    val rating: Double? = null,
    val ratingCount: Int = 0,
    val imageUrl: String? = null,
    val videoUrl: String? = null,
    val tags: List<String> = emptyList(),
    val dietaryTags: List<String> = emptyList(),
    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class RecipeIngredient(
    val id: String,
    val recipeId: String,
    val foodId: String,
    val quantity: Double,
    val unit: String,
    val preparationNote: String? = null,
    val isOptional: Boolean = false,
    val sortOrder: Int = 0,
    val ingredientGroup: String? = null,
    val createdAt: String? = null
)

@Serializable
data class FoodLog(
    val id: String,
    val userId: String,
    val foodId: String? = null,
    val recipeId: String? = null,
    val quantity: Double,
    val unit: String,
    val mealType: String? = null,
    val consumedAt: String,
    val calories: Double,
    val proteinG: Double = 0.0,
    val carbohydratesG: Double = 0.0,
    val fatG: Double = 0.0,
    val fiberG: Double = 0.0,
    val sodiumMg: Double = 0.0,
    val notes: String? = null,
    val location: String? = null,
    val moodBefore: String? = null,
    val moodAfter: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class DailyNutritionSummary(
    val id: String,
    val userId: String,
    val date: String,
    val totalCalories: Double = 0.0,
    val totalProteinG: Double = 0.0,
    val totalCarbohydratesG: Double = 0.0,
    val totalFatG: Double = 0.0,
    val totalFiberG: Double = 0.0,
    val totalSodiumMg: Double = 0.0,
    val calorieGoalPercentage: Double? = null,
    val proteinGoalPercentage: Double? = null,
    val carbGoalPercentage: Double? = null,
    val fatGoalPercentage: Double? = null,
    val fiberGoalPercentage: Double? = null,
    val breakfastCalories: Double = 0.0,
    val lunchCalories: Double = 0.0,
    val dinnerCalories: Double = 0.0,
    val snackCalories: Double = 0.0,
    val waterIntakeMl: Int = 0,
    val waterGoalMl: Int = 2000,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

/**
 * Search and filter models
 */
@Serializable
data class FoodSearchResult(
    val food: Food,
    val category: FoodCategory? = null,
    val brand: String? = null
)

@Serializable
data class RecipeSearchResult(
    val recipe: Recipe,
    val category: String? = null,
    val totalIngredients: Int = 0,
    val averageRating: Double? = null
)

/**
 * Nutrition calculation helpers
 */
data class NutritionInfo(
    val calories: Double,
    val proteinG: Double,
    val carbohydratesG: Double,
    val fatG: Double,
    val fiberG: Double,
    val sodiumMg: Double
) {
    companion object {
        fun fromFood(food: Food, quantity: Double, unit: String): NutritionInfo {
            // Calculate scaling factor based on serving size
            val scalingFactor = when (unit.lowercase()) {
                "g", "gram", "grams" -> quantity / food.servingSize
                "oz", "ounce", "ounces" -> (quantity * 28.35) / food.servingSize
                "cup", "cups" -> (quantity * 240) / food.servingSize // Approximate for liquids
                "piece", "pieces", "item", "items" -> quantity // Assume 1 piece = 1 serving
                else -> quantity / food.servingSize
            }
            
            return NutritionInfo(
                calories = food.calories * scalingFactor,
                proteinG = food.proteinG * scalingFactor,
                carbohydratesG = food.carbohydratesG * scalingFactor,
                fatG = food.totalFatG * scalingFactor,
                fiberG = food.dietaryFiberG * scalingFactor,
                sodiumMg = food.sodiumMg * scalingFactor
            )
        }
    }
}

/**
 * API response wrappers
 */
sealed class SupabaseResult<T> {
    data class Success<T>(val data: T) : SupabaseResult<T>()
    data class Error<T>(val exception: Throwable) : SupabaseResult<T>()
    data class Loading<T>(val isLoading: Boolean = true) : SupabaseResult<T>()
}

/**
 * Extension functions for common operations
 */
suspend inline fun <reified T> SupabaseClient.safeCall(
    crossinline operation: suspend () -> T
): SupabaseResult<T> {
    return try {
        SupabaseResult.Success(operation())
    } catch (e: Exception) {
        SupabaseResult.Error(e)
    }
}
