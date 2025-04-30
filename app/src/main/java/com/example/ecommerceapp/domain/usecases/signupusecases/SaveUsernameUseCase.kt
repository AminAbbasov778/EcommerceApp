package com.example.ecommerceapp.domain.usecases.signupusecases

import com.example.ecommerceapp.domain.interfaces.UserPreferencesRepository
import javax.inject.Inject

class SaveUsernameUseCase @Inject constructor(val userPreferencesRepository: UserPreferencesRepository) {
    operator fun invoke(username : String) = userPreferencesRepository.saveUsername(username)

}