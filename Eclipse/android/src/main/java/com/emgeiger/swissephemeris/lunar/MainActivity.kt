package com.emgeiger.swissephemeris.lunar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.emgeiger.swissephemeris.lunar.ui.theme.SwissEphemerisTheme
import kotlin.math.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SwissEphemerisTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LunarMonitorScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun LunarMonitorScreen(
    modifier: Modifier = Modifier,
    viewModel: LunarViewModel = viewModel()
) {
    val lunarData by viewModel.lunarData.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.updateLunarData()
    }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A0F))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        SwissEphemerisHeader()
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Moon Phase Visualization
        MoonPhaseVisualization(
            phase = lunarData.phase,
            illumination = lunarData.illumination,
            modifier = Modifier
                .size(300.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Lunar Data Panel
        LunarDataPanel(lunarData = lunarData)
    }
}

@Composable
fun SwissEphemerisHeader() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A3A)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Swiss Ephemeris Lunar Monitor",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun MoonPhaseVisualization(
    phase: Double,
    illumination: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0D0D1A)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                drawStarfield()
                drawMoonPhase(phase, illumination)
                drawPhasePercentage(phase)
            }
        }
    }
}

fun DrawScope.drawStarfield() {
    val starColor = Color.White
    repeat(50) {
        val x = (0..size.width.toInt()).random().toFloat()
        val y = (0..size.height.toInt()).random().toFloat()
        val starSize = if (kotlin.random.Random.nextFloat() < 0.7f) 2f else 4f
        
        drawCircle(
            color = starColor,
            radius = starSize,
            center = Offset(x, y)
        )
    }
}

fun DrawScope.drawMoonPhase(phase: Double, illumination: Double) {
    val center = size.center
    val radius = minOf(size.width, size.height) * 0.25f
    
    // Draw full moon (light gray)
    drawCircle(
        color = Color(0xFFE5E5D9),
        radius = radius,
        center = center
    )
    
    // Draw moon border
    drawCircle(
        color = Color(0xFFB3B399),
        radius = radius,
        center = center,
        style = Stroke(width = 4f)
    )
    
    // Draw lunar shadow based on phase
    drawLunarShadow(phase, center, radius)
    
    // Draw moon surface details (craters)
    drawMoonSurface(center, radius)
}

fun DrawScope.drawLunarShadow(phase: Double, center: Offset, radius: Float) {
    val shadowColor = Color(0x80191926) // Semi-transparent shadow
    
    if (phase <= 50.0) {
        // Waxing phase: shadow from right
        val shadowWidth = ((50.0 - phase) / 50.0 * (2 * radius)).toFloat()
        val shadowCenter = Offset(center.x + radius - shadowWidth / 2, center.y)
        
        // Draw shadow arc
        drawArc(
            color = shadowColor,
            startAngle = -90f,
            sweepAngle = 180f,
            useCenter = true,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = androidx.compose.ui.geometry.Size(2 * radius, 2 * radius)
        )
        
        // Add curved shadow edge
        if (phase > 0 && phase < 50) {
            val arcRadius = shadowWidth
            drawCircle(
                color = shadowColor,
                radius = arcRadius / 2,
                center = shadowCenter
            )
        }
    } else {
        // Waning phase: shadow from left
        val shadowWidth = ((phase - 50.0) / 50.0 * (2 * radius)).toFloat()
        val shadowCenter = Offset(center.x - radius + shadowWidth / 2, center.y)
        
        // Draw shadow arc
        drawArc(
            color = shadowColor,
            startAngle = 90f,
            sweepAngle = 180f,
            useCenter = true,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = androidx.compose.ui.geometry.Size(2 * radius, 2 * radius)
        )
        
        // Add curved shadow edge
        if (phase > 50 && phase < 100) {
            val arcRadius = shadowWidth
            drawCircle(
                color = shadowColor,
                radius = arcRadius / 2,
                center = shadowCenter
            )
        }
    }
}

fun DrawScope.drawMoonSurface(center: Offset, radius: Float) {
    val craterColor = Color(0x4D999980) // Semi-transparent gray
    
    // Draw several "craters"
    val craterData = listOf(
        Triple(-20f, -30f, 8f),
        Triple(15f, -10f, 12f),
        Triple(-10f, 20f, 6f),
        Triple(25f, 15f, 4f)
    )
    
    craterData.forEach { (offsetX, offsetY, craterRadius) ->
        drawCircle(
            color = craterColor,
            radius = craterRadius * (radius / 80f), // Scale with moon size
            center = Offset(center.x + offsetX * (radius / 80f), center.y + offsetY * (radius / 80f))
        )
    }
}

fun DrawScope.drawPhasePercentage(phase: Double) {
    val text = String.format("%.1f%%", phase)
    // Note: Text drawing in Canvas requires more complex setup
    // For simplicity, we'll display this in the data panel instead
}

@Composable
fun LunarDataPanel(lunarData: LunarData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE5E5F0)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Current Lunar Data",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333366),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            
            Divider(color = Color(0xFFCCCCDD))
            
            LunarDataRow(
                label = "Lunar Phase:",
                value = String.format("%.2f%%", lunarData.phase),
                valueColor = Color(0xFF3366CC)
            )
            
            LunarDataRow(
                label = "Illumination:",
                value = String.format("%.2f%%", lunarData.illumination),
                valueColor = Color(0xFFCC6633)
            )
            
            LunarDataRow(
                label = "Phase Name:",
                value = lunarData.phaseName,
                valueColor = Color(0xFF993399)
            )
            
            LunarDataRow(
                label = "Last Updated:",
                value = lunarData.lastUpdated,
                valueColor = Color.Gray
            )
        }
    }
}

@Composable
fun LunarDataRow(
    label: String,
    value: String,
    valueColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color(0xFF333333)
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = valueColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LunarMonitorPreview() {
    SwissEphemerisTheme {
        LunarMonitorScreen()
    }
}
