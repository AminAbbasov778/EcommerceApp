package com.example.ecommerceapp.domain.interfaces

import com.example.ecommerceapp.data.model.chat.ChatMessage
import com.example.ecommerceapp.domain.models.ChatMessageModel
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun sendMessage(message: ChatMessageModel): Result<Unit>
    fun getMessages(productId: String, otherUserId: String): Flow<Result<List<ChatMessageModel>>>
}
