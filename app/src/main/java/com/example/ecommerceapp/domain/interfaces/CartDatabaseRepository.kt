package com.example.ecommerceapp.domain.interfaces

import com.example.ecommerceapp.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

interface CartDatabaseRepository {

   suspend fun readProduct(): Flow<Result<List<CartEntity>>>
    suspend fun insertProduct(product: CartEntity) :  Result<Unit>

    suspend fun deleteProduct(id : Int) : Result<Unit>

    suspend fun isProductAdded(id: Int): Result<Boolean>

    suspend fun updateProductSize(id: Int,newSize: String,sizePosition : Int) : Result<Int>
    suspend fun updateProductColor(id: Int,newColor: String,colorPosition : Int) : Result<Int>


 suspend fun updateProductCountAndPrice(id :Int, count : Int,price : Double): Result<Int>
 suspend fun getProductById(productId: Int): Flow<Result<CartEntity>>
}