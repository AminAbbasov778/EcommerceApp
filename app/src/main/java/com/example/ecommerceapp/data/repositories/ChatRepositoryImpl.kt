package com.example.ecommerceapp.data.repositories

import com.example.ecommerceapp.data.mappers.toData
import com.example.ecommerceapp.data.mappers.toDomain
import com.example.ecommerceapp.data.model.chat.ChatMessage
import com.example.ecommerceapp.domain.interfaces.ChatRepository
import com.example.ecommerceapp.domain.models.ChatMessageModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ChatRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : ChatRepository {


    override fun getMessages(productId: String, otherUserId: String): Flow<Result<List<ChatMessageModel>>> = callbackFlow {
        val currentUserId = firebaseAuth.currentUser?.uid ?: run {
            trySend(Result.failure(Exception("User not logged in")))
            close()
            return@callbackFlow
        }

        val chatId = "${productId}_${minOf(currentUserId, otherUserId)}_${maxOf(currentUserId, otherUserId)}"

        val listener = firestore.collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Result.failure(error))
                    return@addSnapshotListener
                }

                val messages = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(ChatMessage::class.java)?.toDomain()
                } ?: emptyList()

                trySend(Result.success(messages))
            }

        awaitClose { listener.remove() }
    }


    override suspend fun sendMessage(
        message: ChatMessageModel
    ): Result<Unit> {
        val senderId = firebaseAuth.currentUser?.uid ?: return Result.failure(Exception("User not logged in"))
        val chatId = "${message.productId}_${minOf(senderId, message.receiverId)}_${maxOf(senderId, message.receiverId)}"

        return try {
            val messageData = message.toData(senderId)
            firestore.collection("chats")
                .document(chatId)
                .collection("messages")
                .add(messageData)
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
