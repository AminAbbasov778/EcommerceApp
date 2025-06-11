package com.example.ecommerceapp.domain.usecases.chatusecases

import com.example.ecommerceapp.domain.interfaces.ChatRepository
import com.example.ecommerceapp.domain.models.ChatMessageModel
import javax.inject.Inject

class SendMessageUseCase  @Inject constructor(val chatRepository: ChatRepository) {
    suspend operator fun invoke(chatMessageModel: ChatMessageModel) = chatRepository.sendMessage(chatMessageModel)
}