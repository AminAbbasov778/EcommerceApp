package com.example.ecommerceapp.domain.usecases.detailusecases

import com.example.ecommerceapp.domain.interfaces.CartDatabaseRepository
import javax.inject.Inject

class UpdateProductColorInCartUseCase @Inject constructor(val cartDatabaseRepository: CartDatabaseRepository) {
    suspend operator fun invoke(id : Int,newColor : String,colorPosition : Int) : Result<Int> = cartDatabaseRepository.updateProductColor(id,newColor,colorPosition)
}