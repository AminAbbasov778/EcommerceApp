package com.example.ecommerceapp.domain.domainstates

sealed class ValidationState {
    object Success : ValidationState()
    sealed class Error : ValidationState(){
        object EmptyField : Error()
        object InvalidEmail : Error()
        object ShortPassword : Error()
        object NoDigit : Error()
        object NoUpperCase : Error()
        object NoLowerCase : Error()
        object NoSpecialChar : Error()

    }
}