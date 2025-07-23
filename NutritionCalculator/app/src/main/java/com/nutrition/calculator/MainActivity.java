package com.nutrition.calculator;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.activity.compose.setContentActivity;
import androidx.compose.foundation.layout.fillMaxSize;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.Surface;
import androidx.compose.ui.Modifier;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * MainActivity - Main entry point for the NutritionCalculator Android app
 * 
 * This activity serves as the host for the Jetpack Compose UI and integrates
 * with the nutrition tracking system using modern Android architecture patterns.
 * 
 * Features:
 * - Jetpack Compose UI
 * - Dependency Injection with Hilt
 * - Material 3 Design System
 * - Navigation support for multi-screen nutrition tracking
 * 
 * @author NutritionCalculator Development Team
 * @version 1.0
 */
@AndroidEntryPoint
public class MainActivity extends ComponentActivity {
    
    /**
     * Called when the activity is first created.
     * Sets up the Compose UI and initializes the nutrition tracking interface.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Set up Jetpack Compose content
        setContentActivity(content -> {
            // Apply Material 3 theme
            MaterialTheme.INSTANCE.invoke(
                content.invoke(
                    Surface.INSTANCE.invoke(
                        Modifier.INSTANCE.fillMaxSize(),
                        MaterialTheme.INSTANCE.getColorScheme().getBackground(),
                        content.invoke(
                            // Main nutrition app content will be implemented here
                            // This connects to the Kotlin Compose UI components
                            NutritionAppContent.INSTANCE.invoke()
                        )
                    )
                )
            );
        });
    }
    
    /**
     * Called when the activity is starting.
     * Perfect place for initializing nutrition data sync and user preferences.
     */
    @Override
    protected void onStart() {
        super.onStart();
        // Initialize nutrition data synchronization
        initializeNutritionSync();
    }
    
    /**
     * Called when the activity is resuming.
     * Update nutrition dashboard and refresh food logging interface.
     */
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh nutrition dashboard data
        refreshNutritionDashboard();
    }
    
    /**
     * Called when the activity is pausing.
     * Save any pending nutrition entries and sync to cloud.
     */
    @Override
    protected void onPause() {
        super.onPause();
        // Save pending nutrition entries
        savePendingNutritionEntries();
    }
    
    /**
     * Initialize nutrition data synchronization with Supabase backend.
     * This method sets up background sync for offline nutrition tracking.
     */
    private void initializeNutritionSync() {
        // Implementation will connect to NutritionRepository
        // and set up WorkManager for background sync
    }
    
    /**
     * Refresh the nutrition dashboard with latest data.
     * Updates daily nutrition summary, recent meals, and progress tracking.
     */
    private void refreshNutritionDashboard() {
        // Implementation will refresh ViewModels
        // and update Compose UI state
    }
    
    /**
     * Save any pending nutrition entries before activity pauses.
     * Ensures no nutrition data is lost during app transitions.
     */
    private void savePendingNutritionEntries() {
        // Implementation will save to local Room database
        // and schedule sync to Supabase
    }
    
    /**
     * Handle back button press for proper navigation flow.
     * Implements custom back navigation for nutrition tracking screens.
     */
    @Override
    public void onBackPressed() {
        // Custom back navigation logic for nutrition screens
        // Fall back to default behavior if no custom handling needed
        super.onBackPressed();
    }
}
