package com.example.ecommerceapp.di.modules

import com.example.ecommerceapp.domain.interfaces.CartDatabaseRepository
import com.example.ecommerceapp.domain.interfaces.DetailRepository
import com.example.ecommerceapp.domain.interfaces.ProductRepository
import com.example.ecommerceapp.domain.usecases.detailusecases.AddColorAndSizeToProductUseCase
import com.example.ecommerceapp.domain.usecases.detailusecases.ConvertProductModelItemToCartEntityUseCase
import com.example.ecommerceapp.domain.usecases.detailusecases.CreateRatingStarListUseCase
import com.example.ecommerceapp.domain.usecases.commonusecases.DeleteProductFromCartUseCase
import com.example.ecommerceapp.domain.usecases.commonusecases.GetProductByIdFromApiUseCase
import com.example.ecommerceapp.domain.usecases.detailusecases.GetProductByIdFromDbUseCase
import com.example.ecommerceapp.domain.usecases.detailusecases.InsertProductToCartUseCase
import com.example.ecommerceapp.domain.usecases.detailusecases.IsColorOrSizeEmptyUseCase
import com.example.ecommerceapp.domain.usecases.detailusecases.IsProductAddedToCartUseCase
import com.example.ecommerceapp.domain.usecases.detailusecases.RoundRatingUseCase
import com.example.ecommerceapp.domain.usecases.detailusecases.UpdateProductColorInCartUseCase
import com.example.ecommerceapp.domain.usecases.detailusecases.UpdateProductSizeInCartUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailUseCasesModule {

    @Provides
    @Singleton
    fun provideRoundRatingUseCase(): RoundRatingUseCase = RoundRatingUseCase()

    @Provides
    @Singleton
    fun provideCreateRatingStarListUseCase(roundRatingUseCase: RoundRatingUseCase): CreateRatingStarListUseCase =
        CreateRatingStarListUseCase(roundRatingUseCase)


    @Provides
    @Singleton
    fun provideConvertProductModelItemToCartEntityUseCase(): ConvertProductModelItemToCartEntityUseCase =
        ConvertProductModelItemToCartEntityUseCase()

    @Provides
    @Singleton
    fun provideInsertProductToCartUseCase(
        cartDatabaseRepository: CartDatabaseRepository,
        convertProductModelItemToCartEntityUseCase: ConvertProductModelItemToCartEntityUseCase,
    ): InsertProductToCartUseCase = InsertProductToCartUseCase(
        cartDatabaseRepository,
        convertProductModelItemToCartEntityUseCase
    )


    @Provides
    @Singleton
    fun provideIsProductAddedToCartUseCase(cartDatabaseRepository: CartDatabaseRepository): IsProductAddedToCartUseCase =
        IsProductAddedToCartUseCase(cartDatabaseRepository)


    @Provides
    @Singleton
    fun provideUpdateProductSizeInCartUseCase(cartDatabaseRepository: CartDatabaseRepository): UpdateProductSizeInCartUseCase =
        UpdateProductSizeInCartUseCase(cartDatabaseRepository)

    @Provides
    @Singleton
    fun provideUpdateProductColorInCartUseCase(cartDatabaseRepository: CartDatabaseRepository): UpdateProductColorInCartUseCase =
        UpdateProductColorInCartUseCase(cartDatabaseRepository)

    @Provides
    @Singleton
    fun provideIsColorOrSizeEmptyUseCase(): IsColorOrSizeEmptyUseCase = IsColorOrSizeEmptyUseCase()

    @Singleton
    @Provides
    fun provideGetProductByIdUseCase(cartDatabaseRepository: CartDatabaseRepository): GetProductByIdFromDbUseCase = GetProductByIdFromDbUseCase(cartDatabaseRepository)


    @Provides
    @Singleton
    fun provideAddColorAndSizeToProductUseCase(detailRepository: DetailRepository) : AddColorAndSizeToProductUseCase = AddColorAndSizeToProductUseCase(detailRepository)

}