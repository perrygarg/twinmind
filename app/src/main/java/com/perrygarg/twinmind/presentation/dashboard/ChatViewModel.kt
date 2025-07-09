package com.perrygarg.twinmind.presentation.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private var _messages = mutableListOf<ChatMessage>()
    var messages by mutableStateOf<List<ChatMessage>>(emptyList())
        private set

    fun onSend(message: String) {
        if (message.isNotBlank()) {
            val userMsg = ChatMessage(text = message, isUser = true)
            _messages.add(userMsg)
            messages = _messages.toList()
            viewModelScope.launch {
                delay(1000)
                val aiMsg = ChatMessage(text = "AI: This is a mock response.", isUser = false)
                _messages.add(aiMsg)
                messages = _messages.toList()
            }
        }
    }
} 