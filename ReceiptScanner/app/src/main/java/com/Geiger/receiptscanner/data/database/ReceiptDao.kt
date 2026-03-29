package com.Geiger.receiptscanner.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for receipt database operations
 */
@Dao
interface ReceiptDao {

    @Query("SELECT * FROM receipts ORDER BY date DESC")
    fun getAllReceipts(): Flow<List<ReceiptEntity>>

    @Query("SELECT * FROM receipts WHERE id = :id")
    suspend fun getReceiptById(id: Long): ReceiptEntity?

    @Query("SELECT * FROM receipts WHERE category = :category ORDER BY date DESC")
    fun getReceiptsByCategory(category: String): Flow<List<ReceiptEntity>>

    @Query("SELECT * FROM receipts WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getReceiptsByDateRange(startDate: String, endDate: String): Flow<List<ReceiptEntity>>

    @Query("SELECT * FROM receipts WHERE merchant_name LIKE :query OR description LIKE :query ORDER BY date DESC")
    fun searchReceipts(query: String): Flow<List<ReceiptEntity>>

    @Query("SELECT SUM(amount) FROM receipts")
    fun getTotalAmount(): Flow<Double?>

    @Query("SELECT SUM(amount) FROM receipts WHERE date LIKE :yearMonth || '%'")
    fun getMonthlyAmount(yearMonth: String): Flow<Double?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReceipt(receipt: ReceiptEntity): Long

    @Update
    suspend fun updateReceipt(receipt: ReceiptEntity)

    @Delete
    suspend fun deleteReceipt(receipt: ReceiptEntity)

    @Query("DELETE FROM receipts WHERE id = :id")
    suspend fun deleteReceiptById(id: Long)
}
