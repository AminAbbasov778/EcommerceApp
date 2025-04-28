package com.example.ecommerceapp.presentation.uiutils

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

object CameraPermissionUtils {

    fun checkAndRequestCameraPermission(
        fragment: Fragment,
        requestCode: Int,
        onGranted: () -> Unit,
        onDenied: () -> Unit
    ) {
        if (ContextCompat.checkSelfPermission(
                fragment.requireContext(),
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                fragment.requireActivity(),
                arrayOf(android.Manifest.permission.CAMERA),
                requestCode
            )
        } else {
            onGranted()
        }
    }

    fun handlePermissionResult(
        requestCode: Int,
        grantResults: IntArray,
        expectedRequestCode: Int,
        onGranted: () -> Unit,
        onDenied: () -> Unit
    ) {
        if (requestCode == expectedRequestCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onGranted()
            } else {
                onDenied()
            }
        }
    }
}
