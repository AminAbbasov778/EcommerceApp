package com.example.ecommerceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
@Dao
interface UpdateProductDetailDao {
    @Query("UPDATE cart SET size = :newSize, sizePosition = :sizePosition WHERE id = :productId")
    suspend fun updateProductSize(productId: Int, newSize: String, sizePosition: Int): Int

    @Query("UPDATE cart SET color = :color, colorPosition = :colorPosition WHERE id = :id")
    suspend fun updateProductColorById(id: Int, color: String,colorPosition : Int):Int
}