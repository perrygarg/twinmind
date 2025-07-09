package com.perrygarg.twinmind.presentation.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perrygarg.twinmind.data.repository.SummaryRepositoryImpl
import com.perrygarg.twinmind.domain.model.SummaryModel
import com.perrygarg.twinmind.domain.usecase.SaveSummaryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class SummaryViewModel(
    private val saveSummaryUseCase: SaveSummaryUseCase = SaveSummaryUseCase(SummaryRepositoryImpl())
) : ViewModel() {

    private val _uiState = MutableStateFlow(SummaryUiState())
    val uiState: StateFlow<SummaryUiState> = _uiState.asStateFlow()

    fun initializeSummary(transcript: String?) {
        if (transcript.isNullOrBlank()) {
            _uiState.value = _uiState.value.copy(
                summaryText = "",
                error = "No transcript provided"
            )
            return
        }

        // Generate a mock summary from the transcript
        val summary = generateSummaryFromTranscript(transcript)
        _uiState.value = _uiState.value.copy(
            summaryText = summary,
            error = null
        )
    }

    fun saveSummary() {
        val currentState = _uiState.value
        if (currentState.summaryText.isEmpty()) {
            _uiState.value = currentState.copy(
                error = "No summary to save"
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true, error = null)
            
            try {
                val summaryModel = SummaryModel(
                    id = UUID.randomUUID().toString(),
                    fullText = currentState.summaryText,
                    timestamp = System.currentTimeMillis()
                )
                saveSummaryUseCase(summaryModel)
                _uiState.value = currentState.copy(
                    isLoading = false,
                    isSaved = true,
                    showSaveSuccess = true
                )
                
                // Hide success message after 3 seconds
                kotlinx.coroutines.delay(3000)
                _uiState.value = _uiState.value.copy(showSaveSuccess = false)
                
            } catch (e: Exception) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    error = "Failed to save summary: ${e.message}"
                )
            }
        }
    }

    fun shareSummary() {
        val currentState = _uiState.value
        if (currentState.summaryText.isEmpty()) {
            _uiState.value = currentState.copy(
                error = "No summary to share"
            )
            return
        }

        try {
            // This will be handled by the UI layer with Intent
            _uiState.value = currentState.copy(
                shouldShare = true,
                error = null
            )
        } catch (e: Exception) {
            _uiState.value = currentState.copy(
                error = "Failed to share summary: ${e.message}"
            )
        }
    }

    fun onShareCompleted() {
        _uiState.value = _uiState.value.copy(shouldShare = false)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    private fun generateSummaryFromTranscript(transcript: String): String {
        // Mock summary generation - in real app, this would use AI/ML
        val sentences = transcript.split(". ").filter { it.isNotBlank() }
        val keyPoints = sentences.take(3).map { it.trim() }
        
        return buildString {
            appendLine("Meeting Summary")
            appendLine("=".repeat(20))
            appendLine()
            appendLine("Key Points:")
            keyPoints.forEachIndexed { index, point ->
                appendLine("${index + 1}. $point")
            }
            appendLine()
            appendLine("Total Duration: ~${sentences.size * 2} minutes")
            appendLine("Participants: 3-5 people")
            appendLine()
            appendLine("Action Items:")
            appendLine("- Follow up on discussed topics")
            appendLine("- Schedule next meeting")
            appendLine("- Share meeting notes with team")
        }
    }
} 