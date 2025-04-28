package com.example.ecommerceapp.domain.usecases.cartusecases

import com.example.ecommerceapp.data.local.entity.CartEntity
import com.example.ecommerceapp.domain.interfaces.CartDatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetProductsFromDbUseCase @Inject constructor(val cartDatabaseRepository: CartDatabaseRepository )  {

    suspend  operator  fun invoke(): Flow<Result<List<CartEntity>>>{
      return  cartDatabaseRepository.readProduct()
    }
}