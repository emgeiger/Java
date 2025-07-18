package com.nutrition.calculator.data.repository

import com.nutrition.calculator.data.network.*
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for nutrition-related database operations
 * 
 * This repository implements CRUD operations for all nutrition entities
 * using Supabase as the backend. It provides a clean API layer between
 * the UI and the database.
 */
@Singleton
class NutritionRepository @Inject constructor(
    private val supabaseClient: SupabaseClient
) {
    
    // ===============================================
    // USER PROFILE OPERATIONS
    // ===============================================
    
    suspend fun getUserProfile(userId: String): SupabaseResult<UserProfile?> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("user_profiles")
                .select()
                .eq("id", userId)
                .decodeSingleOrNull<UserProfile>()
        }
    }
    
    suspend fun createUserProfile(profile: UserProfile): SupabaseResult<UserProfile> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("user_profiles")
                .insert(profile)
                .decodeSingle<UserProfile>()
        }
    }
    
    suspend fun updateUserProfile(profile: UserProfile): SupabaseResult<UserProfile> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("user_profiles")
                .update(profile)
                .eq("id", profile.id)
                .decodeSingle<UserProfile>()
        }
    }
    
    // ===============================================
    // FOOD OPERATIONS
    // ===============================================
    
    suspend fun searchFoods(
        query: String,
        limit: Int = 20,
        offset: Int = 0
    ): SupabaseResult<List<FoodSearchResult>> {
        return supabaseClient.safeCall {
            val foods = supabaseClient.database
                .from("foods")
                .select(Columns.raw("""
                    *,
                    food_categories!inner(id, name, color_hex),
                    food_brands(name)
                """))
                .textSearch("search_vector", query)
                .eq("verified", true)
                .order("name")
                .limit(limit.toLong())
                .range(offset.toLong(), (offset + limit - 1).toLong())
                .decodeList<Food>()
            
            // Transform to search results (simplified - in real implementation, you'd handle the joins properly)
            foods.map { food ->
                FoodSearchResult(
                    food = food,
                    category = null, // Would be populated from the join
                    brand = null     // Would be populated from the join
                )
            }
        }
    }
    
    suspend fun getFoodById(foodId: String): SupabaseResult<Food?> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("foods")
                .select()
                .eq("id", foodId)
                .decodeSingleOrNull<Food>()
        }
    }
    
    suspend fun getFoodByBarcode(barcode: String): SupabaseResult<Food?> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("foods")
                .select()
                .eq("barcode", barcode)
                .eq("verified", true)
                .decodeSingleOrNull<Food>()
        }
    }
    
    suspend fun getFoodsByCategory(
        categoryId: String,
        limit: Int = 50
    ): SupabaseResult<List<Food>> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("foods")
                .select()
                .eq("category_id", categoryId)
                .eq("verified", true)
                .order("name")
                .limit(limit.toLong())
                .decodeList<Food>()
        }
    }
    
    suspend fun createFood(food: Food): SupabaseResult<Food> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("foods")
                .insert(food)
                .decodeSingle<Food>()
        }
    }
    
    suspend fun updateFood(food: Food): SupabaseResult<Food> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("foods")
                .update(food)
                .eq("id", food.id)
                .decodeSingle<Food>()
        }
    }
    
    // ===============================================
    // FOOD CATEGORY OPERATIONS
    // ===============================================
    
    suspend fun getAllFoodCategories(): SupabaseResult<List<FoodCategory>> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("food_categories")
                .select()
                .order("name")
                .decodeList<FoodCategory>()
        }
    }
    
    suspend fun getMainFoodCategories(): SupabaseResult<List<FoodCategory>> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("food_categories")
                .select()
                .isNull("parent_category_id")
                .order("name")
                .decodeList<FoodCategory>()
        }
    }
    
    suspend fun getSubCategories(parentId: String): SupabaseResult<List<FoodCategory>> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("food_categories")
                .select()
                .eq("parent_category_id", parentId)
                .order("name")
                .decodeList<FoodCategory>()
        }
    }
    
    // ===============================================
    // RECIPE OPERATIONS
    // ===============================================
    
    suspend fun searchRecipes(
        query: String,
        limit: Int = 20,
        offset: Int = 0,
        isPublic: Boolean = true
    ): SupabaseResult<List<RecipeSearchResult>> {
        return supabaseClient.safeCall {
            val recipes = supabaseClient.database
                .from("recipes")
                .select(Columns.raw("""
                    *,
                    recipe_categories(name),
                    recipe_ingredients(id)
                """))
                .textSearch("search_vector", query)
                .eq("is_public", isPublic)
                .order("rating", ascending = false)
                .order("name")
                .limit(limit.toLong())
                .range(offset.toLong(), (offset + limit - 1).toLong())
                .decodeList<Recipe>()
            
            // Transform to search results
            recipes.map { recipe ->
                RecipeSearchResult(
                    recipe = recipe,
                    category = null, // Would be populated from join
                    totalIngredients = 0, // Would be counted from join
                    averageRating = recipe.rating
                )
            }
        }
    }
    
    suspend fun getRecipeById(recipeId: String): SupabaseResult<Recipe?> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("recipes")
                .select()
                .eq("id", recipeId)
                .decodeSingleOrNull<Recipe>()
        }
    }
    
    suspend fun getRecipeIngredients(recipeId: String): SupabaseResult<List<RecipeIngredient>> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("recipe_ingredients")
                .select()
                .eq("recipe_id", recipeId)
                .order("sort_order")
                .decodeList<RecipeIngredient>()
        }
    }
    
    suspend fun getUserRecipes(userId: String): SupabaseResult<List<Recipe>> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("recipes")
                .select()
                .eq("created_by", userId)
                .order("created_at", ascending = false)
                .decodeList<Recipe>()
        }
    }
    
    suspend fun createRecipe(recipe: Recipe): SupabaseResult<Recipe> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("recipes")
                .insert(recipe)
                .decodeSingle<Recipe>()
        }
    }
    
    suspend fun updateRecipe(recipe: Recipe): SupabaseResult<Recipe> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("recipes")
                .update(recipe)
                .eq("id", recipe.id)
                .decodeSingle<Recipe>()
        }
    }
    
    suspend fun deleteRecipe(recipeId: String): SupabaseResult<Unit> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("recipes")
                .delete()
                .eq("id", recipeId)
        }
    }
    
    // ===============================================
    // FOOD LOG OPERATIONS
    // ===============================================
    
    suspend fun logFood(foodLog: FoodLog): SupabaseResult<FoodLog> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("food_logs")
                .insert(foodLog)
                .decodeSingle<FoodLog>()
        }
    }
    
    suspend fun updateFoodLog(foodLog: FoodLog): SupabaseResult<FoodLog> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("food_logs")
                .update(foodLog)
                .eq("id", foodLog.id)
                .decodeSingle<FoodLog>()
        }
    }
    
    suspend fun deleteFoodLog(logId: String): SupabaseResult<Unit> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("food_logs")
                .delete()
                .eq("id", logId)
        }
    }
    
    suspend fun getUserFoodLogs(
        userId: String,
        startDate: String,
        endDate: String
    ): SupabaseResult<List<FoodLog>> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("food_logs")
                .select()
                .eq("user_id", userId)
                .gte("consumed_at", startDate)
                .lte("consumed_at", endDate)
                .order("consumed_at", ascending = false)
                .decodeList<FoodLog>()
        }
    }
    
    suspend fun getFoodLogsByMealType(
        userId: String,
        date: String,
        mealType: String
    ): SupabaseResult<List<FoodLog>> {
        return supabaseClient.safeCall {
            val startOfDay = "${date}T00:00:00Z"
            val endOfDay = "${date}T23:59:59Z"
            
            supabaseClient.database
                .from("food_logs")
                .select()
                .eq("user_id", userId)
                .eq("meal_type", mealType)
                .gte("consumed_at", startOfDay)
                .lte("consumed_at", endOfDay)
                .order("consumed_at")
                .decodeList<FoodLog>()
        }
    }
    
    // ===============================================
    // DAILY NUTRITION SUMMARY OPERATIONS
    // ===============================================
    
    suspend fun getDailyNutritionSummary(
        userId: String,
        date: String
    ): SupabaseResult<DailyNutritionSummary?> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("daily_nutrition_summaries")
                .select()
                .eq("user_id", userId)
                .eq("date", date)
                .decodeSingleOrNull<DailyNutritionSummary>()
        }
    }
    
    suspend fun upsertDailyNutritionSummary(
        summary: DailyNutritionSummary
    ): SupabaseResult<DailyNutritionSummary> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("daily_nutrition_summaries")
                .upsert(summary)
                .decodeSingle<DailyNutritionSummary>()
        }
    }
    
    suspend fun getNutritionSummariesForRange(
        userId: String,
        startDate: String,
        endDate: String
    ): SupabaseResult<List<DailyNutritionSummary>> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("daily_nutrition_summaries")
                .select()
                .eq("user_id", userId)
                .gte("date", startDate)
                .lte("date", endDate)
                .order("date", ascending = false)
                .decodeList<DailyNutritionSummary>()
        }
    }
    
    // ===============================================
    // BULK OPERATIONS
    // ===============================================
    
    suspend fun bulkLogFoods(foodLogs: List<FoodLog>): SupabaseResult<List<FoodLog>> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("food_logs")
                .insert(foodLogs)
                .decodeList<FoodLog>()
        }
    }
    
    suspend fun bulkCreateRecipeIngredients(
        ingredients: List<RecipeIngredient>
    ): SupabaseResult<List<RecipeIngredient>> {
        return supabaseClient.safeCall {
            supabaseClient.database
                .from("recipe_ingredients")
                .insert(ingredients)
                .decodeList<RecipeIngredient>()
        }
    }
    
    // ===============================================
    // UTILITY FUNCTIONS
    // ===============================================
    
    suspend fun calculateRecipeNutrition(recipeId: String): SupabaseResult<NutritionInfo> {
        return supabaseClient.safeCall {
            // This would typically be done with a database view or stored procedure
            // For now, we'll calculate it manually
            val ingredients = getRecipeIngredients(recipeId)
            val recipe = getRecipeById(recipeId)
            
            when {
                ingredients is SupabaseResult.Success && recipe is SupabaseResult.Success -> {
                    var totalCalories = 0.0
                    var totalProtein = 0.0
                    var totalCarbs = 0.0
                    var totalFat = 0.0
                    var totalFiber = 0.0
                    var totalSodium = 0.0
                    
                    for (ingredient in ingredients.data) {
                        val food = getFoodById(ingredient.foodId)
                        if (food is SupabaseResult.Success && food.data != null) {
                            val nutrition = NutritionInfo.fromFood(
                                food.data,
                                ingredient.quantity,
                                ingredient.unit
                            )
                            totalCalories += nutrition.calories
                            totalProtein += nutrition.proteinG
                            totalCarbs += nutrition.carbohydratesG
                            totalFat += nutrition.fatG
                            totalFiber += nutrition.fiberG
                            totalSodium += nutrition.sodiumMg
                        }
                    }
                    
                    // Divide by servings to get per-serving nutrition
                    val servings = recipe.data?.servings ?: 1
                    NutritionInfo(
                        calories = totalCalories / servings,
                        proteinG = totalProtein / servings,
                        carbohydratesG = totalCarbs / servings,
                        fatG = totalFat / servings,
                        fiberG = totalFiber / servings,
                        sodiumMg = totalSodium / servings
                    )
                }
                else -> throw Exception("Failed to calculate recipe nutrition")
            }
        }
    }
    
    /**
     * Real-time subscriptions for data changes
     */
    fun subscribeFoodLogs(userId: String): Flow<List<FoodLog>> = flow {
        // Implementation would use Supabase real-time subscriptions
        // This is a simplified version
        val logs = getUserFoodLogs(
            userId,
            startDate = java.time.LocalDate.now().toString(),
            endDate = java.time.LocalDate.now().toString()
        )
        
        if (logs is SupabaseResult.Success) {
            emit(logs.data)
        }
    }
    
    fun subscribeDailySummary(userId: String, date: String): Flow<DailyNutritionSummary?> = flow {
        val summary = getDailyNutritionSummary(userId, date)
        if (summary is SupabaseResult.Success) {
            emit(summary.data)
        }
    }
}
