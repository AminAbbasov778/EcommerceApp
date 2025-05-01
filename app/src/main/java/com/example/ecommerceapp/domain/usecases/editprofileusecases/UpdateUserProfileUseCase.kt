package com.example.ecommerceapp.domain.usecases.editprofileusecases

import com.example.ecommerceapp.domain.interfaces.UserRepository
import com.example.ecommerceapp.presentation.mappers.toDomain
import com.example.ecommerceapp.presentation.uimodels.NewProfileUiModel
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(val userRepository: UserRepository) {

    suspend operator fun invoke(profile: NewProfileUiModel): Result<Unit> =
        userRepository.uploadProfileData(profile.toDomain())

}