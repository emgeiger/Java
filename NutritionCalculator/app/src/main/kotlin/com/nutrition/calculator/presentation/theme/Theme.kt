package com.nutrition.calculator.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Nutrition Calculator Color Palette
 * 
 * Colors inspired by healthy foods and nutrition themes:
 * - Greens: vegetables, health, growth
 * - Oranges: fruits, energy, vitamins
 * - Blues: water, trust, stability
 * - Earth tones: grains, proteins, natural foods
 */
private val NutritionGreen = Color(0xFF4CAF50)
private val NutritionGreenVariant = Color(0xFF388E3C)
private val NutritionLightGreen = Color(0xFF8BC34A)
private val NutritionDarkGreen = Color(0xFF2E7D32)

private val NutritionOrange = Color(0xFFFF9800)
private val NutritionOrangeVariant = Color(0xFFF57C00)
private val NutritionLightOrange = Color(0xFFFFB74D)
private val NutritionDarkOrange = Color(0xFFE65100)

private val NutritionBlue = Color(0xFF2196F3)
private val NutritionBlueVariant = Color(0xFF1976D2)
private val NutritionLightBlue = Color(0xFF64B5F6)
private val NutritionDarkBlue = Color(0xFF0D47A1)

private val NutritionRed = Color(0xFFF44336)
private val NutritionYellow = Color(0xFFFFEB3B)
private val NutritionPurple = Color(0xFF9C27B0)

// Neutral colors for backgrounds and surfaces
private val NutritionGray50 = Color(0xFFFAFAFA)
private val NutritionGray100 = Color(0xFFF5F5F5)
private val NutritionGray200 = Color(0xFFEEEEEE)
private val NutritionGray300 = Color(0xFFE0E0E0)
private val NutritionGray400 = Color(0xFFBDBDBD)
private val NutritionGray500 = Color(0xFF9E9E9E)
private val NutritionGray600 = Color(0xFF757575)
private val NutritionGray700 = Color(0xFF616161)
private val NutritionGray800 = Color(0xFF424242)
private val NutritionGray900 = Color(0xFF212121)

/**
 * Light theme color scheme for Nutrition Calculator
 */
private val NutritionLightColorScheme = lightColorScheme(
    // Primary colors - main brand colors
    primary = NutritionGreen,
    onPrimary = Color.White,
    primaryContainer = NutritionLightGreen,
    onPrimaryContainer = NutritionDarkGreen,
    
    // Secondary colors - accent and supporting elements
    secondary = NutritionOrange,
    onSecondary = Color.White,
    secondaryContainer = NutritionLightOrange,
    onSecondaryContainer = NutritionDarkOrange,
    
    // Tertiary colors - additional accent
    tertiary = NutritionBlue,
    onTertiary = Color.White,
    tertiaryContainer = NutritionLightBlue,
    onTertiaryContainer = NutritionDarkBlue,
    
    // Error colors
    error = NutritionRed,
    onError = Color.White,
    errorContainer = Color(0xFFFFEBEE),
    onErrorContainer = Color(0xFFB71C1C),
    
    // Background and surface colors
    background = Color.White,
    onBackground = NutritionGray900,
    surface = Color.White,
    onSurface = NutritionGray900,
    surfaceVariant = NutritionGray100,
    onSurfaceVariant = NutritionGray700,
    
    // Outline colors
    outline = NutritionGray400,
    outlineVariant = NutritionGray200,
    
    // Container colors
    surfaceContainer = NutritionGray50,
    surfaceContainerHigh = NutritionGray100,
    surfaceContainerHighest = NutritionGray200,
    surfaceContainerLow = NutritionGray50,
    surfaceContainerLowest = Color.White,
    
    // Inverse colors
    inverseSurface = NutritionGray800,
    inverseOnSurface = NutritionGray100,
    inversePrimary = NutritionLightGreen,
    
    // Scrim
    scrim = Color.Black.copy(alpha = 0.32f)
)

/**
 * Dark theme color scheme for Nutrition Calculator
 */
private val NutritionDarkColorScheme = darkColorScheme(
    // Primary colors
    primary = NutritionLightGreen,
    onPrimary = NutritionDarkGreen,
    primaryContainer = NutritionGreenVariant,
    onPrimaryContainer = NutritionLightGreen,
    
    // Secondary colors
    secondary = NutritionLightOrange,
    onSecondary = NutritionDarkOrange,
    secondaryContainer = NutritionOrangeVariant,
    onSecondaryContainer = NutritionLightOrange,
    
    // Tertiary colors
    tertiary = NutritionLightBlue,
    onTertiary = NutritionDarkBlue,
    tertiaryContainer = NutritionBlueVariant,
    onTertiaryContainer = NutritionLightBlue,
    
    // Error colors
    error = Color(0xFFEF5350),
    onError = Color(0xFFB71C1C),
    errorContainer = Color(0xFFD32F2F),
    onErrorContainer = Color(0xFFFFEBEE),
    
    // Background and surface colors
    background = Color(0xFF121212),
    onBackground = Color(0xFFE0E0E0),
    surface = Color(0xFF121212),
    onSurface = Color(0xFFE0E0E0),
    surfaceVariant = NutritionGray800,
    onSurfaceVariant = NutritionGray300,
    
    // Outline colors
    outline = NutritionGray600,
    outlineVariant = NutritionGray700,
    
    // Container colors
    surfaceContainer = Color(0xFF1E1E1E),
    surfaceContainerHigh = Color(0xFF2A2A2A),
    surfaceContainerHighest = Color(0xFF363636),
    surfaceContainerLow = Color(0xFF1A1A1A),
    surfaceContainerLowest = Color(0xFF0F0F0F),
    
    // Inverse colors
    inverseSurface = NutritionGray100,
    inverseOnSurface = NutritionGray800,
    inversePrimary = NutritionGreen,
    
    // Scrim
    scrim = Color.Black.copy(alpha = 0.5f)
)

/**
 * Custom colors for nutrition-specific UI elements
 */
object NutritionColors {
    // Macronutrient colors
    val protein = Color(0xFFE91E63) // Pink for protein
    val carbohydrates = Color(0xFF2196F3) // Blue for carbs
    val fat = Color(0xFFFF9800) // Orange for fats
    val fiber = Color(0xFF4CAF50) // Green for fiber
    val sugar = Color(0xFFFFEB3B) // Yellow for sugar
    val sodium = Color(0xFF9C27B0) // Purple for sodium
    
    // Vitamin and mineral colors
    val vitaminA = Color(0xFFFF5722) // Red-orange
    val vitaminC = Color(0xFFFFEB3B) // Yellow
    val vitaminD = Color(0xFFFF9800) // Orange
    val vitaminE = Color(0xFF4CAF50) // Green
    val vitaminK = Color(0xFF8BC34A) // Light green
    val calcium = Color(0xFF2196F3) // Blue
    val iron = Color(0xFF795548) // Brown
    val potassium = Color(0xFF9C27B0) // Purple
    
    // Status colors
    val excellent = Color(0xFF4CAF50) // Green
    val good = Color(0xFF8BC34A) // Light green
    val fair = Color(0xFFFFEB3B) // Yellow
    val poor = Color(0xFFFF9800) // Orange
    val deficient = Color(0xFFF44336) // Red
    
    // Chart colors
    val chartColors = listOf(
        protein, carbohydrates, fat, fiber, sugar, sodium,
        vitaminA, vitaminC, vitaminD, calcium, iron, potassium
    )
}

/**
 * Main theme composable for Nutrition Calculator
 * 
 * @param darkTheme Whether to use dark theme
 * @param dynamicColor Whether to use Android 12+ dynamic colors
 * @param content The content to be themed
 */
@Composable
fun NutritionCalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> NutritionDarkColorScheme
        else -> NutritionLightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = NutritionTypography,
        content = content
    )
}
