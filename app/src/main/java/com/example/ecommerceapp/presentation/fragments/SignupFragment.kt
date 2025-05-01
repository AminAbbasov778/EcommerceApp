package com.example.ecommerceapp.presentation.fragments

import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentSignupBinding
import com.example.ecommerceapp.presentation.uistates.UiState
import com.example.ecommerceapp.presentation.uiutils.CameraPermissionUtils
import com.example.ecommerceapp.presentation.uiutils.DialogUtils
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.setGone
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.show
import com.example.ecommerceapp.presentation.viewmodels.SignupViewModel
import com.example.ecommerceapp.utils.Constants.CAMERA_PERMISSION_REQUEST_CODE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment() {
    lateinit var binding: FragmentSignupBinding
    val viewModel by viewModels<SignupViewModel>()

    private var imageUri: Uri? = null

    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                imageUri?.let { uri ->
                    binding.profileImg.setImageURI(uri)
                    binding.cameraIcon.setGone()
                }
            }
        }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri = uri
                binding.profileImg.setImageURI(it)
                binding.cameraIcon.setGone()

            }
        }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickOn()
        observe()
    }

    fun clickOn() {
        binding.cancelButton.setOnClickListener() {
            findNavController().popBackStack()
        }
        binding.doneButton.setOnClickListener() {
            var userName = binding.userNameEditText.text.toString().trim()
            var email = binding.emailEditText.text.toString().trim()
            var password = binding.passwordEditText.text.toString().trim()
            viewModel.getSignupValidationResult(userName, email, password)
        }
        binding.visibleIcon.setOnClickListener(){
            clickVisiblePasswordIcon()
        }
        binding.invisibleIcon.setOnClickListener(){
            clickInvisiblePasswordIcon()
        }
        binding.profileImg.setOnClickListener {
            viewModel.getImagePickerOptions()
        }
    }

    fun observe() {
        viewModel.signupValidation.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> successfulValidation(it.data)
                is UiState.Error -> failureValidation(it.message)
                else -> failureValidation(R.string.failure_validation)

            }
        }
        viewModel.signupResult.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> successfulSignup(it.data)
                is UiState.Error -> failureSignup(it.message)
                is UiState.Loading -> binding.loading.show()
            }

        }
        viewModel.imageUri.observe(viewLifecycleOwner) { uri ->
            imageUri = uri
            imageUri?.let {
                takePictureLauncher.launch(it)

            }
        }
        viewModel.imagePickerOptions.observe(viewLifecycleOwner) {
            showImagePickerDialog(it)
        }

    }

    fun successfulValidation(message: Int ) {
        var email = binding.emailEditText.text.toString().trim()
        var password = binding.passwordEditText.text.toString().trim()
        val username = binding.userNameEditText.text.toString().trim()
        binding.errorInfoIcon.setGone()
        binding.errorInfoText.setGone()
        binding.successInfoIcon.show()
        binding.successInfoText.show()
        binding.successInfoText.text = getString(message)
        viewModel.signupUser(email, password,username,imageUri)


    }

    fun failureValidation(message: Int) {
        binding.successInfoIcon.setGone()
        binding.successInfoText.setGone()
        binding.errorInfoIcon.show()
        binding.errorInfoText.show()
        binding.errorInfoText.text =getString(message)
    }


    fun successfulSignup(message: Int) {
        binding.errorInfoIcon.setGone()
        binding.errorInfoText.setGone()
        binding.successInfoIcon.show()
        binding.successInfoText.show()
        binding.loading.setGone()
        binding.successInfoText.text = getString(message)
        findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToLoginFragment())
    }

    fun failureSignup(message: Int) {
        binding.successInfoIcon.setGone()
        binding.successInfoText.setGone()
        binding.errorInfoIcon.show()
        binding.errorInfoText.show()
        binding.loading.setGone()
        binding.errorInfoText.text =getString(message)

    }

    private fun clickInvisiblePasswordIcon() {
        binding.invisibleIcon.setGone()
        binding.visibleIcon.show()
        passwordEdittextVisibleInputType()
    }

    private fun passwordEdittextVisibleInputType() {
        var text = binding.passwordEditText.text

        text?.let {
            binding.passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.passwordEditText.setSelection(it.length)
        }

    }

    private fun clickVisiblePasswordIcon() {
        binding.invisibleIcon.show()
        binding.visibleIcon.setGone()
        passwordEdittextInvisibleInputType()
    }


    private fun passwordEdittextInvisibleInputType() {
        var text = binding.passwordEditText.text
        text?.let {
            binding.passwordEditText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.passwordEditText.setSelection(it.length)
        }

    }

    private fun checkAndRequestCameraPermission() {
        CameraPermissionUtils.checkAndRequestCameraPermission(
            this,
            CAMERA_PERMISSION_REQUEST_CODE,
            onGranted = { viewModel.capturePhoto() },
            onDenied = {
                Toast.makeText(requireContext(), "Camera permission is required", Toast.LENGTH_SHORT).show()
            }
        )

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        CameraPermissionUtils.handlePermissionResult(
            requestCode,
            grantResults,
            CAMERA_PERMISSION_REQUEST_CODE,
            onGranted = { viewModel.capturePhoto() },
            onDenied = {
                Toast.makeText(requireContext(), "Camera permission is required", Toast.LENGTH_SHORT).show()
            }
        )


    }

    private fun showImagePickerDialog(options: Array<String>) {
        DialogUtils.showImagePickerDialog(
            this,
            options = options,
            onCameraClick = { checkAndRequestCameraPermission() },
            onGalleryClick = { pickImageLauncher.launch("image/*") }
        )

    }

}