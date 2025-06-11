package com.example.ecommerceapp.domain.models

data class ChatMessageModel(
    val id: String = "",
    val senderId: String ="1",
    val message: String = "",
    val timestamp: Long = 0L,
    val receiverId : String = "1",
   val  productId: String = "1",

    )
