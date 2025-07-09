package com.perrygarg.twinmind.presentation.transcription

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perrygarg.twinmind.data.repository.TranscriptionRepositoryImpl
import com.perrygarg.twinmind.domain.usecase.StartTranscriptionUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TranscriptionViewModel : ViewModel() {
    private val transcriptionRepository = TranscriptionRepositoryImpl()
    private val startTranscriptionUseCase = StartTranscriptionUseCase(transcriptionRepository)
    
    var uiState by mutableStateOf(TranscriptionUiState())
        private set

    init {
        startTranscription()
    }

    private fun startTranscription() {
        viewModelScope.launch {
            uiState = uiState.copy(isListening = true, error = null)
            
            startTranscriptionUseCase()
                .onEach { transcriptionModel ->
                    uiState = uiState.copy(
                        textBuffer = transcriptionModel.text,
                        isListening = true
                    )
                }
                .catch { exception ->
                    uiState = uiState.copy(
                        isListening = false,
                        error = exception.message ?: "Transcription failed"
                    )
                }
                .launchIn(viewModelScope)
        }
    }

    fun endTranscription(): String {
        transcriptionRepository.stopTranscription()
        uiState = uiState.copy(isListening = false)
        return uiState.textBuffer
    }

    override fun onCleared() {
        super.onCleared()
        transcriptionRepository.stopTranscription()
    }
} 