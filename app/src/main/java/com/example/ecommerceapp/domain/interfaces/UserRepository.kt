package com.example.ecommerceapp.domain.interfaces

import com.example.ecommerceapp.domain.models.ProfileModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun uploadProfileData(profile: ProfileModel): Result<Unit>
    suspend fun getProfileData(): Flow<Result<ProfileModel>>
    suspend fun addProductToFavoriteById(id: Int): Result<Unit>
    suspend fun removeProductFromFavoriteById(id: Int): Result<Unit>
    suspend fun getFavoriteProductsIds(): Flow<Result<List<Int>>>

}