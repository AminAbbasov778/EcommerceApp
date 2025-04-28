package com.example.ecommerceapp.domain.usecases.commonusecases

import com.example.ecommerceapp.domain.interfaces.UserRepository
import javax.inject.Inject

class RemoveProductFromFavoritesByIdUseCase @Inject constructor(val userRepository: UserRepository) {
    suspend operator fun invoke(id : Int) : Result<Unit> = userRepository.removeProductFromFavoriteById(id)

}