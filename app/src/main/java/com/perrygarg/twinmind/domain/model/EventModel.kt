package com.perrygarg.twinmind.domain.model

import java.time.LocalDateTime

data class EventModel(
    val id: String,
    val title: String,
    val timestamp: LocalDateTime,
    val description: String
) 