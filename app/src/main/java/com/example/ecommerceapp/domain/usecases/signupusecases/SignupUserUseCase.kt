package com.example.ecommerceapp.domain.usecases.signupusecases

import com.example.ecommerceapp.domain.interfaces.FirebaseAuthRepository
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

class SignupUserUseCase @Inject constructor(val firebaseAuthRepository: FirebaseAuthRepository) {

    suspend operator fun invoke(email: String, password: String): Result<AuthResult> = firebaseAuthRepository.signupUser(email, password)



}