package com.Geiger.receiptscanner.data.repository

import com.Geiger.receiptscanner.data.model.Receipt
import kotlinx.datetime.LocalDate
import java.math.BigDecimal

/**
 * Repository for receipt data
 * Provides sample data for now, will integrate with database later
 */
class ReceiptRepository {
    
    fun getAllReceipts(): List<Receipt> {
        return listOf(
            Receipt(
                id = 1,
                merchantName = "Sample Store 1",
                amount = BigDecimal("25.50"),
                date = LocalDate.parse("2024-01-15"),
                category = "Groceries"
            ),
            Receipt(
                id = 2,
                merchantName = "Sample Store 2", 
                amount = BigDecimal("45.00"),
                date = LocalDate.parse("2024-01-20"),
                category = "Gas"
            ),
            Receipt(
                id = 3,
                merchantName = "Sample Store 3",
                amount = BigDecimal("12.99"),
                date = LocalDate.parse("2024-01-25"),
                category = "Food"
            )
        )
    }
    
    fun getReceiptsByDateRange(startDate: LocalDate, endDate: LocalDate): List<Receipt> {
        return getAllReceipts().filter { receipt ->
            receipt.date >= startDate && receipt.date <= endDate
        }
    }
    
    fun getTotalAmount(): BigDecimal {
        return getAllReceipts().sumOf { it.amount }
    }
}
