package com.example.ecommerceapp.presentation.uiutils

import android.app.AlertDialog
import androidx.fragment.app.Fragment

object DialogUtils {

    fun showImagePickerDialog(
        fragment: Fragment,
        options: Array<String>,
        onCameraClick: () -> Unit,
        onGalleryClick: () -> Unit
    ) {
        AlertDialog.Builder(fragment.requireContext())
            .setTitle("Add an image")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> onCameraClick()
                    1 -> onGalleryClick()
                }
            }
            .show()
    }
}
