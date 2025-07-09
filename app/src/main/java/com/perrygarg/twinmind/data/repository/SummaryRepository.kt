package com.perrygarg.twinmind.data.repository

import com.perrygarg.twinmind.domain.model.SummaryModel

interface SummaryRepository {
    suspend fun saveSummary(summary: SummaryModel): Result<Unit>
    suspend fun getSummary(id: String): SummaryModel?
} 