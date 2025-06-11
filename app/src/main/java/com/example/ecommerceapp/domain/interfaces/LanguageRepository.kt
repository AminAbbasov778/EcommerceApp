package com.example.ecommerceapp.domain.interfaces
import com.example.ecommerceapp.domain.models.LanguageModel

interface LanguageRepository {
    fun getCurrentLanguage(): Result<String>
    fun setLanguage(language: String): Result<Unit>
    fun getLanguageList(): List<LanguageModel>
}