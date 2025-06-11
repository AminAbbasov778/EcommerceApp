package com.example.ecommerceapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.R
import com.example.ecommerceapp.domain.models.ChatMessageModel
import com.example.ecommerceapp.domain.usecases.chatusecases.GetMessagesUseCase
import com.example.ecommerceapp.domain.usecases.chatusecases.SendMessageUseCase
import com.example.ecommerceapp.domain.usecases.commonusecases.GetCurrentUserIdUseCase
import com.example.ecommerceapp.presentation.mappers.toUi
import com.example.ecommerceapp.presentation.uimodels.ChatMessageUiModel
import com.example.ecommerceapp.presentation.uistates.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    val sendMessageUseCase: SendMessageUseCase,
    val getMessagesUseCase: GetMessagesUseCase,
    val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
) : ViewModel() {
    private var _isMessageSent = MutableLiveData<UiState<Unit>>()
    val isMessageSent: LiveData<UiState<Unit>> get() = _isMessageSent

    private var _messages = MutableLiveData<UiState<List<ChatMessageUiModel>>>()
    val messages: LiveData<UiState<List<ChatMessageUiModel>>> get() = _messages





    fun sendMessage(text: String,receiverId  : String,productId  : String) {
        if(text.isNotEmpty()){
            _isMessageSent.value = UiState.Loading
            val message = ChatMessageModel(message = text.trim(), receiverId = receiverId, productId = productId)
            viewModelScope.launch(Dispatchers.IO) {
                val result = sendMessageUseCase(message)
                withContext(Dispatchers.Main) {
                    _isMessageSent.value = if (result.isSuccess) UiState.Success(Unit)
                    else UiState.Error(R.string.message_failed)
                }

            }
        }

    }

    fun getMessages(productId : String,otherUserId : String) {
        _messages.value = UiState.Loading
        val currentUserId = getCurrentUserIdUseCase()
        viewModelScope.launch(Dispatchers.IO) {
            val messagesFlow = getMessagesUseCase(productId,otherUserId)
            messagesFlow.collect { result ->
                withContext(Dispatchers.Main) {
                    _messages.value =  if (result.isSuccess) {

                            UiState.Success(result.getOrNull()?.map { it.toUi(currentUserId) }
                                ?: emptyList())
                    }
                    else{
                        UiState.Error(R.string.failed_get_messages)
                    }
                }
            }
        }
    }
}