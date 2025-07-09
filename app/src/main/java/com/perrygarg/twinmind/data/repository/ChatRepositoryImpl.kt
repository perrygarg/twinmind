package com.perrygarg.twinmind.data.repository

import com.perrygarg.twinmind.domain.model.ChatMessage
import com.perrygarg.twinmind.domain.model.MessageSender
import kotlinx.coroutines.delay
import java.util.UUID

class ChatRepositoryImpl : ChatRepository {
    
    // TODO: Replace with real AI backend integration
    // TODO: Integrate with Google Gemini API or OpenAI GPT
    // TODO: Add conversation context and memory
    // TODO: Implement streaming responses for real-time chat
    // TODO: Add conversation history and context management
    // TODO: Implement rate limiting and API key management
    // TODO: Add conversation threading and topic tracking
    // TODO: Implement fallback responses for API failures
    
    private val mockResponses = listOf(
        "Based on the meeting transcript, it looks like the team discussed the quarterly roadmap extensively. The key takeaway was focusing on user experience improvements.",
        "I can see from the transcription that there were several action items mentioned. Would you like me to summarize the main decisions made?",
        "The meeting covered important topics about product strategy. The team seemed to agree on prioritizing mobile features in the next quarter.",
        "From what I understand, the discussion was quite productive. The stakeholders appeared to be aligned on the proposed timeline.",
        "I noticed there were some concerns raised about resource allocation. Would you like me to highlight the specific points mentioned?",
        "The meeting transcript shows good engagement from all participants. The technical challenges discussed seem manageable with the current team structure.",
        "Based on the conversation, it appears the team is ready to move forward with the proposed changes. The timeline looks realistic.",
        "I can help you analyze the meeting content further. What specific aspect would you like to explore?",
        "The discussion touched on several important metrics. The team seems focused on improving user retention and engagement.",
        "From the transcript, I can see that the meeting was well-structured and covered all the key agenda items effectively."
    )
    
    private var responseIndex = 0

    override suspend fun sendMessage(userMessage: String): ChatMessage {
        // Simulate AI processing delay (1-2 seconds)
        delay((1000..2000).random().toLong())
        
        // Get next response from the list (cycle through)
        val response = mockResponses[responseIndex % mockResponses.size]
        responseIndex++
        
        return ChatMessage(
            id = UUID.randomUUID().toString(),
            message = response,
            sender = MessageSender.BOT,
            timestamp = System.currentTimeMillis()
        )
    }
} 