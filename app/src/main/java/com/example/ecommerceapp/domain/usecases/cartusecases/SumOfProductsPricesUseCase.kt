package com.example.ecommerceapp.domain.usecases.cartusecases

import com.example.ecommerceapp.domain.models.cart.CartModel
import javax.inject.Inject

class SumOfProductsPricesUseCase  @Inject constructor(){
    operator fun invoke(products : List<CartModel>):Double{
        return  products.sumOf { it.price }
    }
}