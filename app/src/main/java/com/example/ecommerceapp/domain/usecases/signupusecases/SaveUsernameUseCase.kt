package com.example.ecommerceapp.domain.usecases.signupusecases

import com.example.ecommerceapp.domain.interfaces.UserPreferences
import javax.inject.Inject

class SaveUsernameUseCase @Inject constructor(val userPreferences: UserPreferences) {
    operator fun invoke(username : String) = userPreferences.saveUsername(username)

}