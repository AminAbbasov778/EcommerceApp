package com.example.ecommerceapp.domain.usecases.editprofileusecases

import com.example.ecommerceapp.domain.interfaces.FirebaseAuthRepository
import javax.inject.Inject

class GetUserEmailUseCase @Inject constructor(val firebaseAuthRepository: FirebaseAuthRepository) {
    operator fun invoke() : Result<String?> = firebaseAuthRepository.getCurrentUserEmail()
}