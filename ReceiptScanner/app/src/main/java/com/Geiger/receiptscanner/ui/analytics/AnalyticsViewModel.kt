package com.Geiger.receiptscanner.ui.analytics

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.Geiger.receiptscanner.ReceiptScannerApplication
import com.Geiger.receiptscanner.data.model.Receipt
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import java.math.BigDecimal

/**
 * ViewModel for managing analytics and spending insights
 */
class AnalyticsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = (application as ReceiptScannerApplication).repository

    val receipts: LiveData<List<Receipt>> = repository.allReceipts.asLiveData()

    val totalSpending: LiveData<BigDecimal> = repository.totalAmount.asLiveData()

    val categoryBreakdown: LiveData<Map<String, BigDecimal>> =
        repository.allReceipts.map { list ->
            list.groupBy { it.category }
                .mapValues { (_, receipts) -> receipts.fold(BigDecimal.ZERO) { acc, r -> acc + r.amount } }
        }.asLiveData()

    val monthlySpending: LiveData<BigDecimal> = run {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        repository.getMonthlyAmount(today.year, today.monthNumber).asLiveData()
    }

    val receiptCount: LiveData<Int> = repository.allReceipts.map { it.size }.asLiveData()
}

