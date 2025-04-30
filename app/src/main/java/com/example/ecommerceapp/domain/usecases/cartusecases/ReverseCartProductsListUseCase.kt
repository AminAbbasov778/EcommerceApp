package com.example.ecommerceapp.domain.usecases.cartusecases

import com.example.ecommerceapp.data.local.entity.CartEntity
import javax.inject.Inject

class ReverseCartProductsListUseCase  @Inject constructor() {

    operator fun invoke(products :List<CartEntity>):List<CartEntity> {
        return products.reversed()
    }
}