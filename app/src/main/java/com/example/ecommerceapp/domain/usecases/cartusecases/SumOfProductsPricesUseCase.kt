package com.example.ecommerceapp.domain.usecases.cartusecases

import com.example.ecommerceapp.data.local.entity.CartEntity
import javax.inject.Inject

class SumOfProductsPricesUseCase  @Inject constructor(){
    operator fun invoke(products : List<CartEntity>):Double{
        return  products.sumOf { it.price }
    }
}