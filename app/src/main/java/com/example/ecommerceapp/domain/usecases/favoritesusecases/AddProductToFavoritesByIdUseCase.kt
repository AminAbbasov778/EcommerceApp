package com.example.ecommerceapp.domain.usecases.favoritesusecases

import com.example.ecommerceapp.domain.interfaces.UserRepository
import javax.inject.Inject

class AddProductToFavoritesByIdUseCase @Inject constructor(val userRepository: UserRepository) {
    suspend operator fun invoke(id : Int) : Result<Unit> = userRepository.addProductToFavoriteById(id)
}