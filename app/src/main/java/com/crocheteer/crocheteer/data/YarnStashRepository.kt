package com.crocheteer.crocheteer.data

import androidx.paging.PagingSource
import com.crocheteer.crocheteer.data.entities.YarnTypeWithColors

interface YarnStashRepository {
    fun yarnStashPagingSource(searchTerm: String): PagingSource<Int, YarnTypeWithColors>
    suspend fun addStashedYarn(newYarn: YarnTypeWithColors)
    suspend fun updateStashedYarn(updatedYarn: YarnTypeWithColors)
    suspend fun deleteStashedYarn(toDelYarn: YarnTypeWithColors)
    suspend fun getCompanyName(searchTerm: String): List<String>
}