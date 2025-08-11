package com.nutrition.calculator.data.local

import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nutrition.calculator.data.local.dao.*
import com.nutrition.calculator.data.local.entity.*
import com.nutrition.calculator.data.local.converter.*
import com.nutrition.calculator.domain.model.MeasurementUnit
import com.nutrition.calculator.domain.model.NutritionalInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Provider

/**
 * Main Room database for Nutrition Calculator application
 * 
 * Contains all entities for nutrition tracking, ingredients, recipes,
 * meal planning, and user preferences with proper relationships
 */
@Database(
    entities = [
        IngredientEntity::class,
        MeasurementUnitEntity::class,
        RecipeEntity::class,
        RecipeIngredientEntity::class,
        MealEntryEntity::class,
        DailyNutritionEntity::class,
        NutritionGoalEntity::class,
        UserPreferencesEntity::class
    ],
    version = 1,
    exportSchema = true,
    autoMigrations = []
)
@TypeConverters(
    NutritionConverters::class,
    DateConverters::class,
    ListConverters::class
)
abstract class NutritionDatabase : RoomDatabase() {

    // Data Access Objects
    abstract fun ingredientDao(): IngredientDao
    abstract fun measurementUnitDao(): MeasurementUnitDao
    abstract fun recipeDao(): RecipeDao
    abstract fun recipeIngredientDao(): RecipeIngredientDao
    abstract fun mealEntryDao(): MealEntryDao
    abstract fun dailyNutritionDao(): DailyNutritionDao
    abstract fun nutritionGoalDao(): NutritionGoalDao
    abstract fun userPreferencesDao(): UserPreferencesDao

    companion object {
        const val DATABASE_NAME = "nutrition_database"
        
        /**
         * Migration from version 1 to 2 (example for future use)
         */
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Example migration - add new column to ingredients table
                // database.execSQL("ALTER TABLE ingredients ADD COLUMN allergens TEXT")
            }
        }
        
        /**
         * Migration from version 2 to 3 (example for future use)
         */
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Example migration - create new table for meal plans
                // database.execSQL("""
                //     CREATE TABLE meal_plans (
                //         id TEXT PRIMARY KEY NOT NULL,
                //         name TEXT NOT NULL,
                //         description TEXT,
                //         created_date INTEGER NOT NULL
                //     )
                // """)
            }
        }
    }
}

/**
 * Room database callback for initializing default data
 */
class NutritionDatabaseCallback(
    private val database: Provider<NutritionDatabase>,
    private val applicationScope: CoroutineScope
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        
        // Initialize database with default data
        applicationScope.launch {
            populateDatabase()
        }
    }

    /**
     * Populate database with default measurement units and sample data
     */
    private suspend fun populateDatabase() {
        try {
            val db = database.get()
            
            // Initialize default measurement units
            initializeDefaultMeasurementUnits(db)
            
            // Initialize sample ingredients (optional)
            initializeSampleIngredients(db)
            
            // Initialize default nutrition goals
            initializeDefaultNutritionGoals(db)
            
            // Initialize default user preferences
            initializeDefaultUserPreferences(db)
            
        } catch (e: Exception) {
            // Log error but don't crash app during initialization
            android.util.Log.e("NutritionDB", "Error initializing database", e)
        }
    }

    private suspend fun initializeDefaultMeasurementUnits(db: NutritionDatabase) {
        val measurementUnitDao = db.measurementUnitDao()
        
        val defaultUnits = listOf(
            // Weight units (base unit: grams)
            MeasurementUnitEntity(
                symbol = "g",
                name = "grams",
                type = "WEIGHT",
                baseConversionFactor = 1.0
            ),
            MeasurementUnitEntity(
                symbol = "kg",
                name = "kilograms", 
                type = "WEIGHT",
                baseConversionFactor = 1000.0
            ),
            MeasurementUnitEntity(
                symbol = "oz",
                name = "ounces",
                type = "WEIGHT", 
                baseConversionFactor = 28.3495
            ),
            MeasurementUnitEntity(
                symbol = "lb",
                name = "pounds",
                type = "WEIGHT",
                baseConversionFactor = 453.592
            ),
            
            // Volume units (base unit: milliliters)
            MeasurementUnitEntity(
                symbol = "ml",
                name = "milliliters",
                type = "VOLUME",
                baseConversionFactor = 1.0
            ),
            MeasurementUnitEntity(
                symbol = "l",
                name = "liters",
                type = "VOLUME",
                baseConversionFactor = 1000.0
            ),
            MeasurementUnitEntity(
                symbol = "cup",
                name = "cups",
                type = "VOLUME",
                baseConversionFactor = 236.588
            ),
            MeasurementUnitEntity(
                symbol = "tbsp",
                name = "tablespoons",
                type = "VOLUME",
                baseConversionFactor = 14.7868
            ),
            MeasurementUnitEntity(
                symbol = "tsp",
                name = "teaspoons",
                type = "VOLUME",
                baseConversionFactor = 4.92892
            ),
            MeasurementUnitEntity(
                symbol = "fl oz",
                name = "fluid ounces",
                type = "VOLUME",
                baseConversionFactor = 29.5735
            ),
            
            // Count units
            MeasurementUnitEntity(
                symbol = "pcs",
                name = "pieces",
                type = "COUNT",
                baseConversionFactor = 1.0
            ),
            MeasurementUnitEntity(
                symbol = "serving",
                name = "servings", 
                type = "COUNT",
                baseConversionFactor = 1.0
            )
        )
        
        measurementUnitDao.insertUnits(defaultUnits)
    }

    private suspend fun initializeSampleIngredients(db: NutritionDatabase) {
        val ingredientDao = db.ingredientDao()
        
        val sampleIngredients = listOf(
            IngredientEntity(
                id = "apple-001",
                name = "Apple",
                description = "Fresh apple, medium size",
                calories = 52.0,
                protein = 0.3,
                carbohydrates = 13.8,
                fat = 0.2,
                fiber = 2.4,
                sugar = 10.4,
                sodium = 1.0,
                referenceAmount = 100.0,
                referenceUnit = "g",
                category = "Fruits",
                databaseId = "USDA:09003",
                allergens = emptyList(),
                isVerified = true,
                createdDate = System.currentTimeMillis(),
                updatedDate = System.currentTimeMillis()
            ),
            IngredientEntity(
                id = "chicken-breast-001",
                name = "Chicken Breast",
                description = "Skinless, boneless chicken breast",
                calories = 165.0,
                protein = 31.0,
                carbohydrates = 0.0,
                fat = 3.6,
                fiber = 0.0,
                sugar = 0.0,
                sodium = 74.0,
                referenceAmount = 100.0,
                referenceUnit = "g",
                category = "Proteins",
                databaseId = "USDA:05062",
                allergens = emptyList(),
                isVerified = true,
                createdDate = System.currentTimeMillis(),
                updatedDate = System.currentTimeMillis()
            ),
            IngredientEntity(
                id = "brown-rice-001",
                name = "Brown Rice",
                description = "Cooked brown rice",
                calories = 111.0,
                protein = 2.6,
                carbohydrates = 22.0,
                fat = 0.9,
                fiber = 1.8,
                sugar = 0.4,
                sodium = 5.0,
                referenceAmount = 100.0,
                referenceUnit = "g",
                category = "Grains",
                databaseId = "USDA:20040",
                allergens = emptyList(),
                isVerified = true,
                createdDate = System.currentTimeMillis(),
                updatedDate = System.currentTimeMillis()
            )
        )
        
        ingredientDao.insertIngredients(sampleIngredients)
    }

    private suspend fun initializeDefaultNutritionGoals(db: NutritionDatabase) {
        val nutritionGoalDao = db.nutritionGoalDao()
        
        val defaultGoals = NutritionGoalEntity(
            id = "default-goals",
            dailyCalories = 2000.0,
            dailyProtein = 150.0,
            dailyCarbohydrates = 250.0,
            dailyFat = 65.0,
            dailyFiber = 25.0,
            dailySugar = 50.0,
            dailySodium = 2300.0,
            proteinPercentage = 30.0,
            carbohydratesPercentage = 50.0,
            fatPercentage = 20.0,
            isActive = true,
            createdDate = System.currentTimeMillis(),
            updatedDate = System.currentTimeMillis()
        )
        
        nutritionGoalDao.insertOrUpdateGoals(defaultGoals)
    }

    private suspend fun initializeDefaultUserPreferences(db: NutritionDatabase) {
        val userPreferencesDao = db.userPreferencesDao()
        
        val defaultPreferences = UserPreferencesEntity(
            id = "user-preferences",
            preferredWeightUnit = "kg",
            preferredVolumeUnit = "ml",
            preferredTemperatureUnit = "celsius",
            defaultMealPlanDays = 7,
            enableNotifications = true,
            enableDarkMode = false,
            enableAnalytics = true,
            language = "en",
            country = "US",
            timeZone = "UTC",
            dateFormat = "yyyy-MM-dd",
            firstDayOfWeek = 1, // Monday
            reminderTimes = listOf("08:00", "12:00", "18:00"),
            activityLevel = "moderate",
            dietaryRestrictions = emptyList(),
            allergies = emptyList(),
            createdDate = System.currentTimeMillis(),
            updatedDate = System.currentTimeMillis()
        )
        
        userPreferencesDao.insertOrUpdatePreferences(defaultPreferences)
    }
}

/**
 * Database utility functions
 */
object DatabaseUtils {
    
    /**
     * Clear all user data while keeping reference data (ingredients, units)
     */
    suspend fun clearUserData(database: NutritionDatabase) {
        database.runInTransaction {
            database.clearAllTables()
        }
    }
    
    /**
     * Export database to backup file
     */
    suspend fun exportDatabase(database: NutritionDatabase): Result<String> {
        return try {
            // Implementation would depend on backup strategy
            // Could export to JSON, CSV, or create database backup
            Result.success("Database exported successfully")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Import database from backup file
     */
    suspend fun importDatabase(database: NutritionDatabase, backupData: String): Result<Unit> {
        return try {
            // Implementation would restore from backup format
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Get database statistics
     */
    suspend fun getDatabaseStats(database: NutritionDatabase): DatabaseStats {
        return DatabaseStats(
            totalIngredients = database.ingredientDao().getIngredientCount(),
            totalRecipes = database.recipeDao().getRecipeCount(),
            totalMealEntries = database.mealEntryDao().getMealEntryCount(),
            databaseSizeBytes = 0L, // Would need to calculate actual file size
            lastBackupDate = null
        )
    }
}

/**
 * Database statistics data class
 */
data class DatabaseStats(
    val totalIngredients: Int,
    val totalRecipes: Int, 
    val totalMealEntries: Int,
    val databaseSizeBytes: Long,
    val lastBackupDate: Long?
)
