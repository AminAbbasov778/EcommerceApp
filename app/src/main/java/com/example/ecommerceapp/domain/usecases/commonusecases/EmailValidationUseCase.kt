package com.example.ecommerceapp.domain.usecases.commonusecases

import android.util.Patterns
import com.example.ecommerceapp.domain.domainstates.ValidationState
import javax.inject.Inject

class EmailValidationUseCase  @Inject constructor(){

    operator fun invoke(email :String):ValidationState {
        var isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if(isValidEmail){
          return  ValidationState.Success
        }
        else{
            return  ValidationState.Error.InvalidEmail
        }
    }
}