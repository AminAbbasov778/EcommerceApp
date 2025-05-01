package com.example.ecommerceapp.domain.usecases.settingsusecases

import com.example.ecommerceapp.domain.interfaces.SettingsRepository
import com.example.ecommerceapp.domain.models.SettingsModel
import com.example.ecommerceapp.presentation.mappers.toDomain
import javax.inject.Inject

class GetAccountSettingsItemsUseCase @Inject constructor(val settingsRepository: SettingsRepository) {
    operator fun invoke(): List<SettingsModel> = settingsRepository.getAccountSettingsItems()
}