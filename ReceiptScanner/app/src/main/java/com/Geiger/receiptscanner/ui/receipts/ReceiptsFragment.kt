package com.Geiger.receiptscanner.ui.receipts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.Geiger.receiptscanner.databinding.FragmentReceiptsBinding

/**
 * Fragment displaying list of saved receipts
 */
class ReceiptsFragment : Fragment() {
    
    private var _binding: FragmentReceiptsBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: ReceiptsViewModel

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
        
        // Set up RecyclerView
        binding.receiptsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        
        // Observe receipts data
        viewModel.receipts.observe(viewLifecycleOwner) { receipts ->
            // TODO: Set up adapter with receipts
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
