package com.perrygarg.twinmind.domain.usecase

import com.perrygarg.twinmind.data.repository.TranscriptionRepository
import com.perrygarg.twinmind.domain.model.TranscriptionModel
import kotlinx.coroutines.flow.Flow

class StartTranscriptionUseCase(private val transcriptionRepository: TranscriptionRepository) {
    operator fun invoke(): Flow<TranscriptionModel> {
        return transcriptionRepository.startTranscription()
    }
} 