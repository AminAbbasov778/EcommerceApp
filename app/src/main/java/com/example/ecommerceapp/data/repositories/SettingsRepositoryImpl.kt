package com.example.ecommerceapp.data.repositories

import com.example.ecommerceapp.R
import com.example.ecommerceapp.domain.interfaces.SettingsRepository
import com.example.ecommerceapp.presentation.uimodels.SettingsModel
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor() : SettingsRepository {
    override fun getPersonalSettingsItems(): List<Int> =
        arrayListOf<Int>(
            R.string.profile,
            R.string.shipping_address,
            R.string.payment_methods
        )

    override fun getAccountSettingsItems(): List<SettingsModel> =
        arrayListOf<SettingsModel>(
            SettingsModel(R.string.language, R.string.english),
            SettingsModel(R.string.about_slada, null)
        )

}