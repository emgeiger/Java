package com.nutrition.calculator.ui.recipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nutrition.calculator.ui.theme.NutritionCalculatorTheme
import dagger.hilt.android.AndroidEntryPoint

/** Placeholder recipe details screen. */
@AndroidEntryPoint
class RecipeDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recipeName = intent.getStringExtra("recipe_name") ?: "Recipe"
        setContent {
            NutritionCalculatorTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    RecipeDetailPlaceholder(recipeName)
                }
            }
        }
    }
}

@Composable
private fun RecipeDetailPlaceholder(name: String) {
    Column(Modifier.fillMaxSize()) {
        Text(text = name, style = MaterialTheme.typography.headlineSmall)
        Text("Detailed recipe view coming soon.")
    }
}
