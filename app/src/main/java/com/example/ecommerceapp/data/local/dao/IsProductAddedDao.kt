package com.example.ecommerceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
@Dao
interface IsProductAddedDao {
    @Query("SELECT EXISTS(SELECT 1 FROM Cart WHERE id = :id)")
    suspend fun isProductAdded(id: Int): Boolean
}