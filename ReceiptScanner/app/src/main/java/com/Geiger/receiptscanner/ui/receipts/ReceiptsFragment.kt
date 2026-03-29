package com.Geiger.receiptscanner.ui.receipts

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.Geiger.receiptscanner.data.model.Receipt
import com.Geiger.receiptscanner.databinding.FragmentReceiptsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Fragment displaying a searchable, filterable list of saved receipts
 */
class ReceiptsFragment : Fragment() {

    private var _binding: FragmentReceiptsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ReceiptsViewModel
    private lateinit var receiptAdapter: ReceiptAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ReceiptsViewModel::class.java]
        _binding = FragmentReceiptsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupFilters()
        setupSearch()
        observeReceipts()
    }

    private fun setupRecyclerView() {
        receiptAdapter = ReceiptAdapter(
            onReceiptClick = { receipt -> showReceiptDetails(receipt) },
            onDeleteClick = { receipt -> confirmDelete(receipt) }
        )
        binding.receiptsRecyclerView.apply {
            adapter = receiptAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupFilters() {
        binding.filterAll.setOnClickListener { viewModel.setFilter("All") }
        binding.filterGroceries.setOnClickListener { viewModel.setFilter("Groceries") }
        binding.filterDining.setOnClickListener { viewModel.setFilter("Food & Dining") }
        binding.filterTransportation.setOnClickListener { viewModel.setFilter("Transportation") }
    }

    private fun setupSearch() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.setSearchQuery(s?.toString() ?: "")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun observeReceipts() {
        viewModel.receipts.observe(viewLifecycleOwner) { receipts ->
            receiptAdapter.submitList(receipts)
            if (receipts.isEmpty()) {
                binding.emptyState.visibility = View.VISIBLE
                binding.receiptsRecyclerView.visibility = View.GONE
            } else {
                binding.emptyState.visibility = View.GONE
                binding.receiptsRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    private fun showReceiptDetails(receipt: Receipt) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(receipt.merchantName)
            .setMessage(
                buildString {
                    append("Amount: $%.2f\n".format(receipt.amount))
                    append("Date: ${receipt.date}\n")
                    append("Category: ${receipt.category}")
                    if (receipt.description.isNotBlank()) {
                        append("\nNotes: ${receipt.description}")
                    }
                }
            )
            .setPositiveButton("OK", null)
            .setNegativeButton("Delete") { _, _ -> confirmDelete(receipt) }
            .show()
    }

    private fun confirmDelete(receipt: Receipt) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Receipt")
            .setMessage("Delete receipt from ${receipt.merchantName}?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteReceipt(receipt)
                Toast.makeText(requireContext(), "Receipt deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


