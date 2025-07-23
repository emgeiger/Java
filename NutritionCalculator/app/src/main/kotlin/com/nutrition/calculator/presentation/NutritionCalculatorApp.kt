package com.nutrition.calculator.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nutrition.calculator.R
import com.nutrition.calculator.presentation.theme.NutritionCalculatorTheme

/**
 * Main Compose application for the Nutrition Calculator.
 * 
 * This composable function serves as the root of the navigation graph
 * and provides the main structure for the nutrition tracking app.
 * 
 * Features:
 * - Bottom navigation with nutrition tracking screens
 * - Material 3 design system
 * - Navigation between food logging, recipes, and analytics
 * - Responsive layout for different screen sizes
 * 
 * @author NutritionCalculator Development Team
 * @version 1.0
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionCalculatorApp() {
    NutritionCalculatorTheme {
        val navController = rememberNavController()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStackEntry?.destination
        
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.app_name),
                            style = MaterialTheme.typography.headlineSmall
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            },
            bottomBar = {
                NavigationBar {
                    // Dashboard screen
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Dashboard, contentDescription = null) },
                        label = { Text("Dashboard") },
                        selected = currentDestination?.route == "dashboard",
                        onClick = { 
                            navController.navigate("dashboard") {
                                popUpTo("dashboard") { inclusive = true }
                            }
                        }
                    )
                    
                    // Food Logging screen
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Restaurant, contentDescription = null) },
                        label = { Text("Food Log") },
                        selected = currentDestination?.route == "food_log",
                        onClick = { 
                            navController.navigate("food_log") {
                                popUpTo("dashboard")
                            }
                        }
                    )
                    
                    // Recipes screen
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.MenuBook, contentDescription = null) },
                        label = { Text("Recipes") },
                        selected = currentDestination?.route == "recipes",
                        onClick = { 
                            navController.navigate("recipes") {
                                popUpTo("dashboard")
                            }
                        }
                    )
                    
                    // Analytics screen
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Analytics, contentDescription = null) },
                        label = { Text("Analytics") },
                        selected = currentDestination?.route == "analytics",
                        onClick = { 
                            navController.navigate("analytics") {
                                popUpTo("dashboard")
                            }
                        }
                    )
                    
                    // Profile screen
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Person, contentDescription = null) },
                        label = { Text("Profile") },
                        selected = currentDestination?.route == "profile",
                        onClick = { 
                            navController.navigate("profile") {
                                popUpTo("dashboard")
                            }
                        }
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "dashboard",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("dashboard") {
                    NutritionDashboardScreen()
                }
                
                composable("food_log") {
                    FoodLoggingScreen()
                }
                
                composable("recipes") {
                    RecipesScreen()
                }
                
                composable("analytics") {
                    NutritionAnalyticsScreen()
                }
                
                composable("profile") {
                    ProfileScreen()
                }
            }
        }
    }
}

/**
 * Nutrition Dashboard Screen - Main overview of daily nutrition tracking
 */
@Composable
fun NutritionDashboardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Daily nutrition summary card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Today's Nutrition",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Nutrition progress indicators
                NutritionProgressRow("Calories", 1,850, 2,000)
                NutritionProgressRow("Protein", 75, 120)
                NutritionProgressRow("Carbs", 230, 250)
                NutritionProgressRow("Fat", 65, 80)
            }
        }
        
        // Recent meals section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Recent Meals",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "Your recent meals will appear here...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        // Quick actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ElevatedButton(
                onClick = { /* Navigate to food logging */ },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Log Food")
            }
            
            ElevatedButton(
                onClick = { /* Navigate to recipes */ },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Filled.MenuBook, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("View Recipes")
            }
        }
    }
}

/**
 * Nutrition progress row component showing current vs target values
 */
@Composable
fun NutritionProgressRow(
    nutrient: String,
    current: Int,
    target: Int
) {
    val progress = (current.toFloat() / target.toFloat()).coerceAtMost(1f)
    
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = nutrient,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "$current / $target",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth(),
            color = when {
                progress < 0.5f -> MaterialTheme.colorScheme.error
                progress < 0.8f -> MaterialTheme.colorScheme.tertiary
                else -> MaterialTheme.colorScheme.primary
            }
        )
        
        Spacer(modifier = Modifier.height(8.dp))
    }
}

/**
 * Food Logging Screen - Interface for logging meals and foods
 */
@Composable
fun FoodLoggingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                Icons.Filled.Restaurant,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Food Logging",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Food logging interface will be implemented here",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Recipes Screen - Browse and manage nutrition recipes
 */
@Composable
fun RecipesScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                Icons.Filled.MenuBook,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Recipes",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Recipe management interface will be implemented here",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Analytics Screen - Nutrition trends and insights
 */
@Composable
fun NutritionAnalyticsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                Icons.Filled.Analytics,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Nutrition Analytics",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Analytics and charts will be implemented here",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Profile Screen - User settings and preferences
 */
@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                Icons.Filled.Person,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "User profile and settings will be implemented here",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NutritionCalculatorAppPreview() {
    NutritionCalculatorApp()
}
