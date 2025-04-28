package com.example.ecommerceapp.domain.usecases.signupusecases

import com.example.ecommerceapp.domain.domainstates.ValidationState

class LowerCaseValidationUseCase {

    operator fun invoke(password : String): ValidationState{
        var regex = ".*[a-z].*"
        if (password.matches(regex.toRegex())){
            return ValidationState.Success
        }else{
            return ValidationState.Error.NoLowerCase
        }
    }
}