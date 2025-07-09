package com.perrygarg.twinmind.domain.usecase

import com.perrygarg.twinmind.data.repository.ChatRepository
import com.perrygarg.twinmind.domain.model.ChatMessage

class SendMessageUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(userMessage: String): ChatMessage {
        return chatRepository.sendMessage(userMessage)
    }
} 