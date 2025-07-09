package com.perrygarg.twinmind.presentation.chat

import com.perrygarg.twinmind.domain.model.ChatMessage

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val inputText: String = "",
    val isLoading: Boolean = false,
    val isTyping: Boolean = false,
    val error: String? = null
) 