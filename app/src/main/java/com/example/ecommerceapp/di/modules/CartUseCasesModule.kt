package com.example.ecommerceapp.di.modules

import com.example.ecommerceapp.domain.interfaces.CartDatabaseRepository
import com.example.ecommerceapp.domain.usecases.cartusecases.ConvertCartEntityToProductModelUseCase
import com.example.ecommerceapp.domain.usecases.cartusecases.GetProductsFromDbUseCase
import com.example.ecommerceapp.domain.usecases.cartusecases.ReverseCartProductsListUseCase
import com.example.ecommerceapp.domain.usecases.cartusecases.SumOfProductsPricesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartUseCasesModule {


    @Provides
    @Singleton
    fun provideConvertCartEntityToProductModelUseCase():ConvertCartEntityToProductModelUseCase = ConvertCartEntityToProductModelUseCase()

    @Provides
    @Singleton
    fun provideGetProductsFromDbUseCase(cartDatabaseRepository: CartDatabaseRepository): GetProductsFromDbUseCase = GetProductsFromDbUseCase(cartDatabaseRepository)

    @Provides
    @Singleton
    fun provideSumOfProductsPricesUseCase(): SumOfProductsPricesUseCase = SumOfProductsPricesUseCase()

    @Provides
    @Singleton
    fun provideReverseCartProductsListUseCase():ReverseCartProductsListUseCase = ReverseCartProductsListUseCase()


}