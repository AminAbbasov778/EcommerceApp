package com.example.ecommerceapp.domain.usecases.favoritesusecases

class ReverseFavoriteProductsIdsUseCase  {
    operator fun invoke(idsList : List<Int>) : List<Int> = idsList.reversed()
}