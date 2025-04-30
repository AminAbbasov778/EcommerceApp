package com.example.ecommerceapp.domain.usecases.signupusecases

import com.example.ecommerceapp.domain.domainstates.ValidationState
import javax.inject.Inject

class DigitValidationUseCase  @Inject constructor(){

    operator fun invoke(password : String): ValidationState{
        var regex = ".*\\d.*"
        if (password.matches(regex.toRegex())) {
            return ValidationState.Success
        }else{
            return  ValidationState.Error.NoDigit
        }
    }
}