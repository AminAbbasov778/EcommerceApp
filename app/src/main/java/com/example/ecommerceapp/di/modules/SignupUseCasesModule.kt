package com.example.ecommerceapp.di.modules

import com.example.ecommerceapp.domain.interfaces.FirebaseAuthRepository
import com.example.ecommerceapp.domain.interfaces.UserPreferences
import com.example.ecommerceapp.domain.usecases.commonusecases.EmailValidationUseCase
import com.example.ecommerceapp.domain.usecases.commonusecases.ShortPasswordValidationUseCase
import com.example.ecommerceapp.domain.usecases.signupusecases.DigitValidationUseCase
import com.example.ecommerceapp.domain.usecases.signupusecases.IsEmptySignupFieldUseCase
import com.example.ecommerceapp.domain.usecases.signupusecases.LowerCaseValidationUseCase
import com.example.ecommerceapp.domain.usecases.signupusecases.PasswordValidationUseCase
import com.example.ecommerceapp.domain.usecases.signupusecases.SaveUsernameUseCase
import com.example.ecommerceapp.domain.usecases.signupusecases.SignupUserUseCase
import com.example.ecommerceapp.domain.usecases.signupusecases.SignupValidationUseCase
import com.example.ecommerceapp.domain.usecases.signupusecases.SpecialCharValidationUseCase
import com.example.ecommerceapp.domain.usecases.signupusecases.UpperCaseValidationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SignupUseCasesModule {


    @Provides
    @Singleton
    fun provideIsEmptyUseCase(): IsEmptySignupFieldUseCase = IsEmptySignupFieldUseCase()


    @Provides
    @Singleton
    fun provideLoginValidationUseCase(
        isEmptySignupFieldUseCase: IsEmptySignupFieldUseCase,
        emailValidationUseCase: EmailValidationUseCase,
        passwordValidationUseCase: PasswordValidationUseCase,
    ): SignupValidationUseCase {
        return SignupValidationUseCase(
            isEmptySignupFieldUseCase,
            emailValidationUseCase,
            passwordValidationUseCase
        )
    }

    @Provides
    @Singleton
    fun provideDigitValidationUseCase(): DigitValidationUseCase = DigitValidationUseCase()



    @Provides
    @Singleton
    fun provideLowerCaseValidationUseCase(): LowerCaseValidationUseCase = LowerCaseValidationUseCase()


    @Provides
    @Singleton
    fun providePasswordValidationUseCase(

        shortPasswordValidationUseCase: ShortPasswordValidationUseCase,
        digitValidationUseCase: DigitValidationUseCase,
        lowerCaseValidationUseCase: LowerCaseValidationUseCase,
        upperCaseValidationUseCase: UpperCaseValidationUseCase,
        specialCharValidationUseCase: SpecialCharValidationUseCase,
    ): PasswordValidationUseCase {
        return PasswordValidationUseCase(
            shortPasswordValidationUseCase,
            digitValidationUseCase,
            lowerCaseValidationUseCase,
            upperCaseValidationUseCase,
            specialCharValidationUseCase
        )
    }


    @Provides
    @Singleton
    fun provideSpecialCharValidationUseCase(): SpecialCharValidationUseCase  = SpecialCharValidationUseCase()



    @Provides
    @Singleton
    fun provideUpperCaseValidationUseCase(): UpperCaseValidationUseCase = UpperCaseValidationUseCase()


    @Provides
    @Singleton
    fun provideSignupUserUseCase(firebaseAuthRepository: FirebaseAuthRepository): SignupUserUseCase =  SignupUserUseCase(firebaseAuthRepository)

    @Provides
    @Singleton
    fun provideSaveUsernameUseCase(userPreferences: UserPreferences): SaveUsernameUseCase =
        SaveUsernameUseCase(userPreferences)


}