package com.example.ecommerceapp.domain.usecases.commonusecases

import com.example.ecommerceapp.domain.interfaces.CartDatabaseRepository

class DeleteProductFromCartUseCase(val cartDatabaseRepository: CartDatabaseRepository) {

    suspend operator fun invoke(id:Int): Result<Unit>
    =  cartDatabaseRepository.deleteProduct(id)


}