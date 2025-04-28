package com.example.ecommerceapp.domain.usecases.editprofileusecases

import com.example.ecommerceapp.domain.interfaces.FirebaseAuthRepository
import javax.inject.Inject

class UpdatePasswordUseCase @Inject constructor(val firebaseAuthRepository: FirebaseAuthRepository) {
    suspend operator fun invoke(currentPassword : String,newPassword : String): Result<Unit> = firebaseAuthRepository.updatePassword(currentPassword,newPassword)
}