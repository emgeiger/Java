package com.Geiger.receiptscanner.data.repository

import com.Geiger.receiptscanner.data.database.ReceiptDao
import com.Geiger.receiptscanner.data.database.ReceiptEntity
import com.Geiger.receiptscanner.data.model.Receipt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import java.math.BigDecimal

/**
 * Repository for receipt data backed by Room database.
 * Converts between database entities and domain models.
 */
class ReceiptRepository(private val receiptDao: ReceiptDao) {

    val allReceipts: Flow<List<Receipt>> = receiptDao.getAllReceipts().map { entities ->
        entities.map { it.toDomainModel() }
    }

    val totalAmount: Flow<BigDecimal> = receiptDao.getTotalAmount().map { amount ->
        amount?.let { BigDecimal(it.toString()) } ?: BigDecimal.ZERO
    }

    fun getReceiptsByCategory(category: String): Flow<List<Receipt>> =
        receiptDao.getReceiptsByCategory(category).map { entities ->
            entities.map { it.toDomainModel() }
        }

    fun searchReceipts(query: String): Flow<List<Receipt>> =
        receiptDao.searchReceipts("%$query%").map { entities ->
            entities.map { it.toDomainModel() }
        }

    fun getReceiptsByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<Receipt>> =
        receiptDao.getReceiptsByDateRange(startDate.toString(), endDate.toString()).map { entities ->
            entities.map { it.toDomainModel() }
        }

    fun getMonthlyAmount(year: Int, month: Int): Flow<BigDecimal> {
        val yearMonth = "$year-%02d".format(month)
        return receiptDao.getMonthlyAmount(yearMonth).map { amount ->
            amount?.let { BigDecimal(it.toString()) } ?: BigDecimal.ZERO
        }
    }

    suspend fun insertReceipt(receipt: Receipt): Long =
        receiptDao.insertReceipt(receipt.toEntity())

    suspend fun updateReceipt(receipt: Receipt) =
        receiptDao.updateReceipt(receipt.toEntity())

    suspend fun deleteReceipt(receipt: Receipt) =
        receiptDao.deleteReceipt(receipt.toEntity())

    suspend fun deleteReceiptById(id: Long) =
        receiptDao.deleteReceiptById(id)

    private fun ReceiptEntity.toDomainModel() = Receipt(
        id = id,
        merchantName = merchantName,
        amount = BigDecimal(amount.toString()),
        date = LocalDate.parse(date),
        category = category,
        description = description,
        imageUri = imageUri
    )

    private fun Receipt.toEntity() = ReceiptEntity(
        id = id,
        merchantName = merchantName,
        amount = amount.toDouble(),
        date = date.toString(),
        category = category,
        description = description,
        imageUri = imageUri
    )
}

