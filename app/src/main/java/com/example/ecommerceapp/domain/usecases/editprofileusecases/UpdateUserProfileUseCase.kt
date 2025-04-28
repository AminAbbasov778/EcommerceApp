package com.example.ecommerceapp.domain.usecases.editprofileusecases

import com.example.ecommerceapp.domain.domainstates.UserProfileModel
import com.example.ecommerceapp.domain.interfaces.UserRepository
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(val userRepository: UserRepository) {

    suspend operator fun invoke(userProfileModel: UserProfileModel): Result<Unit> =
        userRepository.uploadProfileData(userProfileModel)

}