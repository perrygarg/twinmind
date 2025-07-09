package com.perrygarg.twinmind.presentation.transcription

data class TranscriptionUiState(
    val textBuffer: String = "",
    val isListening: Boolean = false,
    val error: String? = null
) 