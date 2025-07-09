package com.perrygarg.twinmind.presentation.calendar

import com.perrygarg.twinmind.domain.model.EventModel

data class CalendarUiState(
    val isLoading: Boolean = false,
    val events: List<EventModel> = emptyList(),
    val error: String? = null
) 