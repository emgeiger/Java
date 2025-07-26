package com.ovintiv.receiptscanner.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Data Access Object for Receipt operations
 * Defines database operations for receipts
 */
@Dao
interface ReceiptDao {
    
    @Query("SELECT * FROM receipts ORDER BY date DESC")
    fun getAllReceipts(): LiveData<List<Receipt>>
    
    @Query("SELECT * FROM receipts WHERE id = :id")
    suspend fun getReceiptById(id: Long): Receipt?
    
    @Query("SELECT * FROM receipts WHERE category = :category ORDER BY date DESC")
    fun getReceiptsByCategory(category: String): LiveData<List<Receipt>>
    
    @Query("SELECT * FROM receipts WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getReceiptsByDateRange(startDate: String, endDate: String): LiveData<List<Receipt>>
    
    @Query("SELECT * FROM receipts WHERE merchantName LIKE '%' || :searchTerm || '%' OR description LIKE '%' || :searchTerm || '%' ORDER BY date DESC")
    fun searchReceipts(searchTerm: String): LiveData<List<Receipt>>
    
    @Query("SELECT SUM(totalAmount) FROM receipts WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getTotalAmountByDateRange(startDate: String, endDate: String): Double?
    
    @Query("SELECT SUM(totalAmount) FROM receipts WHERE category = :category AND date BETWEEN :startDate AND :endDate")
    suspend fun getTotalAmountByCategoryAndDateRange(category: String, startDate: String, endDate: String): Double?
    
    @Insert
    suspend fun insertReceipt(receipt: Receipt): Long
    
    @Update
    suspend fun updateReceipt(receipt: Receipt)
    
    @Delete
    suspend fun deleteReceipt(receipt: Receipt)
    
    @Query("DELETE FROM receipts WHERE id = :id")
    suspend fun deleteReceiptById(id: Long)
}
