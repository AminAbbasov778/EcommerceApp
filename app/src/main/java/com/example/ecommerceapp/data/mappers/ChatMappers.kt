package com.example.ecommerceapp.data.mappers

import com.example.ecommerceapp.data.model.chat.ChatMessage
import com.example.ecommerceapp.domain.models.ChatMessageModel

fun ChatMessageModel.toData(userId : String): ChatMessage{
    return ChatMessage(id,userId,message,System.currentTimeMillis())
}
fun ChatMessage.toDomain(): ChatMessageModel{
    return ChatMessageModel(id,senderId,message,timestamp,receiverId,productId)
}
