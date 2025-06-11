package com.example.mynewsapp.domain.usecases.languageusecases

import com.example.ecommerceapp.domain.interfaces.LanguageRepository
import com.example.ecommerceapp.domain.models.LanguageModel
import javax.inject.Inject

class GetLanguageListUseCase @Inject constructor(val languageRepository: LanguageRepository) {
   operator fun invoke(): List<LanguageModel> = languageRepository.getLanguageList()
}