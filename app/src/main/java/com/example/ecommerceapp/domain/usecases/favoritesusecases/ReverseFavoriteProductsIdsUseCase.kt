package com.example.ecommerceapp.domain.usecases.favoritesusecases

import javax.inject.Inject

class ReverseFavoriteProductsIdsUseCase  @Inject constructor() {
    operator fun invoke(idsList : List<Int>) : List<Int> = idsList.reversed()
}