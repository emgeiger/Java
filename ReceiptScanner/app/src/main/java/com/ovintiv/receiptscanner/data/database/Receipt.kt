package com.ovintiv.receiptscanner.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

/**
 * Receipt entity for Room database
 * Represents a scanned receipt with OCR extracted data
 */
@Entity(tableName = "receipts")
data class Receipt(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val merchantName: String,
    val totalAmount: Double,
    val currency: String = "USD",
    val date: String, // Stored as ISO string
    val category: String = "General",
    val description: String = "",
    val imagePath: String, // Path to the stored receipt image
    val ocrText: String = "", // Raw OCR text for reference
    val tags: String = "", // Comma-separated tags
    val isReimbursable: Boolean = false,
    val notes: String = "",
    val createdAt: String, // When the receipt was scanned/created
    val updatedAt: String  // When the receipt was last modified
)
