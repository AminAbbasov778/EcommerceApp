package com.example.ecommerceapp.presentation.uimodels


data class ChatMessageUiModel(
    val id: String= "",
    val message: String,
    val isMine: Boolean = false,
    val formattedTime: String = "",
)
