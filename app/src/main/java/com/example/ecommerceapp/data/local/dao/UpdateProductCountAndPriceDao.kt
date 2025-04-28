package com.example.ecommerceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface UpdateProductCountAndPriceDao {
    @Query("UPDATE cart SET quantity = :quantity, price = :price WHERE id = :id")
    suspend fun updateProductCountById(id: Int, quantity: Int,price: Double):Int

}