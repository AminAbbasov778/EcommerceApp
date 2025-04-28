package com.example.ecommerceapp.di.modules

import android.content.Context
import com.example.ecommerceapp.domain.interfaces.FirebaseAuthRepository
import com.example.ecommerceapp.domain.interfaces.UserPreferences
import com.example.ecommerceapp.domain.interfaces.UserRepository
import com.example.ecommerceapp.domain.usecases.editprofileusecases.ConvertUriToBase64UseCase
import com.example.ecommerceapp.domain.usecases.editprofileusecases.GetUserEmailUseCase
import com.example.ecommerceapp.domain.usecases.editprofileusecases.GetUsernameUseCase
import com.example.ecommerceapp.domain.usecases.editprofileusecases.UpdateUsernameUseCase
import com.example.ecommerceapp.domain.usecases.editprofileusecases.UpdatePasswordUseCase
import com.example.ecommerceapp.domain.usecases.editprofileusecases.UpdateUserProfileUseCase
import com.example.ecommerceapp.domain.usecases.editprofileusecases.UserProfileModelUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
object EditProfileUseCasesModule {

    @Provides
    @Singleton
    fun provideGetUserEmailUseCase(firebaseAuthRepository: FirebaseAuthRepository): GetUserEmailUseCase = GetUserEmailUseCase(firebaseAuthRepository)

    @Provides
    @Singleton
    fun provideGetUsernameUseCase(userPreferences: UserPreferences): GetUsernameUseCase = GetUsernameUseCase(userPreferences)

    @Provides
    @Singleton
    fun provideSaveUsernameUseCase(userPreferences: UserPreferences):UpdateUsernameUseCase = UpdateUsernameUseCase(userPreferences)

    @Provides
    @Singleton
    fun provideUpdatePasswordUseCase(firebaseAuthRepository: FirebaseAuthRepository):UpdatePasswordUseCase = UpdatePasswordUseCase(firebaseAuthRepository
    )
    @Provides
    @Singleton
    fun provideUpdateUserProfileUseCase(userRepository: UserRepository): UpdateUserProfileUseCase =
        UpdateUserProfileUseCase(userRepository)

    @Provides
    @Singleton
    fun provideUserProfileModelUseCase(convertUriToBase64UseCase: ConvertUriToBase64UseCase): UserProfileModelUseCase =
        UserProfileModelUseCase(convertUriToBase64UseCase)

    @Provides
    @Singleton
    fun provideConvertUriToBase64UseCase(@ApplicationContext context: Context): ConvertUriToBase64UseCase =
        ConvertUriToBase64UseCase(context)


}