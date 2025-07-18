package com.nutrition.calculator

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NutritionApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize any global configurations here
        initializeNetworking()
        initializeLogging()
    }
    
    private fun initializeNetworking() {
        // Network configuration for nutrition API access
        // SSL configuration for corporate networks handled in NetworkModule
    }
    
    private fun initializeLogging() {
        // Initialize crash reporting and analytics if needed
    }
}
