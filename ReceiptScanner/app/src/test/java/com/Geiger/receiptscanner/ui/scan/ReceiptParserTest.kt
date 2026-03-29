package com.Geiger.receiptscanner.ui.scan

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for receipt OCR text parsing logic
 * Tests the parseReceiptText helper without needing Android context
 */
class ReceiptParserTest {

    /**
     * A simple standalone replication of the parser logic for unit testing
     * (mirrors ScanViewModel.parseReceiptText)
     */
    private fun parseReceiptText(ocrText: String): Map<String, String> {
        val result = mutableMapOf<String, String>()

        val amountRegex = Regex("""\$?\s*(\d{1,4}[.,]\d{2})\b""")
        val amounts = amountRegex.findAll(ocrText)
            .map { it.groupValues[1].replace(",", ".") }
            .filter { it.toBigDecimalOrNull() != null }
            .sortedByDescending { it.toBigDecimalOrNull() }
        result["amount"] = amounts.firstOrNull() ?: ""

        val dateRegex = Regex("""(\d{1,2}[/\-]\d{1,2}[/\-]\d{2,4}|\d{4}-\d{2}-\d{2})""")
        result["date"] = dateRegex.find(ocrText)?.value?.let { normalizeDate(it) } ?: ""

        val lines = ocrText.lines().map { it.trim() }.filter { it.isNotBlank() }
        result["merchantName"] = lines.firstOrNull() ?: ""

        return result
    }

    private fun normalizeDate(raw: String): String {
        return try {
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

    @Test
    fun parseReceiptText_extractsMerchantName() {
        val text = "Walmart Supercenter\n1234 Main St\nDate: 01/15/2024\nTotal: \$45.67"
        val result = parseReceiptText(text)
        assertEquals("Walmart Supercenter", result["merchantName"])
    }

    @Test
    fun parseReceiptText_extractsAmount_dollarSign() {
        val text = "Store Name\n01/15/2024\nSubtotal: \$35.00\nTax: \$2.80\nTotal: \$37.80"
        val result = parseReceiptText(text)
        assertEquals("37.80", result["amount"])
    }

    @Test
    fun parseReceiptText_extractsAmount_noSign() {
        val text = "Coffee Shop\n12.50"
        val result = parseReceiptText(text)
        assertEquals("12.50", result["amount"])
    }

    @Test
    fun parseReceiptText_extractsDate_slashFormat() {
        val text = "Store\n01/15/2024\n\$10.00"
        val result = parseReceiptText(text)
        assertEquals("2024-01-15", result["date"])
    }

    @Test
    fun parseReceiptText_extractsDate_isoFormat() {
        val text = "Store\n2024-03-20\n\$10.00"
        val result = parseReceiptText(text)
        assertEquals("2024-03-20", result["date"])
    }

    @Test
    fun parseReceiptText_emptyText_returnsEmptyFields() {
        val result = parseReceiptText("")
        assertEquals("", result["merchantName"])
        assertEquals("", result["amount"])
        assertEquals("", result["date"])
    }

    @Test
    fun parseReceiptText_returnsLargestAmount() {
        val text = "Store\nItem1: \$5.00\nItem2: \$12.99\nTotal: \$17.99"
        val result = parseReceiptText(text)
        assertEquals("17.99", result["amount"])
    }

    @Test
    fun parseReceiptText_twoDigitYear_normalizesCorrectly() {
        val text = "Store\n12/25/23\n\$50.00"
        val result = parseReceiptText(text)
        assertEquals("2023-12-25", result["date"])
    }

    @Test
    fun parseReceiptText_dayFirstFormat_normalizesCorrectly() {
        val text = "Store\n25/12/2024\n\$50.00"
        val result = parseReceiptText(text)
        // 25 > 12, so it must be DD/MM/YYYY -> 2024-12-25
        assertEquals("2024-12-25", result["date"])
    }
}
