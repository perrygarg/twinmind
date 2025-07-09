package com.perrygarg.twinmind.domain.model

data class ChatMessage(
    val id: String,
    val message: String,
    val sender: MessageSender,
    val timestamp: Long
)

enum class MessageSender {
    USER, BOT
} 