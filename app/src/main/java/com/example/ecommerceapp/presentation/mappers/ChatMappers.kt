package com.example.ecommerceapp.presentation.mappers

import com.example.ecommerceapp.domain.models.ChatMessageModel
import com.example.ecommerceapp.presentation.uimodels.ChatMessageUiModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun ChatMessageModel.toUi(currentUserId: String?): ChatMessageUiModel {
    val isMine = this.senderId == currentUserId
    val formattedTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(timestamp))
    return ChatMessageUiModel(id, message, isMine, formattedTime)
}
