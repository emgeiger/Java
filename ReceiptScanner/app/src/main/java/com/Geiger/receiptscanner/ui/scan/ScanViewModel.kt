package com.Geiger.receiptscanner.ui.scan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * ViewModel for managing scan functionality
 */
class ScanViewModel(application: Application) : AndroidViewModel(application) {
    
    private val _isScanning = MutableLiveData<Boolean>()
    val isScanning: LiveData<Boolean> = _isScanning
    
    private val _scannedText = MutableLiveData<String>()
    val scannedText: LiveData<String> = _scannedText
    
    init {
        _isScanning.value = false
        _scannedText.value = ""
    }
    
    fun startScanning() {
        _isScanning.value = true
    }
    
    fun stopScanning() {
        _isScanning.value = false
    }
    
    fun processScannedText(text: String) {
        _scannedText.value = text
        _isScanning.value = false
    }
}