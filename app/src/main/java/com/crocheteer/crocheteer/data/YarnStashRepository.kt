package com.crocheteer.crocheteer.data

import androidx.paging.PagingSource
import com.crocheteer.crocheteer.data.entities.YarnTypeWithColors

interface YarnStashRepository {
    val yarnStashPagingSource: PagingSource<Int, YarnTypeWithColors>
    suspend fun addStashedYarn(newYarn: YarnTypeWithColors)
    suspend fun updateStashedYarn(updatedYarn: YarnTypeWithColors)
    suspend fun deleteStashedYarn(toDelYarn: YarnTypeWithColors)
}