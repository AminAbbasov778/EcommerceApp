package com.example.ecommerceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.ecommerceapp.data.local.entity.CartEntity
@Dao
interface InsertProductDao {
    @Insert
    suspend fun insertProduct(product : CartEntity)
}