package com.perrygarg.twinmind.presentation.summary

data class SummaryUiState(
    val summaryText: String = "",
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val showSaveSuccess: Boolean = false,
    val error: String? = null,
    val shouldShare: Boolean = false
) 