package com.example.ecommerceapp.domain.usecases.detailusecases

import com.example.ecommerceapp.domain.interfaces.CartDatabaseRepository
import com.example.ecommerceapp.domain.models.cart.CartModel
import javax.inject.Inject

class InsertProductToCartUseCase @Inject constructor(
    val cartDatabaseRepository: CartDatabaseRepository,
) {
    suspend operator fun invoke(cartModel: CartModel): Result<Unit> {
        return cartDatabaseRepository.insertProduct(cartModel)
    }
}