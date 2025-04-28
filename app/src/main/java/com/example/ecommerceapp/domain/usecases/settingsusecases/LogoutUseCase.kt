package com.example.ecommerceapp.domain.usecases.settingsusecases

import com.example.ecommerceapp.domain.interfaces.FirebaseAuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(val firebaseAuthRepository: FirebaseAuthRepository) {

    operator fun invoke(): Result<Unit> = firebaseAuthRepository.logout()



}