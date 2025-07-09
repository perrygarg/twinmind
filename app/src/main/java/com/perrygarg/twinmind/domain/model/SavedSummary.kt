package com.perrygarg.twinmind.domain.model

data class SavedSummary(
    val id: String,
    val title: String,
    val content: String,
    val timestamp: Long,
    val eventId: String? = null
) 