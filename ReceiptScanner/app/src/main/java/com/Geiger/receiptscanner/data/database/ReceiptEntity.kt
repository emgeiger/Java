package com.Geiger.receiptscanner.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room database entity representing a stored receipt
 */
@Entity(tableName = "receipts")
data class ReceiptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "merchant_name")
    val merchantName: String,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "category")
    val category: String = "Other",

    @ColumnInfo(name = "description")
    val description: String = "",

    @ColumnInfo(name = "image_uri")
    val imageUri: String = ""
)
