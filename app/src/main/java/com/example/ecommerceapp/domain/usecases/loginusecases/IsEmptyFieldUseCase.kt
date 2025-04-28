package com.example.ecommerceapp.domain.usecases.loginusecases

import com.example.ecommerceapp.domain.domainstates.ValidationState

class IsEmptyFieldUseCase {

    operator fun invoke(email: String, password : String): ValidationState {
        if(email.isEmpty() || password.isEmpty()){
            return  ValidationState.Error.EmptyField
        }
        else{
            return ValidationState.Success
        }

    }
}