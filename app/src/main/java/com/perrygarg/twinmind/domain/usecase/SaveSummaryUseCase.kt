package com.perrygarg.twinmind.domain.usecase

import com.perrygarg.twinmind.data.repository.SummaryRepository
import com.perrygarg.twinmind.domain.model.SummaryModel

class SaveSummaryUseCase(private val summaryRepository: SummaryRepository) {
    suspend operator fun invoke(summary: SummaryModel): Result<Unit> {
        return summaryRepository.saveSummary(summary)
    }
} 