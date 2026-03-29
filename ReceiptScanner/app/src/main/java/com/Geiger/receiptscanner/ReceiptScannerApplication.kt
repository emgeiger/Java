package com.Geiger.receiptscanner

import android.app.Application
import com.Geiger.receiptscanner.data.database.ReceiptDatabase
import com.Geiger.receiptscanner.data.repository.ReceiptRepository

/**
 * Application class for the Receipt Scanner App
 */
class ReceiptScannerApplication : Application() {

    val database by lazy { ReceiptDatabase.getDatabase(this) }
    val repository by lazy { ReceiptRepository(database.receiptDao()) }

    companion object {
        lateinit var instance: ReceiptScannerApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
