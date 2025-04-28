package com.example.ecommerceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.ecommerceapp.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface ReadProductsDao {
    @Query("SELECT * FROM Cart")
    fun getProducts(): Flow<List<CartEntity>>

    @Query("SELECT * FROM cart WHERE id = :productId")
     fun getProductById(productId: Int): Flow<CartEntity>
}