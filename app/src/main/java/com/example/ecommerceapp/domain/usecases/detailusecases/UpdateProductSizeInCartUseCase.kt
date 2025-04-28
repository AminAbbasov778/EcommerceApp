package com.example.ecommerceapp.domain.usecases.detailusecases

import com.example.ecommerceapp.domain.interfaces.CartDatabaseRepository
import javax.inject.Inject

class UpdateProductSizeInCartUseCase @Inject constructor(val cartDatabaseRepository: CartDatabaseRepository) {
    suspend operator fun invoke(id : Int,size : String,sizePosition : Int) : Result<Int> = cartDatabaseRepository.updateProductSize(id,size,sizePosition)
}