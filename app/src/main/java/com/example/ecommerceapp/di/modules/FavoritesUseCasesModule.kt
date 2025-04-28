package com.example.ecommerceapp.di.modules

import com.example.ecommerceapp.domain.interfaces.UserRepository
import com.example.ecommerceapp.domain.usecases.favoritesusecases.AddProductToFavoritesByIdUseCase
import com.example.ecommerceapp.domain.usecases.commonusecases.RemoveProductFromFavoritesByIdUseCase
import com.example.ecommerceapp.domain.usecases.favoritesusecases.GetFavoriteProductsIdsUseCase
import com.example.ecommerceapp.domain.usecases.favoritesusecases.ReverseFavoriteProductsIdsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoritesUseCasesModule {

      @Provides
    @Singleton
    fun provideAddProductToFavoritesByIdUseCase(userRepository: UserRepository) : AddProductToFavoritesByIdUseCase = AddProductToFavoritesByIdUseCase(userRepository)

    @Provides
    @Singleton
    fun provideGetFavoriteProductsIdsUseCase(userRepository: UserRepository,reverseFavoriteProductsIdsUseCase: ReverseFavoriteProductsIdsUseCase):GetFavoriteProductsIdsUseCase = GetFavoriteProductsIdsUseCase(userRepository,reverseFavoriteProductsIdsUseCase)


    @Provides
    @Singleton
    fun provideReverseFavoriteProductsIdsUseCase():ReverseFavoriteProductsIdsUseCase = ReverseFavoriteProductsIdsUseCase()
}