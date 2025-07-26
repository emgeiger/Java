package com.ovintiv.receiptscanner.ui.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovintiv.receiptscanner.ReceiptScannerApplication
import com.ovintiv.receiptscanner.data.database.Receipt
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.regex.Pattern

/**
 * Scan ViewModel
 * Handles OCR text processing and receipt data extraction
 */
class ScanViewModel : ViewModel() {
    
    private val receiptDao = ReceiptScannerApplication.instance.database.receiptDao()
    
    fun processOCRText(ocrText: String, imagePath: String) {
        viewModelScope.launch {
            val receipt = extractReceiptData(ocrText, imagePath)
            receiptDao.insertReceipt(receipt)
        }
    }
    
    private fun extractReceiptData(ocrText: String, imagePath: String): Receipt {
        val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
        
        return Receipt(
            merchantName = extractMerchantName(ocrText),
            totalAmount = extractTotalAmount(ocrText),
            currency = "USD",
            date = extractDate(ocrText) ?: currentTime.substring(0, 10), // Use current date if not found
            category = "General", // Default category, user can change later
            description = "",
            imagePath = imagePath,
            ocrText = ocrText,
            tags = "",
            isReimbursable = false,
            notes = "",
            createdAt = currentTime,
            updatedAt = currentTime
        )
    }
    
    private fun extractMerchantName(text: String): String {
        val lines = text.split("\n").map { it.trim() }
        
        // Look for common merchant indicators
        val merchantKeywords = listOf("LLC", "INC", "CORP", "STORE", "MARKET", "RESTAURANT", "CAFE")
        
        for (line in lines.take(5)) { // Check first 5 lines
            if (line.length > 3 && merchantKeywords.any { keyword -> 
                line.uppercase().contains(keyword) 
            }) {
                return line.take(50) // Limit length
            }
        }
        
        // If no merchant keywords found, return the first substantial line
        return lines.firstOrNull { it.length > 3 }?.take(50) ?: "Unknown Merchant"
    }
    
    private fun extractTotalAmount(text: String): Double {
        // Patterns to match total amounts
        val patterns = listOf(
            Pattern.compile("TOTAL[\\s:]*\\$?([0-9]+\\.?[0-9]*)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("AMOUNT[\\s:]*\\$?([0-9]+\\.?[0-9]*)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("\\$([0-9]+\\.[0-9]{2})"),
            Pattern.compile("([0-9]+\\.[0-9]{2})\\s*$", Pattern.MULTILINE)
        )
        
        val amounts = mutableListOf<Double>()
        
        for (pattern in patterns) {
            val matcher = pattern.matcher(text)
            while (matcher.find()) {
                try {
                    val amountStr = matcher.group(1) ?: matcher.group(0)
                    val amount = amountStr.replace("$", "").toDoubleOrNull()
                    if (amount != null && amount > 0) {
                        amounts.add(amount)
                    }
                } catch (e: Exception) {
                    // Ignore parsing errors
                }
            }
        }
        
        // Return the largest amount found, or 0.0 if none found
        return amounts.maxOrNull() ?: 0.0
    }
    
    private fun extractDate(text: String): String? {
        // Common date patterns
        val patterns = listOf(
            Pattern.compile("([0-9]{1,2})/([0-9]{1,2})/([0-9]{2,4})"),
            Pattern.compile("([0-9]{1,2})-([0-9]{1,2})-([0-9]{2,4})"),
            Pattern.compile("([0-9]{4})-([0-9]{1,2})-([0-9]{1,2})"),
            Pattern.compile("(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)[a-z]*\\s+([0-9]{1,2}),?\\s+([0-9]{4})", Pattern.CASE_INSENSITIVE)
        )
        
        for (pattern in patterns) {
            val matcher = pattern.matcher(text)
            if (matcher.find()) {
                try {
                    return when (pattern) {
                        patterns[0], patterns[1] -> {
                            // MM/DD/YYYY or MM-DD-YYYY format
                            val month = matcher.group(1)!!.padStart(2, '0')
                            val day = matcher.group(2)!!.padStart(2, '0')
                            var year = matcher.group(3)!!
                            if (year.length == 2) {
                                year = "20$year" // Assume 2000s for 2-digit years
                            }
                            "$year-$month-$day"
                        }
                        patterns[2] -> {
                            // YYYY-MM-DD format (already correct)
                            matcher.group(0)
                        }
                        patterns[3] -> {
                            // Month name format
                            val monthName = matcher.group(1)!!
                            val day = matcher.group(2)!!.padStart(2, '0')
                            val year = matcher.group(3)!!
                            val month = getMonthNumber(monthName).padStart(2, '0')
                            "$year-$month-$day"
                        }
                        else -> null
                    }
                } catch (e: Exception) {
                    // Continue to next pattern if parsing fails
                    continue
                }
            }
        }
        
        return null
    }
    
    private fun getMonthNumber(monthName: String): String {
        return when (monthName.lowercase().substring(0, 3)) {
            "jan" -> "01"
            "feb" -> "02"
            "mar" -> "03"
            "apr" -> "04"
            "may" -> "05"
            "jun" -> "06"
            "jul" -> "07"
            "aug" -> "08"
            "sep" -> "09"
            "oct" -> "10"
            "nov" -> "11"
            "dec" -> "12"
            else -> "01"
        }
    }
}
