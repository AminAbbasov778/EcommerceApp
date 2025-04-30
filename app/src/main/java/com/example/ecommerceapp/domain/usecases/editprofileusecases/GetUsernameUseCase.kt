package com.example.ecommerceapp.domain.usecases.editprofileusecases

import com.example.ecommerceapp.domain.interfaces.UserPreferencesRepository
import javax.inject.Inject

class GetUsernameUseCase @Inject constructor(val userPreferencesRepository: UserPreferencesRepository) {
    operator fun invoke() : Result<String?> = userPreferencesRepository.getUsername()
}