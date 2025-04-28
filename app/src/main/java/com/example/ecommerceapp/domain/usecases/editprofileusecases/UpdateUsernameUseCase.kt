package com.example.ecommerceapp.domain.usecases.editprofileusecases

import com.example.ecommerceapp.domain.interfaces.UserPreferences
import javax.inject.Inject

class UpdateUsernameUseCase @Inject constructor(val userPreferences: UserPreferences) {
    operator fun invoke(username : String): Result<Unit> = userPreferences.saveUsername(username)
}