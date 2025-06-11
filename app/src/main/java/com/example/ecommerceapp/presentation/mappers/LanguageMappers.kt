package com.example.ecommerceapp.presentation.mappers

import com.example.ecommerceapp.domain.models.LanguageModel
import com.example.ecommerceapp.presentation.uimodels.LanguageUiModel

fun LanguageModel.toUi(): LanguageUiModel{
    return LanguageUiModel(language,code,false)
}

