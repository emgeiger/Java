package com.Geiger.receiptscanner.ui.scan

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.Geiger.receiptscanner.R
import com.Geiger.receiptscanner.databinding.FragmentScanBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private const val TAG = "ScanFragment"

/**
 * Fragment for scanning receipts using the device camera with ML Kit OCR
 */
class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ScanViewModel
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startCamera()
        } else {
            Toast.makeText(
                requireContext(),
                "Camera permission is required to scan receipts",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ScanViewModel::class.java]
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraExecutor = Executors.newSingleThreadExecutor()

        binding.captureButton.setOnClickListener { takePhoto() }

        observeViewModel()
        checkCameraPermission()
    }

    private fun observeViewModel() {
        viewModel.isProcessing.observe(viewLifecycleOwner) { processing ->
            binding.progressBar.visibility = if (processing) View.VISIBLE else View.GONE
            binding.captureButton.isEnabled = !processing
        }

        viewModel.saveResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ScanViewModel.SaveResult.Success ->
                    Toast.makeText(requireContext(), "Receipt saved successfully!", Toast.LENGTH_SHORT).show()
                is ScanViewModel.SaveResult.Error ->
                    Toast.makeText(requireContext(), "Error: ${result.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> startCamera()
            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            }
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    viewLifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageCapture
                )
            } catch (e: Exception) {
                Log.e(TAG, "Camera binding failed", e)
                Toast.makeText(requireContext(), "Failed to start camera", Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        binding.progressBar.visibility = View.VISIBLE
        binding.captureButton.isEnabled = false

        imageCapture.takePicture(
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    processImageWithOcr(image)
                }

                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                    binding.progressBar.visibility = View.GONE
                    binding.captureButton.isEnabled = true
                    Toast.makeText(requireContext(), "Failed to capture photo", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun processImageWithOcr(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image ?: run {
            imageProxy.close()
            binding.progressBar.visibility = View.GONE
            binding.captureButton.isEnabled = true
            return
        }
        val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        recognizer.process(inputImage)
            .addOnSuccessListener { visionText ->
                imageProxy.close()
                val extractedText = visionText.text
                viewModel.processScannedText(extractedText)
                showReceiptEditDialog(extractedText)
            }
            .addOnFailureListener { e ->
                imageProxy.close()
                Log.e(TAG, "OCR failed", e)
                binding.progressBar.visibility = View.GONE
                binding.captureButton.isEnabled = true
                Toast.makeText(requireContext(), "OCR processing failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showReceiptEditDialog(ocrText: String) {
        binding.progressBar.visibility = View.GONE
        binding.captureButton.isEnabled = true

        val parsed = viewModel.parseReceiptText(ocrText)
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault()).toString()

        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_receipt_edit, null)

        val merchantEdit = dialogView.findViewById<EditText>(R.id.edit_merchant_name)
        val amountEdit = dialogView.findViewById<EditText>(R.id.edit_amount)
        val dateEdit = dialogView.findViewById<EditText>(R.id.edit_date)
        val descriptionEdit = dialogView.findViewById<EditText>(R.id.edit_description)
        val categorySpinner = dialogView.findViewById<Spinner>(R.id.spinner_category)

        merchantEdit.setText(parsed["merchantName"] ?: "")
        amountEdit.setText(parsed["amount"] ?: "")
        dateEdit.setText(parsed["date"]?.ifBlank { today } ?: today)

        val categories = resources.getStringArray(R.array.receipt_categories)
        categorySpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            categories
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Save Receipt")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                viewModel.saveReceipt(
                    merchantName = merchantEdit.text.toString(),
                    amount = amountEdit.text.toString(),
                    date = dateEdit.text.toString(),
                    category = categorySpinner.selectedItem?.toString() ?: "Other",
                    description = descriptionEdit.text.toString()
                )
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::cameraExecutor.isInitialized) {
            cameraExecutor.shutdown()
        }
        _binding = null
    }
}
