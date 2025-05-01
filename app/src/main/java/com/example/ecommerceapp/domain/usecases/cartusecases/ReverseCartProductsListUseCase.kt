package com.example.ecommerceapp.domain.usecases.cartusecases

import com.example.ecommerceapp.domain.models.cart.CartModel
import javax.inject.Inject

class ReverseCartProductsListUseCase  @Inject constructor() {

    operator fun invoke(products :List<CartModel>):List<CartModel> {
        return products.reversed()
    }
}