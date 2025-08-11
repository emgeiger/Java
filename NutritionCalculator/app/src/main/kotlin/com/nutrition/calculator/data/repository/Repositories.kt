package com.nutrition.calculator.data.repository

import com.nutrition.calculator.data.local.dao.*
import com.nutrition.calculator.data.remote.api.NutritionApiService
import com.nutrition.calculator.data.local.entity.*
import com.nutrition.calculator.domain.model.*
import com.nutrition.calculator.domain.repository.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing ingredient data from local and remote sources
 */
@Singleton
class IngredientRepositoryImpl @Inject constructor(
    private val ingredientDao: IngredientDao,
    private val nutritionApiService: NutritionApiService
) : IngredientRepository {

    override fun getAllIngredients(): Flow<List<Ingredient>> {
        return ingredientDao.getAllIngredients()
            .map { entities -> entities.map { it.toDomainModel() } }
    }

    override fun getIngredientById(id: String): Flow<Ingredient?> {
        return ingredientDao.getIngredientById(id)
            .map { it?.toDomainModel() }
    }

    override fun searchIngredients(query: String): Flow<List<Ingredient>> {
        return ingredientDao.searchIngredients(query)
            .map { entities -> entities.map { it.toDomainModel() } }
    }

    override fun getIngredientsByCategory(category: String): Flow<List<Ingredient>> {
        return ingredientDao.getIngredientsByCategory(category)
            .map { entities -> entities.map { it.toDomainModel() } }
    }

    override suspend fun insertIngredient(ingredient: Ingredient): Result<Unit> {
        return try {
            ingredientDao.insertIngredient(ingredient.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateIngredient(ingredient: Ingredient): Result<Unit> {
        return try {
            ingredientDao.updateIngredient(ingredient.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteIngredient(id: String): Result<Unit> {
        return try {
            ingredientDao.deleteIngredient(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchRemoteIngredients(query: String): Result<List<Ingredient>> {
        return try {
            val response = nutritionApiService.searchIngredients(query)
            if (response.isSuccessful) {
                val ingredients = response.body()?.results?.map { it.toDomainModel() } ?: emptyList()
                Result.success(ingredients)
            } else {
                Result.failure(Exception("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun syncIngredients(): Result<Unit> {
        return try {
            val localIngredients = ingredientDao.getAllIngredientsSync()
            val remoteIngredients = nutritionApiService.getAllIngredients()
            
            if (remoteIngredients.isSuccessful) {
                val ingredientsToSync = remoteIngredients.body()?.results ?: emptyList()
                ingredientDao.insertIngredients(ingredientsToSync.map { it.toEntity() })
                Result.success(Unit)
            } else {
                Result.failure(Exception("Sync failed: ${remoteIngredients.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

/**
 * Repository for managing measurement units
 */
@Singleton
class MeasurementUnitRepositoryImpl @Inject constructor(
    private val measurementUnitDao: MeasurementUnitDao
) : MeasurementUnitRepository {

    override fun getAllUnits(): Flow<List<MeasurementUnit>> {
        return measurementUnitDao.getAllUnits()
            .map { entities -> entities.map { it.toDomainModel() } }
    }

    override fun getUnitsByType(type: MeasurementUnit.UnitType): Flow<List<MeasurementUnit>> {
        return measurementUnitDao.getUnitsByType(type.name)
            .map { entities -> entities.map { it.toDomainModel() } }
    }

    override fun getUnitBySymbol(symbol: String): Flow<MeasurementUnit?> {
        return measurementUnitDao.getUnitBySymbol(symbol)
            .map { it?.toDomainModel() }
    }

    override suspend fun insertUnit(unit: MeasurementUnit): Result<Unit> {
        return try {
            measurementUnitDao.insertUnit(unit.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteUnit(symbol: String): Result<Unit> {
        return try {
            measurementUnitDao.deleteUnit(symbol)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun initializeDefaultUnits(): Result<Unit> {
        return try {
            val defaultUnits = getDefaultMeasurementUnits()
            measurementUnitDao.insertUnits(defaultUnits.map { it.toEntity() })
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun getDefaultMeasurementUnits(): List<MeasurementUnit> {
        return listOf(
            // Weight units
            MeasurementUnit("g", "grams", MeasurementUnit.UnitType.WEIGHT, 1.0),
            MeasurementUnit("kg", "kilograms", MeasurementUnit.UnitType.WEIGHT, 1000.0),
            MeasurementUnit("oz", "ounces", MeasurementUnit.UnitType.WEIGHT, 28.3495),
            MeasurementUnit("lb", "pounds", MeasurementUnit.UnitType.WEIGHT, 453.592),
            
            // Volume units
            MeasurementUnit("ml", "milliliters", MeasurementUnit.UnitType.VOLUME, 1.0),
            MeasurementUnit("l", "liters", MeasurementUnit.UnitType.VOLUME, 1000.0),
            MeasurementUnit("cup", "cups", MeasurementUnit.UnitType.VOLUME, 236.588),
            MeasurementUnit("tbsp", "tablespoons", MeasurementUnit.UnitType.VOLUME, 14.7868),
            MeasurementUnit("tsp", "teaspoons", MeasurementUnit.UnitType.VOLUME, 4.92892),
            MeasurementUnit("fl oz", "fluid ounces", MeasurementUnit.UnitType.VOLUME, 29.5735),
            
            // Count units
            MeasurementUnit("pcs", "pieces", MeasurementUnit.UnitType.COUNT, 1.0),
            MeasurementUnit("serving", "servings", MeasurementUnit.UnitType.COUNT, 1.0)
        )
    }
}

/**
 * Repository for managing recipes
 */
@Singleton
class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao,
    private val recipeIngredientDao: RecipeIngredientDao
) : RecipeRepository {

    override fun getAllRecipes(): Flow<List<Recipe>> {
        return recipeDao.getAllRecipes()
            .map { entities -> entities.map { it.toDomainModel() } }
    }

    override fun getRecipeById(id: String): Flow<Recipe?> {
        return recipeDao.getRecipeById(id)
            .map { it?.toDomainModel() }
    }

    override fun getRecipesByCategory(category: String): Flow<List<Recipe>> {
        return recipeDao.getRecipesByCategory(category)
            .map { entities -> entities.map { it.toDomainModel() } }
    }

    override fun searchRecipes(query: String): Flow<List<Recipe>> {
        return recipeDao.searchRecipes(query)
            .map { entities -> entities.map { it.toDomainModel() } }
    }

    override fun getFavoriteRecipes(): Flow<List<Recipe>> {
        return recipeDao.getFavoriteRecipes()
            .map { entities -> entities.map { it.toDomainModel() } }
    }

    override suspend fun insertRecipe(recipe: Recipe): Result<String> {
        return try {
            val recipeId = recipeDao.insertRecipe(recipe.toEntity())
            
            // Insert recipe ingredients
            recipe.ingredients.forEach { recipeIngredient ->
                recipeIngredientDao.insertRecipeIngredient(
                    recipeIngredient.toEntity(recipeId.toString())
                )
            }
            
            Result.success(recipeId.toString())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateRecipe(recipe: Recipe): Result<Unit> {
        return try {
            recipeDao.updateRecipe(recipe.toEntity())
            
            // Update recipe ingredients
            recipeIngredientDao.deleteByRecipeId(recipe.id)
            recipe.ingredients.forEach { recipeIngredient ->
                recipeIngredientDao.insertRecipeIngredient(
                    recipeIngredient.toEntity(recipe.id)
                )
            }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteRecipe(id: String): Result<Unit> {
        return try {
            recipeIngredientDao.deleteByRecipeId(id)
            recipeDao.deleteRecipe(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun toggleFavorite(id: String): Result<Unit> {
        return try {
            recipeDao.toggleFavorite(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getRecipeIngredients(recipeId: String): Flow<List<RecipeIngredient>> {
        return recipeIngredientDao.getRecipeIngredients(recipeId)
            .map { entities -> entities.map { it.toDomainModel() } }
    }
}

/**
 * Repository for managing nutrition tracking and meal planning
 */
@Singleton
class NutritionTrackingRepositoryImpl @Inject constructor(
    private val mealEntryDao: MealEntryDao,
    private val dailyNutritionDao: DailyNutritionDao,
    private val nutritionGoalDao: NutritionGoalDao
) : NutritionTrackingRepository {

    override fun getMealEntriesForDate(date: String): Flow<List<MealEntry>> {
        return mealEntryDao.getMealEntriesForDate(date)
            .map { entities -> entities.map { it.toDomainModel() } }
    }

    override fun getMealEntriesForDateRange(startDate: String, endDate: String): Flow<List<MealEntry>> {
        return mealEntryDao.getMealEntriesForDateRange(startDate, endDate)
            .map { entities -> entities.map { it.toDomainModel() } }
    }

    override fun getDailyNutrition(date: String): Flow<DailyNutrition?> {
        return dailyNutritionDao.getDailyNutrition(date)
            .map { it?.toDomainModel() }
    }

    override fun getNutritionGoals(): Flow<NutritionGoals?> {
        return nutritionGoalDao.getCurrentGoals()
            .map { it?.toDomainModel() }
    }

    override suspend fun addMealEntry(mealEntry: MealEntry): Result<Unit> {
        return try {
            mealEntryDao.insertMealEntry(mealEntry.toEntity())
            updateDailyNutrition(mealEntry.date)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateMealEntry(mealEntry: MealEntry): Result<Unit> {
        return try {
            mealEntryDao.updateMealEntry(mealEntry.toEntity())
            updateDailyNutrition(mealEntry.date)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteMealEntry(id: String): Result<Unit> {
        return try {
            val mealEntry = mealEntryDao.getMealEntryByIdSync(id)
            mealEntryDao.deleteMealEntry(id)
            mealEntry?.date?.let { updateDailyNutrition(it) }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateNutritionGoals(goals: NutritionGoals): Result<Unit> {
        return try {
            nutritionGoalDao.insertOrUpdateGoals(goals.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun calculateDailyProgress(date: String): Result<NutritionProgress> {
        return try {
            val mealEntries = mealEntryDao.getMealEntriesForDateSync(date)
            val goals = nutritionGoalDao.getCurrentGoalsSync()
            
            if (goals == null) {
                return Result.failure(Exception("No nutrition goals set"))
            }
            
            val totalNutrition = calculateTotalNutrition(mealEntries.map { it.toDomainModel() })
            val progress = NutritionProgress(
                date = date,
                consumedCalories = totalNutrition.calories,
                targetCalories = goals.dailyCalories,
                consumedProtein = totalNutrition.protein,
                targetProtein = goals.dailyProtein,
                consumedCarbs = totalNutrition.carbohydrates,
                targetCarbs = goals.dailyCarbohydrates,
                consumedFat = totalNutrition.fat,
                targetFat = goals.dailyFat
            )
            
            Result.success(progress)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun updateDailyNutrition(date: String) {
        try {
            val mealEntries = mealEntryDao.getMealEntriesForDateSync(date)
            val totalNutrition = calculateTotalNutrition(mealEntries.map { it.toDomainModel() })
            
            val dailyNutrition = DailyNutrition(
                date = date,
                totalCalories = totalNutrition.calories,
                totalProtein = totalNutrition.protein,
                totalCarbohydrates = totalNutrition.carbohydrates,
                totalFat = totalNutrition.fat,
                totalFiber = totalNutrition.fiber,
                totalSugar = totalNutrition.sugar,
                totalSodium = totalNutrition.sodium
            )
            
            dailyNutritionDao.insertOrUpdateDailyNutrition(dailyNutrition.toEntity())
        } catch (e: Exception) {
            // Log error but don't throw to avoid breaking meal entry operations
        }
    }

    private fun calculateTotalNutrition(mealEntries: List<MealEntry>): NutritionalInfo {
        return mealEntries.fold(
            NutritionalInfo.Builder().build()
        ) { acc, entry ->
            NutritionalInfo.Builder()
                .calories(acc.calories + entry.nutritionalInfo.calories)
                .protein(acc.protein + entry.nutritionalInfo.protein)
                .carbohydrates(acc.carbohydrates + entry.nutritionalInfo.carbohydrates)
                .fat(acc.fat + entry.nutritionalInfo.fat)
                .fiber(acc.fiber + entry.nutritionalInfo.fiber)
                .sugar(acc.sugar + entry.nutritionalInfo.sugar)
                .sodium(acc.sodium + entry.nutritionalInfo.sodium)
                .build()
        }
    }
}

/**
 * Repository for managing user preferences and settings
 */
@Singleton
class UserPreferencesRepositoryImpl @Inject constructor(
    private val userPreferencesDao: UserPreferencesDao
) : UserPreferencesRepository {

    override fun getUserPreferences(): Flow<UserPreferences?> {
        return userPreferencesDao.getUserPreferences()
            .map { it?.toDomainModel() }
    }

    override suspend fun updatePreferences(preferences: UserPreferences): Result<Unit> {
        return try {
            userPreferencesDao.insertOrUpdatePreferences(preferences.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun resetToDefaults(): Result<Unit> {
        return try {
            val defaultPreferences = UserPreferences.getDefaults()
            userPreferencesDao.insertOrUpdatePreferences(defaultPreferences.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
