package com.perrygarg.twinmind.presentation.saved_summaries

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perrygarg.twinmind.domain.model.SavedSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

data class SavedSummariesUiState(
    val summaries: List<SavedSummary> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val shareIntent: Intent? = null
)

class SavedSummariesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SavedSummariesUiState())
    val uiState: StateFlow<SavedSummariesUiState> = _uiState.asStateFlow()

    init {
        loadSavedSummaries()
    }

    private fun loadSavedSummaries() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                // TODO: Replace with actual repository call
                val mockSummaries = listOf(
                    SavedSummary(
                        id = UUID.randomUUID().toString(),
                        title = "Product Strategy Meeting",
                        content = "Discussed Q4 roadmap, new feature priorities, and team resource allocation. Key decisions made on mobile app redesign and API integration timeline.",
                        timestamp = System.currentTimeMillis() - 86400000 // 1 day ago
                    ),
                    SavedSummary(
                        id = UUID.randomUUID().toString(),
                        title = "Client Demo Call",
                        content = "Presented new dashboard features to enterprise client. Received positive feedback on analytics capabilities and requested additional customization options.",
                        timestamp = System.currentTimeMillis() - 172800000 // 2 days ago
                    ),
                    SavedSummary(
                        id = UUID.randomUUID().toString(),
                        title = "Team Standup",
                        content = "Team members reported progress on current sprint tasks. Identified blockers in payment integration and scheduled follow-up meeting for technical review.",
                        timestamp = System.currentTimeMillis() - 259200000 // 3 days ago
                    )
                )
                _uiState.value = _uiState.value.copy(
                    summaries = mockSummaries,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load summaries"
                )
            }
        }
    }

    fun deleteSummary(summaryId: String) {
        viewModelScope.launch {
            try {
                // TODO: Replace with actual repository call
                val currentSummaries = _uiState.value.summaries.toMutableList()
                currentSummaries.removeAll { it.id == summaryId }
                _uiState.value = _uiState.value.copy(summaries = currentSummaries)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to delete summary"
                )
            }
        }
    }

    fun shareSummary(summary: SavedSummary) {
        viewModelScope.launch {
            try {
                val dateFormat = SimpleDateFormat("MMM dd, yyyy 'at' HH:mm", Locale.getDefault())
                val shareText = buildString {
                    appendLine("📋 ${summary.title}")
                    appendLine("📅 ${dateFormat.format(Date(summary.timestamp))}")
                    appendLine()
                    appendLine("📝 Summary:")
                    appendLine(summary.content)
                    appendLine()
                    appendLine("Shared from TwinMind - AI Meeting Assistant")
                }
                
                // Create share intent
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    putExtra(Intent.EXTRA_SUBJECT, "Meeting Summary: ${summary.title}")
                }
                
                // Store the intent for the UI to use
                _uiState.value = _uiState.value.copy(
                    shareIntent = shareIntent
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to prepare share"
                )
            }
        }
    }

    fun clearShareIntent() {
        _uiState.value = _uiState.value.copy(shareIntent = null)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
} 