package com.example.ecommerceapp.domain.usecases.detailusecases

import com.example.ecommerceapp.data.local.dao.IsProductAddedDao
import com.example.ecommerceapp.domain.interfaces.CartDatabaseRepository
import javax.inject.Inject

class IsProductAddedToCartUseCase @Inject constructor(val cartDatabaseRepository: CartDatabaseRepository) {

    suspend operator fun invoke(id: Int): Result<Boolean> =   cartDatabaseRepository.isProductAdded(id)


}