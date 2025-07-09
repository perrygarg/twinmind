package com.perrygarg.twinmind.data.repository

import com.perrygarg.twinmind.domain.model.EventModel

interface EventRepository {
    suspend fun getUpcomingEvents(): List<EventModel>
} 