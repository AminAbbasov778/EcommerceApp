package com.example.ecommerceapp.domain.interfaces

import android.net.Uri

interface CameraRepository {
    fun createImgUri(): Uri
}