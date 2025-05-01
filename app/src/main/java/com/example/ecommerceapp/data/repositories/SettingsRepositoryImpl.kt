package com.example.ecommerceapp.data.repositories

import com.example.ecommerceapp.R
import com.example.ecommerceapp.data.mappers.toDomain
import com.example.ecommerceapp.data.model.settings.Settings
import com.example.ecommerceapp.domain.interfaces.SettingsRepository
import com.example.ecommerceapp.domain.models.SettingsModel
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor() : SettingsRepository {
    override fun getPersonalSettingsItems(): List<Int> =
        arrayListOf<Int>(
            R.string.profile,
            R.string.shipping_address,
            R.string.payment_methods
        )

    override fun getAccountSettingsItems(): List<SettingsModel>
    {
        val list =   arrayListOf<Settings>(  Settings(R.string.language, R.string.english),
            Settings(R.string.about_slada, null)
        )
        return list.map { it.toDomain() }
    }


}