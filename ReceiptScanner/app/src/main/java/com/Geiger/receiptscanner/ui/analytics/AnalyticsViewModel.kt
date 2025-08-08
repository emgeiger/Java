package com.Geiger.receiptscanner.ui.analytics

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.Geiger.receiptscanner.data.model.Receipt
import com.Geiger.receiptscanner.data.repository.ReceiptRepository
import java.math.BigDecimal
import kotlinx.datetime.LocalDate

/**
 * ViewModel for managing analytics and spending insights
 */
class AnalyticsViewModel(application: Application) : AndroidViewModel(application) {
    
    private val receiptRepository = ReceiptRepository()
    
    private val _receipts = MutableLiveData<List<Receipt>>()
    val receipts: LiveData<List<Receipt>> = _receipts
    
    private val _totalSpending = MutableLiveData<BigDecimal>()
    val totalSpending: LiveData<BigDecimal> = _totalSpending
    
    private val _monthlySpending = MutableLiveData<BigDecimal>()
    val monthlySpending: LiveData<BigDecimal> = _monthlySpending
    
    private val _categoryBreakdown = MutableLiveData<Map<String, BigDecimal>>()
    val categoryBreakdown: LiveData<Map<String, BigDecimal>> = _categoryBreakdown
    
    private val _reimbursableAmount = MutableLiveData<BigDecimal>()
    val reimbursableAmount: LiveData<BigDecimal> = _reimbursableAmount
    
    init {
        loadReceipts()
    }
    
    private fun loadReceipts() {
        val receiptList = receiptRepository.getAllReceipts()
        _receipts.value = receiptList
        calculateAnalytics(receiptList)
    }
    
    private fun calculateAnalytics(receiptList: List<Receipt>) {
        if (receiptList.isEmpty()) {
            _totalSpending.value = BigDecimal.ZERO
            _monthlySpending.value = BigDecimal.ZERO
            _categoryBreakdown.value = emptyMap()
            _reimbursableAmount.value = BigDecimal.ZERO
            return
        }
        
        // Calculate total spending
        val total = receiptList.sumOf { it.amount }
        _totalSpending.value = total
        
        // Calculate monthly spending (current month)
        val currentDate = LocalDate.parse("2024-01-01")  // Sample date for now
        val monthlyTotal = receiptList
            .filter { it.date.year == currentDate.year && it.date.monthNumber == currentDate.monthNumber }
            .sumOf { it.amount }
        _monthlySpending.value = monthlyTotal
        
        // Calculate category breakdown
        val breakdown = receiptList
            .groupBy { it.category }
            .mapValues { (_, receipts) -> receipts.sumOf { it.amount } }
        _categoryBreakdown.value = breakdown
        
        // Calculate reimbursable amount (for now, assume 0)
        _reimbursableAmount.value = BigDecimal.ZERO
    }
    
    fun getReceiptsByDateRange(startDate: LocalDate, endDate: LocalDate): List<Receipt> {
        return receiptRepository.getReceiptsByDateRange(startDate, endDate)
    }
    
    fun getTotalAmount(): BigDecimal {
        return receiptRepository.getTotalAmount()
    }
}
