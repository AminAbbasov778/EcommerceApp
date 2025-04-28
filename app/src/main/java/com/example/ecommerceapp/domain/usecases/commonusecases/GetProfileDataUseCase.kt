package com.example.ecommerceapp.domain.usecases.commonusecases

import com.example.ecommerceapp.domain.domainstates.UserProfileModel
import com.example.ecommerceapp.domain.interfaces.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileDataUseCase @Inject constructor(val userRepository: UserRepository) {

    suspend operator fun invoke(): Flow<Result<UserProfileModel>> = userRepository.getProfileData()

}