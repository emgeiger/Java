package com.nutrition.calculator.ui.scanner

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint

/** Simplified placeholder for a barcode/food scanning screen. */
@AndroidEntryPoint
class FoodScannerActivity : ComponentActivity() {

    private val cameraPermission = Manifest.permission.CAMERA

    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        // In a real implementation, start camera preview when granted
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ensurePermission()
        setContent { ScannerPlaceholder() }
    }

    private fun ensurePermission() {
        if (ContextCompat.checkSelfPermission(this, cameraPermission) != PackageManager.PERMISSION_GRANTED) {
            requestPermission.launch(cameraPermission)
        }
    }
}

@Composable
private fun ScannerPlaceholder() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        Text(text = "Scanner initializing...", modifier = Modifier.align(Alignment.BottomCenter))
    }
}
