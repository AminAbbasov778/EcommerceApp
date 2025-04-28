package com.example.ecommerceapp.domain.usecases.commonusecases

import com.example.ecommerceapp.domain.interfaces.FirebaseAuthRepository
import javax.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(val firebaseAuthRepository: FirebaseAuthRepository) {

    operator fun invoke(): Boolean  =      firebaseAuthRepository.isUserLoggedIn()

}
