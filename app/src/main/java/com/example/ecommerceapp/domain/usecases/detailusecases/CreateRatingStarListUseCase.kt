package com.example.ecommerceapp.domain.usecases.detailusecases

import javax.inject.Inject

class CreateRatingStarListUseCase @Inject constructor(val roundRatingUseCase: RoundRatingUseCase)  {
    operator fun invoke(rating : Double): List<Boolean>{
        val intRating = roundRatingUseCase(rating)
        return List(intRating) {true}
    }
}