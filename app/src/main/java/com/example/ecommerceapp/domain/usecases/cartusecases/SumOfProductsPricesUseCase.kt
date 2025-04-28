package com.example.ecommerceapp.domain.usecases.cartusecases

import com.example.ecommerceapp.data.local.entity.CartEntity

class SumOfProductsPricesUseCase {
    operator fun invoke(products : List<CartEntity>):Double{
        return  products.sumOf { it.price }
    }
}