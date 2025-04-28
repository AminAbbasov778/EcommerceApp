package com.example.ecommerceapp.di.modules

import com.example.ecommerceapp.domain.interfaces.FirebaseAuthRepository
import com.example.ecommerceapp.domain.interfaces.UserPreferences
import com.example.ecommerceapp.domain.usecases.commonusecases.EmailValidationUseCase
import com.example.ecommerceapp.domain.usecases.commonusecases.ShortPasswordValidationUseCase
import com.example.ecommerceapp.domain.usecases.loginusecases.IsEmptyFieldUseCase
import com.example.ecommerceapp.domain.usecases.loginusecases.LoginUserUseCase
import com.example.ecommerceapp.domain.usecases.loginusecases.LoginValidationUseCase
import com.example.ecommerceapp.domain.usecases.signupusecases.SaveUsernameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginUseCasesModule {

    @Provides
    @Singleton
    fun provideIsEmptyLoginFieldUseCase(): IsEmptyFieldUseCase  = IsEmptyFieldUseCase()


    @Provides
    @Singleton
    fun provideLoginValidationUseCase(
        isEmptyFieldUseCase: IsEmptyFieldUseCase,
        shortPasswordValidationUseCase: ShortPasswordValidationUseCase,
        emailValidationUseCase: EmailValidationUseCase,
    ): LoginValidationUseCase =
        LoginValidationUseCase(
            isEmptyFieldUseCase,
            emailValidationUseCase,
            shortPasswordValidationUseCase
        )


    @Provides
    @Singleton
    fun provideLoginUserUseCase(firebaseAuthRepository: FirebaseAuthRepository): LoginUserUseCase =
        LoginUserUseCase(firebaseAuthRepository)


}