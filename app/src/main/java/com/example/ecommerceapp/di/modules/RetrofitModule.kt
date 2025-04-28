package com.example.ecommerceapp.di.modules

import com.example.ecommerceapp.data.remote.RequestService
import com.example.ecommerceapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
object RetrofitModule {


    @Provides
    @Singleton
    fun provideBaseUrl(): String = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl : String): Retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): RequestService = retrofit.create(RequestService::class.java)


}