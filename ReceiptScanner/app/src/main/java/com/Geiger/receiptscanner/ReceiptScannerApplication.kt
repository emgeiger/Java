package com.Geiger.receiptscanner

import android.app.Application

/**
 * Application class for the Receipt Scanner App
 */
class ReceiptScannerApplication : Application() {
    
    companion object {
        lateinit var instance: ReceiptScannerApplication
            private set
    }
    
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}