package com.example.ecommerceapp.domain.usecases.commonusecases

import com.example.ecommerceapp.domain.interfaces.CartDatabaseRepository
import javax.inject.Inject

class UpdateProductCountAndPriceInCartUseCase @Inject constructor(val cartDatabaseRepository: CartDatabaseRepository) {

    suspend operator fun invoke(id :Int,count : Int,price : Double) : Result<Int> = cartDatabaseRepository.updateProductCountAndPrice(id,count,price)
}