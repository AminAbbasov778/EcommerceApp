package com.example.ecommerceapp.domain.usecases.cartusecases

import com.example.ecommerceapp.domain.interfaces.CartDatabaseRepository
import com.example.ecommerceapp.domain.models.cart.CartModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsFromDbUseCase @Inject constructor(val cartDatabaseRepository: CartDatabaseRepository )  {

    suspend  operator  fun invoke(): Flow<Result<List<CartModel>>>{
      return  cartDatabaseRepository.readProduct()
    }
}