package com.example.ecommerceapp.domain.usecases.detailusecases

import com.example.ecommerceapp.domain.interfaces.CartDatabaseRepository
import com.example.ecommerceapp.domain.models.cart.CartModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductByIdFromDbUseCase @Inject constructor(val cartDatabaseRepository: CartDatabaseRepository) {
    suspend operator fun invoke(productId : Int) : Flow<Result<CartModel>> = cartDatabaseRepository.getProductById(productId)
}