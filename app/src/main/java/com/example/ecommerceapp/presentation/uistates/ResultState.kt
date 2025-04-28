package com.example.ecommerceapp.presentation.uistates

sealed class ResultState<out T> {
        data class Success<T>(val data : T): ResultState<T>()
        data class Error(val message : Int) : ResultState<Nothing>()
}
