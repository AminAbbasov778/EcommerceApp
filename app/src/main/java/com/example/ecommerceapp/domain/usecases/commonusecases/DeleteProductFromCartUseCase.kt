package com.example.ecommerceapp.domain.usecases.commonusecases

import com.example.ecommerceapp.domain.interfaces.CartDatabaseRepository
import javax.inject.Inject

class DeleteProductFromCartUseCase  @Inject constructor(val cartDatabaseRepository: CartDatabaseRepository) {

    suspend operator fun invoke(id:Int): Result<Unit>
    =  cartDatabaseRepository.deleteProduct(id)


}