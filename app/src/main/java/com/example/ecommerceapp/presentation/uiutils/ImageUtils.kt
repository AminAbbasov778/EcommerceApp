package com.example.ecommerceapp.presentation.uiutils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

object ImageUtils {
    fun base64ToBitmap(base64Str: String?): Bitmap? {
        base64Str?.let {
            val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        }

        return null

    }
}