package com.example.ecommerceapp.di.modules

import com.example.ecommerceapp.domain.interfaces.ProductRepository
import com.example.ecommerceapp.domain.usecases.commonusecases.AddColorAndSizeToProductsListUseCase
import com.example.ecommerceapp.domain.usecases.homeusecases.GetProductsUseCase
import com.example.ecommerceapp.domain.usecases.searchresultusecases.FilteredProductsByQueryUseCase
import com.example.ecommerceapp.domain.usecases.searchresultusecases.GetProductsByCategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent ::class)
object SearchResultUseCasesModule {

    @Provides
    @Singleton
    fun provideFilteredProductsByQueryUseCase(getProductsUseCase: GetProductsUseCase):FilteredProductsByQueryUseCase = FilteredProductsByQueryUseCase(getProductsUseCase)


    @Provides
    @Singleton
    fun provideGetProductsByCategoryUseCase(productRepository: ProductRepository,addColorAndSizeToProductsListUseCase: AddColorAndSizeToProductsListUseCase): GetProductsByCategoryUseCase = GetProductsByCategoryUseCase(productRepository,addColorAndSizeToProductsListUseCase)
}