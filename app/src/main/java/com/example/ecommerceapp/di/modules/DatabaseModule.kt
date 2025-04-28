package com.example.ecommerceapp.di.modules

import android.content.Context
import androidx.room.Room
import com.example.ecommerceapp.data.local.dao.DeleteProductDao
import com.example.ecommerceapp.data.local.dao.InsertProductDao
import com.example.ecommerceapp.data.local.dao.IsProductAddedDao
import com.example.ecommerceapp.data.local.dao.ReadProductsDao
import com.example.ecommerceapp.data.local.dao.UpdateProductCountAndPriceDao
import com.example.ecommerceapp.data.local.dao.UpdateProductDetailDao
import com.example.ecommerceapp.data.local.database.CartDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context):CartDatabase =
        Room.databaseBuilder(context,CartDatabase ::class.java,"Cart").fallbackToDestructiveMigration().build()


    @Provides
    @Singleton
    fun provideInsertProductDao(cartDatabase: CartDatabase): InsertProductDao = cartDatabase.getInsertProductDao()


    @Provides
    @Singleton
    fun provideDeleteProductDao(cartDatabase: CartDatabase):DeleteProductDao = cartDatabase.getDeleteProductDao()


    @Provides
    @Singleton
    fun provideIsProductAddedDao(cartDatabase: CartDatabase): IsProductAddedDao = cartDatabase.getIsProductAddedDao()


    @Singleton
    @Provides
    fun provideReadProductDao(cartDatabase: CartDatabase):ReadProductsDao = cartDatabase.getReadProductsDao()



    @Provides
    @Singleton
    fun provideUpdateProductDetailDao(cartDatabase: CartDatabase): UpdateProductDetailDao = cartDatabase.getUpdateProductDetailDao()

    @Provides
    @Singleton
    fun provideUpdateProductCountDao(cartDatabase: CartDatabase): UpdateProductCountAndPriceDao = cartDatabase.getUpdateProductCountAndPriceDao()
}