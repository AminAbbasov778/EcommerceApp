package com.example.ecommerceapp.domain.usecases.commonusecases

import android.net.Uri
import com.example.ecommerceapp.domain.interfaces.CameraRepository
import javax.inject.Inject


class CapturePhotoUseCase @Inject constructor(val cameraRepository: CameraRepository) {
    operator fun invoke(): Uri = cameraRepository.createImgUri()

}