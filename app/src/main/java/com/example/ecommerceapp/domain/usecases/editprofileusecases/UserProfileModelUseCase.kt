package com.example.ecommerceapp.domain.usecases.editprofileusecases

import android.net.Uri
import com.example.ecommerceapp.domain.domainstates.UserProfileModel
import javax.inject.Inject

class UserProfileModelUseCase @Inject constructor(val convertUriToBase64UseCase: ConvertUriToBase64UseCase) {
    operator fun invoke(
        imageUri: Uri?,
        userName: String,
        ): UserProfileModel {
        val imageBase64 = convertUriToBase64UseCase(imageUri)
        return UserProfileModel(imageBase64 ?: "", userName)
    }
}