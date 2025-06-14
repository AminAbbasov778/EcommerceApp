package com.example.ecommerceapp.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.example.ecommerceapp.data.local.dao.DeleteProductDao
import com.example.ecommerceapp.data.local.dao.InsertProductDao
import com.example.ecommerceapp.data.local.dao.IsProductAddedDao
import com.example.ecommerceapp.data.local.dao.ReadProductsDao
import com.example.ecommerceapp.data.local.dao.UpdateProductCountAndPriceDao
import com.example.ecommerceapp.data.local.dao.UpdateProductDetailDao
import com.example.ecommerceapp.data.remote.RequestService
import com.example.ecommerceapp.data.repositories.BannerRepositoryImpl
import com.example.ecommerceapp.data.repositories.CameraRepositoryImpl
import com.example.ecommerceapp.data.repositories.CartDatabaseRepositoryImpl
import com.example.ecommerceapp.data.repositories.CategoryRepositoryImpl
import com.example.ecommerceapp.data.repositories.ChatRepositoryImpl
import com.example.ecommerceapp.data.repositories.DetailRepositoryImpl
import com.example.ecommerceapp.data.repositories.FirebaseAuthRepositoryImpl
import com.example.ecommerceapp.data.repositories.LanguageRepositoryImpl
import com.example.ecommerceapp.data.repositories.ProductRepositoryImpl
import com.example.ecommerceapp.data.repositories.SettingsRepositoryImpl
import com.example.ecommerceapp.data.repositories.UserPreferencesRepositoryImpl
import com.example.ecommerceapp.data.repositories.UserRepositoryImpl
import com.example.ecommerceapp.domain.interfaces.BannerRepository
import com.example.ecommerceapp.domain.interfaces.CameraRepository
import com.example.ecommerceapp.domain.interfaces.CartDatabaseRepository
import com.example.ecommerceapp.domain.interfaces.CategoryRepository
import com.example.ecommerceapp.domain.interfaces.ChatRepository
import com.example.ecommerceapp.domain.interfaces.DetailRepository
import com.example.ecommerceapp.domain.interfaces.FirebaseAuthRepository
import com.example.ecommerceapp.domain.interfaces.LanguageRepository
import com.example.ecommerceapp.domain.interfaces.ProductRepository
import com.example.ecommerceapp.domain.interfaces.SettingsRepository
import com.example.ecommerceapp.domain.interfaces.UserPreferencesRepository
import com.example.ecommerceapp.domain.interfaces.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class )
object RepositoryModule {


    @Provides
    @Singleton
    fun provideFirebaseAuthRepository(firebaseAuth: FirebaseAuth): FirebaseAuthRepository =
        FirebaseAuthRepositoryImpl(firebaseAuth)


    @Provides
    @Singleton
    fun provideBannerRepository(): BannerRepository = BannerRepositoryImpl()


    @Provides
    @Singleton
    fun provideCategoryRepository(requestService: RequestService): CategoryRepository {
        return CategoryRepositoryImpl(requestService)
    }


    @Provides
    @Singleton
    fun provideRetrofitRepository(requestService: RequestService): ProductRepository =
        ProductRepositoryImpl(requestService)


    @Provides
    @Singleton
    fun provideCartDatabaseRepository(
        readProductsDao: ReadProductsDao,
        insertProductDao: InsertProductDao,
        deleteProductDao: DeleteProductDao,
        isProductAddedDao: IsProductAddedDao,
        updateProductDetailDao: UpdateProductDetailDao,
        updateProductCountAndPriceDao: UpdateProductCountAndPriceDao
    ): CartDatabaseRepository = CartDatabaseRepositoryImpl(
        readProductsDao,
        insertProductDao,
        isProductAddedDao,
        deleteProductDao,
        updateProductDetailDao,
        updateProductCountAndPriceDao
    )


    @Provides
    @Singleton
    fun provideDetailRepository(): DetailRepository = DetailRepositoryImpl()

    @Provides
    @Singleton
    fun provideUserRepositoryImpl(
        firebaseAuth: FirebaseAuth,
        firebaseFireStore: FirebaseFirestore
    ): UserRepository = UserRepositoryImpl(firebaseAuth, firebaseFireStore)

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(sharedPreferences: SharedPreferences): UserPreferencesRepository =
        UserPreferencesRepositoryImpl(sharedPreferences)


    @Provides
    @Singleton
    fun provideCameraRepository(@ApplicationContext context: Context): CameraRepository =
        CameraRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideSettingsRepository():SettingsRepository  = SettingsRepositoryImpl()

   @Provides
   @Singleton
   fun provideChatRepositoryImpl(firebaseFireStore: FirebaseFirestore,firebaseAuth: FirebaseAuth):ChatRepository = ChatRepositoryImpl(firebaseFireStore,firebaseAuth)

    @Provides
    @Singleton
    fun provideLanguageRepositoryImpl(sharedPreferences: SharedPreferences):LanguageRepository = LanguageRepositoryImpl(sharedPreferences)

}