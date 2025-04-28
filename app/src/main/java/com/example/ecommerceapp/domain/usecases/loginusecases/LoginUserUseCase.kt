package com.example.ecommerceapp.domain.usecases.loginusecases

import com.example.ecommerceapp.domain.interfaces.FirebaseAuthRepository
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(val firebaseAuthRepository: FirebaseAuthRepository) {

    suspend operator fun invoke(email: String, password: String): Result<AuthResult> = firebaseAuthRepository.loginUser(email, password)


}