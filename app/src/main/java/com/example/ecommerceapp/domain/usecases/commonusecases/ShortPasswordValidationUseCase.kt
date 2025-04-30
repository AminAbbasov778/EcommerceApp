package com.example.ecommerceapp.domain.usecases.commonusecases

import com.example.ecommerceapp.domain.domainstates.ValidationState
import javax.inject.Inject

class ShortPasswordValidationUseCase  @Inject constructor() {

    operator fun invoke(password: String): ValidationState {
        if (password.length >= 8) {
            return ValidationState.Success
        } else {
            return ValidationState.Error.ShortPassword
        }
    }
}