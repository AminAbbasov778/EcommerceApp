package com.example.ecommerceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
@Dao
interface DeleteProductDao {
    @Query("DELETE FROM Cart WHERE id = :id")
    suspend  fun deleteProduct(id : Int)
}