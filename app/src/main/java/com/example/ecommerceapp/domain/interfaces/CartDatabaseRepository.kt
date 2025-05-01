package com.example.ecommerceapp.domain.interfaces

import com.example.ecommerceapp.domain.models.cart.CartModel
import kotlinx.coroutines.flow.Flow

interface CartDatabaseRepository {

   suspend fun readProduct(): Flow<Result<List<CartModel>>>
    suspend fun insertProduct(product: CartModel) :  Result<Unit>

    suspend fun deleteProduct(id : Int) : Result<Unit>

    suspend fun isProductAdded(id: Int): Result<Boolean>

    suspend fun updateProductSize(id: Int,newSize: String,sizePosition : Int) : Result<Int>
    suspend fun updateProductColor(id: Int,newColor: String,colorPosition : Int) : Result<Int>


 suspend fun updateProductCountAndPrice(id :Int, count : Int,price : Double): Result<Int>
 suspend fun getProductById(productId: Int): Flow<Result<CartModel>>
}