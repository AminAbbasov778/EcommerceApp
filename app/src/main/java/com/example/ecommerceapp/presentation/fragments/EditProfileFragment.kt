package com.example.ecommerceapp.presentation.fragments

import android.graphics.Bitmap
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
import com.example.ecommerceapp.databinding.FragmentEditProfileBinding
import com.example.ecommerceapp.presentation.uistates.ResultState
import com.example.ecommerceapp.presentation.uistates.UiState
import com.example.ecommerceapp.presentation.uiutils.BitmapUtils
import com.example.ecommerceapp.presentation.uiutils.CameraPermissionUtils
import com.example.ecommerceapp.presentation.uiutils.DialogUtils
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.setGone
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.show
import com.example.ecommerceapp.presentation.viewmodels.EditProfileViewModel
import com.example.ecommerceapp.utils.Constants.CAMERA_PERMISSION_REQUEST_CODE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    lateinit var binding : FragmentEditProfileBinding
    val viewModel by viewModels<EditProfileViewModel>()

    private var imageUri: Uri? = null
    private var imageBitmap : Bitmap? = null

    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                imageUri?.let { uri ->
                    binding.profileImg.setImageURI(uri)
                }
            }
        }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri = uri
                binding.profileImg.setImageURI(it)
            }
        }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        onClick()

    }

    private fun onClick(){
        binding.saveChangesBtn.setOnClickListener {
            val username = binding.userNameEditText.text.toString()
            val currentPassword = binding.currentPasswordEditText.text.toString()
            val newPassword = binding.newPasswordEditText.text.toString()
            if(imageUri == null) imageBitmap?.let {imageUri = BitmapUtils.bitmapToUri(requireContext(),it)}
            viewModel.updateProfile(username,currentPassword,newPassword,imageUri)

        }
        binding.profileImgCard.setOnClickListener {
            viewModel.getImagePickerOptions()
        }
        binding.editIcon.setOnClickListener {
            viewModel.getImagePickerOptions()
        }

        binding.currentPasInvisibleIcon.setOnClickListener(){
            clickCurrentInvisiblePasswordIcon()
        }
        binding.currentPasVisibleIcon.setOnClickListener(){
           clickCurrentVisiblePasswordIcon()
        }
        binding.newPasVisibleIcon.setOnClickListener(){
            clickNewVisiblePasswordIcon()
        }
        binding.newPasInnvisibleIcon.setOnClickListener {
            clickNewInvisiblePasswordIcon()
        }

    }
    private fun currentPasswordEdittextInvisibleInputType() {
        var text = binding.currentPasswordEditText.text
        text?.let {
            binding.currentPasswordEditText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.currentPasswordEditText.setSelection(it.length)
        }

    }
    private fun clickCurrentVisiblePasswordIcon() {
        binding.currentPasInvisibleIcon.show()
        binding.currentPasVisibleIcon.setGone()
        currentPasswordEdittextInvisibleInputType()
    }
    private fun clickCurrentInvisiblePasswordIcon() {
        binding.currentPasInvisibleIcon.setGone()
        binding.currentPasVisibleIcon.show()
        currentPasswordEdittextVisibleInputType()
    }

    private fun currentPasswordEdittextVisibleInputType() {
        var text = binding.currentPasswordEditText.text

        text?.let {
            binding.currentPasswordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.currentPasswordEditText.setSelection(it.length)
        }






    }


    private fun newPasswordEdittextInvisibleInputType() {
        var text = binding.newPasswordEditText.text
        text?.let {
            binding.newPasswordEditText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.newPasswordEditText.setSelection(it.length)
        }

    }
    private fun clickNewVisiblePasswordIcon() {
        binding.newPasInnvisibleIcon.show()
        binding.newPasVisibleIcon.setGone()
        newPasswordEdittextInvisibleInputType()
    }
    private fun clickNewInvisiblePasswordIcon() {
        binding.newPasInnvisibleIcon.setGone()
        binding.newPasVisibleIcon.show()
        newPasswordEdittextVisibleInputType()
    }

    private fun newPasswordEdittextVisibleInputType() {
        var text = binding.newPasswordEditText.text

        text?.let {
            binding.newPasswordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.newPasswordEditText.setSelection(it.length)
        }

    }









    private fun observe() {
        viewModel.imageUri.observe(viewLifecycleOwner) { uri ->
            imageUri = uri
            imageUri?.let {
                takePictureLauncher.launch(it)

            }
        }
        viewModel.imagePickerOptions.observe(viewLifecycleOwner) {
            showImagePickerDialog(it)
        }


        viewModel.email.observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Success -> binding.emailTextView.setText(it.data)
                is ResultState.Error -> Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        viewModel.username.observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Success -> binding.userNameEditText.setText(it.data)
                is ResultState.Error -> Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        viewModel.updateResult.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    binding.loading.setGone()
                    Toast.makeText(context, it.data, Toast.LENGTH_SHORT).show()
                }

                is UiState.Error -> {
                    binding.loading.setGone()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is UiState.Loading -> binding.loading.show()
            }
        }


        viewModel.profileData.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    binding.loading.setGone()
                    imageBitmap = it.data.imageBitmap
                    binding.profileImg.setImageBitmap(it.data.imageBitmap)
                    binding.userNameEditText.setText(it.data.username)

                }

                is UiState.Error -> {
                    binding.loading.setGone()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is UiState.Loading -> binding.loading.show()
            }

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