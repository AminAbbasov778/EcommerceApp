package com.example.ecommerceapp.domain.usecases.signupusecases

import com.example.ecommerceapp.domain.domainstates.ValidationState

class SpecialCharValidationUseCase {

    operator fun invoke(password : String): ValidationState{
        val regex = ".*[@#\$%^&+=].*"
        if (password.matches(regex.toRegex())) {

            return ValidationState.Success
        }
        else{
          return  ValidationState.Error.NoSpecialChar
        }
    }
}