package com.Geiger.receiptscanner.ui.receipts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.Geiger.receiptscanner.data.model.Receipt
import com.Geiger.receiptscanner.data.repository.ReceiptRepository

/**
 * ViewModel for managing receipts list
 */
class ReceiptsViewModel(application: Application) : AndroidViewModel(application) {
    
    private val receiptRepository = ReceiptRepository()
    
    private val _receipts = MutableLiveData<List<Receipt>>()
    val receipts: LiveData<List<Receipt>> = _receipts
    
    init {
        loadReceipts()
    }
    
    private fun loadReceipts() {
        _receipts.value = receiptRepository.getAllReceipts()
    }
    
    fun searchReceipts(query: String): List<Receipt> {
        return receiptRepository.getAllReceipts().filter { 
            it.merchantName.contains(query, ignoreCase = true) ||
            it.description.contains(query, ignoreCase = true)
        }
    }
    
    fun getReceiptsByCategory(category: String): List<Receipt> {
        return receiptRepository.getAllReceipts().filter { it.category == category }
    }
}
