package com.perrygarg.twinmind.data.repository

import com.perrygarg.twinmind.domain.model.TranscriptionModel
import kotlinx.coroutines.flow.Flow

interface TranscriptionRepository {
    fun startTranscription(): Flow<TranscriptionModel>
    fun stopTranscription()
} 