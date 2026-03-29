package com.Geiger.receiptscanner.ui.analytics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.Geiger.receiptscanner.databinding.FragmentAnalyticsBinding

/**
 * Fragment displaying analytics and spending insights from the Room database
 */
class AnalyticsFragment : Fragment() {

    private var _binding: FragmentAnalyticsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AnalyticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[AnalyticsViewModel::class.java]
        _binding = FragmentAnalyticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAnalytics()
    }

    private fun observeAnalytics() {
        viewModel.totalSpending.observe(viewLifecycleOwner) { total ->
            binding.totalSpendingValue.text = "$%.2f".format(total ?: java.math.BigDecimal.ZERO)
        }

        viewModel.monthlySpending.observe(viewLifecycleOwner) { monthly ->
            binding.monthlySpendingValue.text = "$%.2f".format(monthly ?: java.math.BigDecimal.ZERO)
        }

        viewModel.categoryBreakdown.observe(viewLifecycleOwner) { breakdown ->
            if (breakdown.isNullOrEmpty()) {
                binding.categoryBreakdownText.text = "No data available"
            } else {
                binding.categoryBreakdownText.text = breakdown.entries
                    .sortedByDescending { it.value }
                    .joinToString("\n") { (category, amount) ->
                        "$category: $%.2f".format(amount)
                    }
            }
        }

        viewModel.receiptCount.observe(viewLifecycleOwner) { count ->
            binding.receiptCountValue.text = count?.toString() ?: "0"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

