package com.example.ecommerceapp.di.modules

import com.example.ecommerceapp.domain.interfaces.CameraRepository
import com.example.ecommerceapp.domain.interfaces.CartDatabaseRepository
import com.example.ecommerceapp.domain.interfaces.DetailRepository
import com.example.ecommerceapp.domain.interfaces.FirebaseAuthRepository
import com.example.ecommerceapp.domain.interfaces.ProductRepository
import com.example.ecommerceapp.domain.interfaces.UserRepository
import com.example.ecommerceapp.domain.usecases.commonusecases.EmailValidationUseCase
import com.example.ecommerceapp.domain.usecases.commonusecases.AddColorAndSizeToProductsListUseCase
import com.example.ecommerceapp.domain.usecases.commonusecases.CapturePhotoUseCase
import com.example.ecommerceapp.domain.usecases.commonusecases.DeleteProductFromCartUseCase
import com.example.ecommerceapp.domain.usecases.commonusecases.GetProductByIdFromApiUseCase
import com.example.ecommerceapp.domain.usecases.commonusecases.GetProfileDataUseCase
import com.example.ecommerceapp.domain.usecases.commonusecases.IsUserLoggedInUseCase
import com.example.ecommerceapp.domain.usecases.commonusecases.RemoveProductFromFavoritesByIdUseCase
import com.example.ecommerceapp.domain.usecases.commonusecases.ShortPasswordValidationUseCase
import com.example.ecommerceapp.domain.usecases.commonusecases.UpdateProductCountAndPriceInCartUseCase
import com.example.ecommerceapp.domain.usecases.detailusecases.AddColorAndSizeToProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
object CommonUseCasesModule {



    @Provides
    @Singleton
    fun provideEmailValidationUseCase(): EmailValidationUseCase = EmailValidationUseCase()

    @Provides
    @Singleton
    fun provideShortPasswordValidationLoginUseCase(): ShortPasswordValidationUseCase  = ShortPasswordValidationUseCase()


    @Provides
    @Singleton
    fun provideIsUserLoggedInUseCase(firebaseAuthRepository: FirebaseAuthRepository):IsUserLoggedInUseCase =IsUserLoggedInUseCase(firebaseAuthRepository)

    @Provides
    @Singleton
    fun provideUpdateProductCountInCartUseCase(cartDatabaseRepository: CartDatabaseRepository) : UpdateProductCountAndPriceInCartUseCase = UpdateProductCountAndPriceInCartUseCase(cartDatabaseRepository)

    @Provides
    @Singleton
    fun provideAddColorAndSizeToProductsUseCase(detailRepository: DetailRepository): AddColorAndSizeToProductsListUseCase  = AddColorAndSizeToProductsListUseCase(detailRepository)


    @Provides
    @Singleton
    fun provideDeleteProductFromCartUseCase(cartDatabaseRepository: CartDatabaseRepository): DeleteProductFromCartUseCase =
        DeleteProductFromCartUseCase(cartDatabaseRepository)

    @Provides
    @Singleton
    fun provideGetProductByIdFromApiUseCase(productRepository: ProductRepository,addColorAndSizeToProductUseCase: AddColorAndSizeToProductUseCase): GetProductByIdFromApiUseCase = GetProductByIdFromApiUseCase(productRepository,addColorAndSizeToProductUseCase)

    @Provides
    @Singleton
    fun provideRemoveProductFromFavoritesByIdUseCase(userRepository: UserRepository):RemoveProductFromFavoritesByIdUseCase = RemoveProductFromFavoritesByIdUseCase(userRepository)

    @Provides
    @Singleton
    fun provideCapturePhotoUseCase(cameraRepository: CameraRepository): CapturePhotoUseCase =
        CapturePhotoUseCase(cameraRepository)

   @Provides
   @Singleton
   fun provideGetProfileDataUseCase(userRepository: UserRepository):GetProfileDataUseCase = GetProfileDataUseCase(userRepository)

}