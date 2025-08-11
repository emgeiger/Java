package com.nutrition.calculator

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.nutrition.calculator.data.database.NutritionDatabase
import com.nutrition.calculator.data.database.NutritionRepository
import com.nutrition.calculator.data.network.NutritionApiService
import com.nutrition.calculator.data.preferences.UserPreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Main Application class for Nutrition Calculator
 * 
 * Handles application-wide initialization including:
 * - Database setup and migration
 * - Network configuration with corporate proxy support
 * - Dependency injection container
 * - User preferences initialization
 * - Background task management
 */
class NutritionCalculatorApplication : Application() {

    // Application-wide coroutine scope
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Database instance - lazy initialization
    val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            NutritionDatabase::class.java,
            "nutrition_database"
        )
        .addMigrations(*NutritionDatabase.getAllMigrations())
        .fallbackToDestructiveMigration() // For development only
        .build()
    }

    // Network client with corporate network support
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("User-Agent", "NutritionCalculator/1.0")
                    .addHeader("Accept", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    // Retrofit API service
    val nutritionApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.nutritionix.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NutritionApiService::class.java)
    }

    // Repository with combined local and remote data sources
    val nutritionRepository by lazy {
        NutritionRepository(
            localDataSource = database.nutritionDao(),
            remoteDataSource = nutritionApiService,
            userPreferences = userPreferencesManager,
            applicationScope = applicationScope
        )
    }

    // User preferences manager
    val userPreferencesManager by lazy {
        UserPreferencesManager(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        
        // Initialize application components
        initializeApplication()
        
        // Setup crash reporting and analytics (if needed)
        setupCrashReporting()
        
        // Pre-populate database with default nutrition data
        initializeNutritionDatabase()
    }

    /**
     * Initialize core application components
     */
    private fun initializeApplication() {
        // Set up default user preferences if first launch
        userPreferencesManager.initializeDefaultPreferences()
        
        // Configure network security for corporate environments
        configureNetworkSecurity()
        
        // Initialize background task scheduler
        initializeBackgroundTasks()
    }

    /**
     * Configure network security settings for corporate environments
     */
    private fun configureNetworkSecurity() {
        // Configure SSL settings for corporate proxy environments
        System.setProperty("http.proxyHost", "")
        System.setProperty("http.proxyPort", "")
        System.setProperty("https.proxyHost", "")
        System.setProperty("https.proxyPort", "")
        
        // Trust all certificates for development (remove in production)
        if (BuildConfig.DEBUG) {
            // Corporate SSL bypass configuration would go here
        }
    }

    /**
     * Setup crash reporting and analytics
     */
    private fun setupCrashReporting() {
        if (!BuildConfig.DEBUG) {
            // Initialize crash reporting service (Firebase Crashlytics, etc.)
            // Initialize analytics service if needed
        }
    }

    /**
     * Initialize nutrition database with default data
     */
    private fun initializeNutritionDatabase() {
        // Pre-populate database with common foods and nutrients
        // This could be done in a background thread
        Thread {
            try {
                val dao = database.nutritionDao()
                if (dao.getFoodCount() == 0) {
                    // Insert default nutrition data
                    dao.insertDefaultNutritionData()
                }
            } catch (e: Exception) {
                // Log error but don't crash the app
                e.printStackTrace()
            }
        }.start()
    }

    /**
     * Initialize background tasks for data synchronization
     */
    private fun initializeBackgroundTasks() {
        // Setup WorkManager tasks for:
        // - Periodic nutrition data updates
        // - Data backup and sync
        // - Cache cleanup
    }

    /**
     * Get application context statically
     */
    companion object {
        @Volatile
        private var INSTANCE: NutritionCalculatorApplication? = null

        fun getInstance(context: Context): NutritionCalculatorApplication {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: (context.applicationContext as NutritionCalculatorApplication).also { INSTANCE = it }
            }
        }

        /**
         * Nutrition calculation constants and utilities
         */
        object NutritionConstants {
            // Caloric values per gram
            const val CALORIES_PER_GRAM_CARB = 4.0
            const val CALORIES_PER_GRAM_PROTEIN = 4.0
            const val CALORIES_PER_GRAM_FAT = 9.0
            const val CALORIES_PER_GRAM_ALCOHOL = 7.0

            // Daily value percentages (based on 2000 calorie diet)
            const val DAILY_VALUE_TOTAL_FAT = 65.0 // grams
            const val DAILY_VALUE_SATURATED_FAT = 20.0 // grams
            const val DAILY_VALUE_CHOLESTEROL = 300.0 // mg
            const val DAILY_VALUE_SODIUM = 2300.0 // mg
            const val DAILY_VALUE_TOTAL_CARB = 300.0 // grams
            const val DAILY_VALUE_DIETARY_FIBER = 25.0 // grams
            const val DAILY_VALUE_PROTEIN = 50.0 // grams

            // Vitamin and mineral daily values
            const val DAILY_VALUE_VITAMIN_C = 90.0 // mg
            const val DAILY_VALUE_CALCIUM = 1300.0 // mg
            const val DAILY_VALUE_IRON = 18.0 // mg
        }

        /**
         * Calculate total calories from macronutrients
         */
        fun calculateTotalCalories(
            carbs: Double,
            protein: Double,
            fat: Double,
            alcohol: Double = 0.0
        ): Double {
            return (carbs * NutritionConstants.CALORIES_PER_GRAM_CARB) +
                   (protein * NutritionConstants.CALORIES_PER_GRAM_PROTEIN) +
                   (fat * NutritionConstants.CALORIES_PER_GRAM_FAT) +
                   (alcohol * NutritionConstants.CALORIES_PER_GRAM_ALCOHOL)
        }

        /**
         * Calculate daily value percentage
         */
        fun calculateDailyValuePercentage(actualValue: Double, dailyValue: Double): Double {
            return if (dailyValue > 0) (actualValue / dailyValue) * 100.0 else 0.0
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        // Cleanup resources
        database.close()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        // Clear caches and free up memory
        nutritionRepository.clearCache()
    }
}
