package com.example.ecommerceapp.domain.interfaces

import com.example.ecommerceapp.domain.domainstates.UserProfileModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun uploadProfileData(userProfileModel: UserProfileModel): Result<Unit>
    suspend fun getProfileData(): Flow<Result<UserProfileModel>>
    suspend fun addProductToFavoriteById(id: Int): Result<Unit>
    suspend fun removeProductFromFavoriteById(id: Int): Result<Unit>
    suspend fun getFavoriteProductsIds(): Flow<Result<List<Int>>>

}