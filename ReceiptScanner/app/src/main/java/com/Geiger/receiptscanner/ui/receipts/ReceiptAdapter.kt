package com.Geiger.receiptscanner.ui.receipts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.Geiger.receiptscanner.data.model.Receipt
import com.Geiger.receiptscanner.databinding.ItemReceiptBinding

/**
 * RecyclerView adapter for displaying a list of receipts
 */
class ReceiptAdapter(
    private val onReceiptClick: (Receipt) -> Unit,
    private val onDeleteClick: (Receipt) -> Unit
) : ListAdapter<Receipt, ReceiptAdapter.ReceiptViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptViewHolder {
        val binding = ItemReceiptBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReceiptViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReceiptViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ReceiptViewHolder(private val binding: ItemReceiptBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(receipt: Receipt) {
            binding.merchantName.text = receipt.merchantName
            binding.date.text = receipt.date.toString()
            binding.amount.text = "$%.2f".format(receipt.amount)
            binding.categoryChip.text = receipt.category

            binding.root.setOnClickListener { onReceiptClick(receipt) }
            binding.root.setOnLongClickListener {
                onDeleteClick(receipt)
                true
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Receipt>() {
            override fun areItemsTheSame(oldItem: Receipt, newItem: Receipt) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Receipt, newItem: Receipt) =
                oldItem == newItem
        }
    }
}
