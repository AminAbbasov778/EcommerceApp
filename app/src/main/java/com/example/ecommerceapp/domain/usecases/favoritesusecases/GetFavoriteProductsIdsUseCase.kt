package com.example.ecommerceapp.domain.usecases.favoritesusecases

import android.util.Log
import com.example.ecommerceapp.domain.interfaces.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteProductsIdsUseCase @Inject constructor(
    val userRepository: UserRepository,
    val reverseFavoriteProductsIdsUseCase: ReverseFavoriteProductsIdsUseCase
) {
    suspend operator fun invoke(): Flow<Result<List<Int>>> {
        return userRepository.getFavoriteProductsIds().map { result ->
            if (result.isSuccess) {
                val reversedIds = reverseFavoriteProductsIdsUseCase(result.getOrNull() ?: emptyList())
                Result.success(reversedIds)
            } else {
                result
            }
        }
    }
}
