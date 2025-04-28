package com.example.ecommerceapp.domain.usecases.detailusecases

class RoundRatingUseCase {
    operator fun invoke(rating : Double): Int{
        return Math.round(rating).toInt()
    }
}