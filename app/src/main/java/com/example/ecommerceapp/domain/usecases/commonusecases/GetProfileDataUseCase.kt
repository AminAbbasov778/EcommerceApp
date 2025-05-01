package com.example.ecommerceapp.domain.usecases.commonusecases

import com.example.ecommerceapp.domain.interfaces.UserRepository
import com.example.ecommerceapp.domain.models.ProfileModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileDataUseCase @Inject constructor(val userRepository: UserRepository) {

    suspend operator fun invoke(): Flow<Result<ProfileModel>> = userRepository.getProfileData()

}