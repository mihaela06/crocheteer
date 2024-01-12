package com.crocheteer.crocheteer.data

import androidx.paging.PagingSource
import com.crocheteer.crocheteer.data.entities.YarnType
import com.crocheteer.crocheteer.data.entities.YarnTypeWithColors
import kotlinx.coroutines.flow.Flow

interface YarnStashRepository {
    fun yarnStashPagingSource(searchTerm: String): PagingSource<Int, YarnTypeWithColors>
    suspend fun addStashedYarn(newYarn: YarnTypeWithColors)
    suspend fun getStashedYarn(id: Long) : Flow<YarnTypeWithColors>
    suspend fun updateStashedYarn(updatedYarn: YarnTypeWithColors)
    suspend fun deleteStashedYarn(yarnType: YarnType)
    suspend fun getCompanyName(searchTerm: String): List<String>
}