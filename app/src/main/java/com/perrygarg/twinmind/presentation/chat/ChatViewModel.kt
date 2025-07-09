package com.perrygarg.twinmind.presentation.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perrygarg.twinmind.data.repository.ChatRepositoryImpl
import com.perrygarg.twinmind.domain.model.ChatMessage
import com.perrygarg.twinmind.domain.model.MessageSender
import com.perrygarg.twinmind.domain.usecase.SendMessageUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

class ChatViewModel : ViewModel() {
    private val chatRepository = ChatRepositoryImpl()
    private val sendMessageUseCase = SendMessageUseCase(chatRepository)
    
    var uiState by mutableStateOf(ChatUiState())
        private set

    fun updateInputText(text: String) {
        uiState = uiState.copy(inputText = text)
    }

    fun sendMessage() {
        val message = uiState.inputText.trim()
        if (message.isEmpty()) return

        // Add user message immediately
        val userMessage = ChatMessage(
            id = UUID.randomUUID().toString(),
            message = message,
            sender = MessageSender.USER,
            timestamp = System.currentTimeMillis()
        )
        
        uiState = uiState.copy(
            messages = uiState.messages + userMessage,
            inputText = "",
            isLoading = true,
            isTyping = true,
            error = null
        )

        // Get AI response with typing indicator
        viewModelScope.launch {
            try {
                // Show typing indicator for a short duration
                delay(1500) // 1.5 seconds typing animation
                
                val botResponse = sendMessageUseCase(message)
                uiState = uiState.copy(
                    messages = uiState.messages + botResponse,
                    isLoading = false,
                    isTyping = false
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    isTyping = false,
                    error = e.message ?: "Failed to get response"
                )
            }
        }
    }
} 