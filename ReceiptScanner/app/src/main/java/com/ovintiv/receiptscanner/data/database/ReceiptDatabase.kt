package com.ovintiv.receiptscanner.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

/**
 * Receipt Database
 * Room database for storing receipt data
 */
@Database(
    entities = [Receipt::class],
    version = 1,
    exportSchema = false
)
abstract class ReceiptDatabase : RoomDatabase() {
    
    abstract fun receiptDao(): ReceiptDao
    
    companion object {
        @Volatile
        private var INSTANCE: ReceiptDatabase? = null
        
        fun getDatabase(context: Context): ReceiptDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReceiptDatabase::class.java,
                    "receipt_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
