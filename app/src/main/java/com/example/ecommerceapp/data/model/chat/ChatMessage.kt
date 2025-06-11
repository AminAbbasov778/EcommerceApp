package com.example.ecommerceapp.data.model.chat

data class ChatMessage(
    val id: String  = "",
    val senderId: String = "",
    val message: String = "",
    val timestamp: Long = 0L,
    val receiverId : String = "",
    val  productId: String = ""
)
