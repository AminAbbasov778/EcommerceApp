package com.example.ecommerceapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ecommerceapp.data.local.dao.DeleteProductDao
import com.example.ecommerceapp.data.local.dao.InsertProductDao
import com.example.ecommerceapp.data.local.dao.IsProductAddedDao
import com.example.ecommerceapp.data.local.dao.ReadProductsDao
import com.example.ecommerceapp.data.local.dao.UpdateProductCountAndPriceDao
import com.example.ecommerceapp.data.local.dao.UpdateProductDetailDao
import com.example.ecommerceapp.data.local.entity.CartEntity

@Database(entities = [CartEntity::class], version = 3, exportSchema = false)
abstract class CartDatabase : RoomDatabase(){

    abstract fun getInsertProductDao(): InsertProductDao
    abstract fun getDeleteProductDao():DeleteProductDao
    abstract fun getIsProductAddedDao(): IsProductAddedDao
    abstract fun getReadProductsDao(): ReadProductsDao
    abstract fun getUpdateProductDetailDao(): UpdateProductDetailDao
    abstract fun getUpdateProductCountAndPriceDao():UpdateProductCountAndPriceDao
}