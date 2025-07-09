package com.perrygarg.twinmind.data.repository

import com.perrygarg.twinmind.domain.model.ChatMessage

interface ChatRepository {
    suspend fun sendMessage(userMessage: String): ChatMessage
} 