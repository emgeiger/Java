package com.emgeiger.swissephemeris.lunar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

data class LunarData(
    val phase: Double = 0.0,
    val illumination: Double = 0.0,
    val phaseName: String = "New Moon",
    val lastUpdated: String = "Loading..."
)

class LunarViewModel : ViewModel() {
    private val _lunarData = MutableStateFlow(LunarData())
    val lunarData: StateFlow<LunarData> = _lunarData.asStateFlow()
    
    private val moonPhases = MoonPhasesAndroid()
    
    fun updateLunarData() {
        viewModelScope.launch {
            try {
                val currentPhase = moonPhases.getCurrentLunarPhase()
                val currentIllumination = moonPhases.getLunarIllumination()
                val currentPhaseName = moonPhases.getPhaseName(currentPhase)
                
                val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault())
                val timestamp = dateFormat.format(Date())
                
                _lunarData.value = LunarData(
                    phase = currentPhase,
                    illumination = currentIllumination,
                    phaseName = currentPhaseName,
                    lastUpdated = "Updated: $timestamp"
                )
                
                println("Swiss Ephemeris Data: Phase=${String.format("%.2f%%", currentPhase)}, " +
                        "Illumination=${String.format("%.2f%%", currentIllumination)}, " +
                        "Name=$currentPhaseName")
                        
            } catch (e: Exception) {
                println("Error loading lunar data: ${e.message}")
                _lunarData.value = LunarData(
                    phase = 0.0,
                    illumination = 0.0,
                    phaseName = "Swiss Ephemeris Error",
                    lastUpdated = "Load Failed: ${Date()}"
                )
            }
        }
    }
}
