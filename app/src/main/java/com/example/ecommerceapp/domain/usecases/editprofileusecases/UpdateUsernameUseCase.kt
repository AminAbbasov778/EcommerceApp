package com.example.ecommerceapp.domain.usecases.editprofileusecases

import com.example.ecommerceapp.domain.interfaces.UserPreferencesRepository
import javax.inject.Inject

class UpdateUsernameUseCase @Inject constructor(val userPreferencesRepository: UserPreferencesRepository) {
    operator fun invoke(username : String): Result<Unit> = userPreferencesRepository.saveUsername(username)
}