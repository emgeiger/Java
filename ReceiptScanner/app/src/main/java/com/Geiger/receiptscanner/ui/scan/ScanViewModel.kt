package com.Geiger.receiptscanner.ui.scan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.Geiger.receiptscanner.ReceiptScannerApplication
import com.Geiger.receiptscanner.data.model.Receipt
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import java.math.BigDecimal

/**
 * ViewModel for managing scan functionality and persisting scanned receipts
 */
class ScanViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = (application as ReceiptScannerApplication).repository

    private val _isScanning = MutableLiveData(false)
    val isScanning: LiveData<Boolean> = _isScanning

    private val _scannedText = MutableLiveData("")
    val scannedText: LiveData<String> = _scannedText

    private val _saveResult = MutableLiveData<SaveResult>()
    val saveResult: LiveData<SaveResult> = _saveResult

    private val _isProcessing = MutableLiveData(false)
    val isProcessing: LiveData<Boolean> = _isProcessing

    fun startScanning() {
        _isScanning.value = true
    }

    fun stopScanning() {
        _isScanning.value = false
    }

    fun processScannedText(text: String) {
        _scannedText.value = text
        _isScanning.value = false
    }

    fun saveReceipt(
        merchantName: String,
        amount: String,
        date: String,
        category: String,
        description: String,
        imageUri: String = ""
    ) {
        _isProcessing.value = true
        viewModelScope.launch {
            try {
                val parsedAmount = amount.replace(Regex("[^0-9.]"), "")
                    .toBigDecimalOrNull() ?: BigDecimal.ZERO
                val parsedDate = try {
                    kotlinx.datetime.LocalDate.parse(date)
                } catch (e: Exception) {
                    Clock.System.todayIn(TimeZone.currentSystemDefault())
                }
                val receipt = Receipt(
                    merchantName = merchantName.ifBlank { "Unknown Merchant" },
                    amount = parsedAmount,
                    date = parsedDate,
                    category = category.ifBlank { "Other" },
                    description = description,
                    imageUri = imageUri
                )
                repository.insertReceipt(receipt)
                _saveResult.postValue(SaveResult.Success)
            } catch (e: Exception) {
                _saveResult.postValue(SaveResult.Error(e.message ?: "Failed to save receipt"))
            } finally {
                _isProcessing.postValue(false)
            }
        }
    }

    /**
     * Parses OCR text and returns a map of extracted receipt fields
     */
    fun parseReceiptText(ocrText: String): Map<String, String> {
        val result = mutableMapOf<String, String>()

        // Extract amount: look for patterns like $12.34 or 12.34
        val amountRegex = Regex("""\$?\s*(\d{1,4}[.,]\d{2})\b""")
        val amounts = amountRegex.findAll(ocrText).map { it.groupValues[1].replace(",", ".") }
            .filter { it.toBigDecimalOrNull() != null }
            .sortedByDescending { it.toBigDecimalOrNull() }
        result["amount"] = amounts.firstOrNull() ?: ""

        // Extract date: look for common date patterns
        val dateRegex = Regex("""(\d{1,2}[/\-]\d{1,2}[/\-]\d{2,4}|\d{4}-\d{2}-\d{2})""")
        result["date"] = dateRegex.find(ocrText)?.value?.let { normalizeDate(it) } ?: ""

        // Merchant name: typically first non-empty line of the receipt
        val lines = ocrText.lines().map { it.trim() }.filter { it.isNotBlank() }
        result["merchantName"] = lines.firstOrNull() ?: ""

        return result
    }

    private fun normalizeDate(raw: String): String {
        return try {
            // Already in ISO format
            if (raw.matches(Regex("""\d{4}-\d{2}-\d{2}"""))) return raw
            val parts = raw.split(Regex("[/\\-]"))
            if (parts.size == 3) {
                val year = if (parts[2].length == 2) "20${parts[2]}" else parts[2]
                val first = parts[0].toInt()
                val second = parts[1].toInt()
                // Determine order: if first > 12, it must be a day (DD/MM/YYYY)
                val (month, day) = if (first > 12) second to first else first to second
                if (month in 1..12 && day in 1..31) {
                    "%s-%02d-%02d".format(year, month, day)
                } else ""
            } else ""
        } catch (e: Exception) {
            ""
        }
    }

    sealed class SaveResult {
        object Success : SaveResult()
        data class Error(val message: String) : SaveResult()
    }
}
