package com.example.ecommerceapp.domain.usecases.signupusecases

import com.example.ecommerceapp.domain.domainstates.ValidationState

class IsEmptySignupFieldUseCase {
  operator fun invoke(username: String, email: String, password : String): ValidationState {
        if(username.isEmpty() || email.isEmpty() || password.isEmpty()){
          return  ValidationState.Error.EmptyField
        }
        else{
            return ValidationState.Success
        }

    }
}