package com.example.ecommerceapp.domain.usecases.signupusecases

import com.example.ecommerceapp.domain.domainstates.ValidationState
import com.example.ecommerceapp.domain.usecases.commonusecases.EmailValidationUseCase
import javax.inject.Inject

class SignupValidationUseCase @Inject constructor(
    val isEmptySignupFieldUseCase: IsEmptySignupFieldUseCase,
    val emailValidationUseCase: EmailValidationUseCase,
    val passwordValidationUseCase: PasswordValidationUseCase
    ) {

    operator fun invoke(
        userName: String,
        email: String,
        password: String,
    ): ValidationState {


        var isEmpty = isEmptySignupFieldUseCase(userName, email, password)
        if (isEmpty is ValidationState.Error) return isEmpty

        var isValidEmail = emailValidationUseCase(email)
        if (isValidEmail is ValidationState.Error) return isValidEmail

        var isValidPassword = passwordValidationUseCase(password)
        return isValidPassword


    }


}