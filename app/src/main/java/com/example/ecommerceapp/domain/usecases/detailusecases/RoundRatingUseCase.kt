package com.example.ecommerceapp.domain.usecases.detailusecases

import javax.inject.Inject

class RoundRatingUseCase  @Inject constructor(){
    operator fun invoke(rating : Double): Int{
        return Math.round(rating).toInt()
    }
}