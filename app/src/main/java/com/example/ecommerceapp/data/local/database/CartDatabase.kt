package com.example.ecommerceapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.ecommerceapp.data.local.converters.Converters
import com.example.ecommerceapp.data.local.dao.DeleteProductDao
import com.example.ecommerceapp.data.local.dao.InsertProductDao
import com.example.ecommerceapp.data.local.dao.IsProductAddedDao
import com.example.ecommerceapp.data.local.dao.ReadProductsDao
import com.example.ecommerceapp.data.local.dao.UpdateProductCountAndPriceDao
import com.example.ecommerceapp.data.local.dao.UpdateProductDetailDao
import com.example.ecommerceapp.data.local.entity.CartEntity

@Database(entities = [CartEntity::class], version = 7, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CartDatabase : RoomDatabase(){

    abstract fun getInsertProductDao(): InsertProductDao
    abstract fun getDeleteProductDao():DeleteProductDao
    abstract fun getIsProductAddedDao(): IsProductAddedDao
    abstract fun getReadProductsDao(): ReadProductsDao
    abstract fun getUpdateProductDetailDao(): UpdateProductDetailDao
    abstract fun getUpdateProductCountAndPriceDao():UpdateProductCountAndPriceDao
}