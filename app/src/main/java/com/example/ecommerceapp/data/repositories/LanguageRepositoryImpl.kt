package com.example.ecommerceapp.data.repositories

import android.content.SharedPreferences
import com.example.ecommerceapp.R
import com.example.ecommerceapp.data.mappers.toDomain
import com.example.ecommerceapp.data.model.language.Language
import com.example.ecommerceapp.domain.interfaces.LanguageRepository
import com.example.ecommerceapp.domain.models.LanguageModel
import javax.inject.Inject
import kotlin.collections.map

class LanguageRepositoryImpl @Inject constructor(
     val sharedPreferences: SharedPreferences,
) : LanguageRepository {

    override fun getCurrentLanguage(): Result<String> {
        return try {
            val language = sharedPreferences.getString("language", "en") ?: "en"
            Result.success(language)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun setLanguage(language: String): Result<Unit> {
        return try {
            sharedPreferences.edit().putString("language", language).apply()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override fun getLanguageList(): List<LanguageModel> {
       val languagesList =
           listOf<Language>(Language(R.string.english, "en"), Language(R.string.azerbaijani, "az"))
       return languagesList.map { it.toDomain() }
    }
}