package com.example.ecommerceapp.domain.usecases.cartusecases

import com.example.ecommerceapp.data.local.entity.CartEntity

class ReverseCartProductsListUseCase {

    operator fun invoke(products :List<CartEntity>):List<CartEntity> {
        return products.reversed()
    }
}