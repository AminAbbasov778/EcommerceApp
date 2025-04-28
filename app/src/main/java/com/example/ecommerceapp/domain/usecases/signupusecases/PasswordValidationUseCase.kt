package com.example.ecommerceapp.domain.usecases.signupusecases

import com.example.ecommerceapp.domain.domainstates.ValidationState
import com.example.ecommerceapp.domain.usecases.commonusecases.ShortPasswordValidationUseCase
import javax.inject.Inject

class PasswordValidationUseCase @Inject constructor(
    val shortPasswordValidationUseCase: ShortPasswordValidationUseCase,
    val digitValidationUseCase: DigitValidationUseCase,
    val lowerCaseValidationUseCase: LowerCaseValidationUseCase,
    val upperCaseValidationUseCase: UpperCaseValidationUseCase,
    val specialCharValidationUseCase: SpecialCharValidationUseCase,

    ) {

    operator fun invoke(
        password: String,
    ): ValidationState {


        val shortResult = shortPasswordValidationUseCase(password)
        if (shortResult is ValidationState.Error) return shortResult

        val digitResult = digitValidationUseCase(password)
        if (digitResult is ValidationState.Error) return digitResult

        val lowerResult = lowerCaseValidationUseCase(password)
        if (lowerResult is ValidationState.Error) return lowerResult

        val upperResult = upperCaseValidationUseCase(password)
        if (upperResult is ValidationState.Error) return upperResult

        val specialResult = specialCharValidationUseCase(password)
        if (specialResult is ValidationState.Error) return specialResult

        return ValidationState.Success


    }
}