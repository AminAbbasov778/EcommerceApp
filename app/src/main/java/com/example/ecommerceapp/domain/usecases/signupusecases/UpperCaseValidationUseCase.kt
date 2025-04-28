package com.example.ecommerceapp.domain.usecases.signupusecases

import com.example.ecommerceapp.domain.domainstates.ValidationState

class UpperCaseValidationUseCase {

    operator fun invoke(password : String): ValidationState{
        val regex = ".*[A-Z].*"
        if (password.matches(regex.toRegex())) {
            return  ValidationState.Success
        }else{
            return  ValidationState.Error.NoUpperCase
        }
    }
}