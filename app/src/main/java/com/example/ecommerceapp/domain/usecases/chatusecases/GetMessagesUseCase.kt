package com.example.ecommerceapp.domain.usecases.chatusecases

import com.example.ecommerceapp.domain.interfaces.ChatRepository
import com.example.ecommerceapp.domain.models.ChatMessageModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(val chatRepository: ChatRepository) {
    operator fun invoke(productId : String,otherUserId : String): Flow<Result<List<ChatMessageModel>>> = chatRepository.getMessages(productId,otherUserId)
}