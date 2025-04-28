package com.example.ecommerceapp.di.modules

import com.example.ecommerceapp.domain.interfaces.BannerRepository
import com.example.ecommerceapp.domain.interfaces.CategoryRepository
import com.example.ecommerceapp.domain.interfaces.DetailRepository
import com.example.ecommerceapp.domain.interfaces.ProductRepository
import com.example.ecommerceapp.domain.usecases.commonusecases.AddColorAndSizeToProductsListUseCase
import com.example.ecommerceapp.domain.usecases.homeusecases.CombineCategoriesWithImagesUseCase
import com.example.ecommerceapp.domain.usecases.homeusecases.GetBannersUseCase
import com.example.ecommerceapp.domain.usecases.homeusecases.GetCategoriesUseCase
import com.example.ecommerceapp.domain.usecases.homeusecases.GetProductsUseCase
import com.example.ecommerceapp.domain.usecases.homeusecases.MarkFavoritesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
object HomeUseCasesModule {



    @Provides
    @Singleton
    fun provideGetBannersUseCase(bannerRepository: BannerRepository): GetBannersUseCase = GetBannersUseCase(bannerRepository)



    @Provides
    @Singleton
    fun provideGetCategoryUseCase(categoryRepository: CategoryRepository): GetCategoriesUseCase = GetCategoriesUseCase(categoryRepository)


    @Provides
    @Singleton
    fun provideCombineCategoriesWithImagesUseCase(categoriesUseCase: GetCategoriesUseCase,categoryRepository: CategoryRepository): CombineCategoriesWithImagesUseCase = CombineCategoriesWithImagesUseCase(categoriesUseCase,categoryRepository)



    @Provides
    @Singleton
    fun provideGetProductsUseCase(addColorAndSizeToProductsListUseCase: AddColorAndSizeToProductsListUseCase, productRepository: ProductRepository):GetProductsUseCase = GetProductsUseCase(addColorAndSizeToProductsListUseCase,productRepository)

    @Provides
    @Singleton
    fun provideMarkFavoritesUseCase():MarkFavoritesUseCase = MarkFavoritesUseCase()



}