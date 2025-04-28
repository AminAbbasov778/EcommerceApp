package com.example.ecommerceapp.domain.usecases.editprofileusecases

import com.example.ecommerceapp.domain.interfaces.UserPreferences
import javax.inject.Inject

class GetUsernameUseCase @Inject constructor(val userPreferences: UserPreferences) {
    operator fun invoke() : Result<String?> = userPreferences.getUsername()
}