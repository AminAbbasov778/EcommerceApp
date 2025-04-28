package com.example.ecommerceapp.domain.usecases.loginusecases

import com.example.ecommerceapp.domain.domainstates.ValidationState
import com.example.ecommerceapp.domain.usecases.commonusecases.EmailValidationUseCase
import com.example.ecommerceapp.domain.usecases.commonusecases.ShortPasswordValidationUseCase
import javax.inject.Inject

class LoginValidationUseCase @Inject constructor(
    val isEmptyFieldUseCase: IsEmptyFieldUseCase,
    val emailValidationUseCase: EmailValidationUseCase,
    val shortPasswordValidationUseCase: ShortPasswordValidationUseCase,
) {

    operator fun invoke(email: String, password: String): ValidationState {
        val isEmpty = isEmptyFieldUseCase(email, password)
        if (isEmpty is ValidationState.Error.EmptyField) return isEmpty

         val isInvalidEmail = emailValidationUseCase(email)
        if (isInvalidEmail is ValidationState.Error.InvalidEmail) return isInvalidEmail

        val isShortPassword = shortPasswordValidationUseCase(password)
        return isShortPassword
    }
}