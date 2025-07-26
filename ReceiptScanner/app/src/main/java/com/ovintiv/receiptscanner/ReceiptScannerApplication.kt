package com.ovintiv.receiptscanner

import android.app.Application
import androidx.room.Room
import com.ovintiv.receiptscanner.data.database.ReceiptDatabase

/**
 * Receipt Scanner Application
 * Main application class for the receipt scanning app
 */
class ReceiptScannerApplication : Application() {
    
    // Database instance
    val database by lazy { 
        Room.databaseBuilder(
            this,
            ReceiptDatabase::class.java,
            "receipt_database"
        ).build()
    }
    
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    
    companion object {
        lateinit var instance: ReceiptScannerApplication
            private set
    }
}
