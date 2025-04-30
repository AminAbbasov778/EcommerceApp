package com.example.ecommerceapp.domain.usecases.settingsusecases

import com.example.ecommerceapp.domain.interfaces.SettingsRepository
import javax.inject.Inject

class GetPersonalSettingsItemsUseCase @Inject constructor(val settingsRepository: SettingsRepository) {
    operator fun invoke() : List<Int> = settingsRepository.getPersonalSettingsItems()
}