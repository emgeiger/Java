package com.Geiger.receiptscanner.data.model

import java.math.BigDecimal
import kotlinx.datetime.LocalDate

/**
 * Data model representing a receipt
 */
data class Receipt(
    val id: Long = 0,
    val merchantName: String,
    val amount: BigDecimal,
    val date: LocalDate,
    val category: String = "Other",
    val description: String = "",
    val imageUri: String = ""
)
