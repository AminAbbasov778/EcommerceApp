package com.example.ecommerceapp.presentation.mappers

import com.example.ecommerceapp.domain.models.ProfileModel
import com.example.ecommerceapp.presentation.uimodels.NewProfileUiModel

fun NewProfileUiModel.toDomain(): ProfileModel{
    return ProfileModel(imageBase64,username)
}