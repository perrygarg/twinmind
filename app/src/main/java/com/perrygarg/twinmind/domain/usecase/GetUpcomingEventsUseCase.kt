package com.perrygarg.twinmind.domain.usecase

import com.perrygarg.twinmind.data.repository.EventRepository
import com.perrygarg.twinmind.domain.model.EventModel

class GetUpcomingEventsUseCase(private val eventRepository: EventRepository) {
    suspend operator fun invoke(): List<EventModel> {
        return eventRepository.getUpcomingEvents()
    }
} 