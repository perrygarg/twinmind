package com.perrygarg.twinmind.presentation.calendar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perrygarg.twinmind.data.repository.EventRepositoryImpl
import com.perrygarg.twinmind.domain.usecase.GetUpcomingEventsUseCase
import kotlinx.coroutines.launch

class CalendarViewModel : ViewModel() {
    private val eventRepository = EventRepositoryImpl()
    private val getUpcomingEventsUseCase = GetUpcomingEventsUseCase(eventRepository)
    
    var uiState by mutableStateOf(CalendarUiState())
        private set

    init {
        loadUpcomingEvents()
    }

    private fun loadUpcomingEvents() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            try {
                val events = getUpcomingEventsUseCase()
                uiState = uiState.copy(
                    isLoading = false,
                    events = events,
                    error = null
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load events"
                )
            }
        }
    }

    fun onEventClick(eventId: String) {
        // TODO: Navigate to Transcription screen with event details
        // This will be handled by the UI layer through navigation
    }

    fun retryLoading() {
        loadUpcomingEvents()
    }
} 