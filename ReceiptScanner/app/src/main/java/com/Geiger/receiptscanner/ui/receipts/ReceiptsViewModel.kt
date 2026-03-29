package com.Geiger.receiptscanner.ui.receipts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.Geiger.receiptscanner.ReceiptScannerApplication
import com.Geiger.receiptscanner.data.model.Receipt
import kotlinx.coroutines.launch

/**
 * ViewModel for managing receipts list backed by Room database
 */
class ReceiptsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = (application as ReceiptScannerApplication).repository

    private val _allReceipts: LiveData<List<Receipt>> = repository.allReceipts.asLiveData()
    private val _activeCategory = MutableLiveData("All")
    private val _searchQuery = MutableLiveData("")

    val receipts: LiveData<List<Receipt>> = MediatorLiveData<List<Receipt>>().apply {
        fun update() {
            val all = _allReceipts.value ?: emptyList()
            val category = _activeCategory.value ?: "All"
            val query = _searchQuery.value ?: ""
            value = all.filter { receipt ->
                val matchesCategory = category == "All" || receipt.category == category
                val matchesQuery = query.isBlank() ||
                    receipt.merchantName.contains(query, ignoreCase = true) ||
                    receipt.description.contains(query, ignoreCase = true)
                matchesCategory && matchesQuery
            }
        }
        addSource(_allReceipts) { update() }
        addSource(_activeCategory) { update() }
        addSource(_searchQuery) { update() }
    }

    fun setFilter(category: String) {
        _activeCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun deleteReceipt(receipt: Receipt) {
        viewModelScope.launch {
            repository.deleteReceipt(receipt)
        }
    }
}


