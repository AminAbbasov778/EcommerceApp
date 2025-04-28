package com.example.ecommerceapp.di.modules

import com.example.ecommerceapp.domain.interfaces.FirebaseAuthRepository
import com.example.ecommerceapp.domain.usecases.settingsusecases.LogoutUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
object SettingsModule {


    @Provides
    @Singleton
    fun provideLogoutUseCase(firebaseAuthRepository: FirebaseAuthRepository): LogoutUseCase = LogoutUseCase(firebaseAuthRepository)

}