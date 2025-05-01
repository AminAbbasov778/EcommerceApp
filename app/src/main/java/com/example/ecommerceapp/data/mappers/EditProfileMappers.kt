package com.example.ecommerceapp.data.mappers

import com.example.ecommerceapp.data.model.profile.Profile
import com.example.ecommerceapp.domain.models.ProfileModel


fun ProfileModel.toData(): Profile{
    return Profile(imageBase64,username)
}
fun Profile.toDomain(): ProfileModel{
    return ProfileModel(imageBase64,username)
}