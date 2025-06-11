package com.example.ecommerceapp.domain.usecases.commonusecases

import com.example.ecommerceapp.domain.interfaces.FirebaseAuthRepository
import javax.inject.Inject

class GetCurrentUserIdUseCase @Inject constructor(val authRepository: FirebaseAuthRepository) {
    operator fun invoke(): String? = authRepository.getCurrentUserId()
}