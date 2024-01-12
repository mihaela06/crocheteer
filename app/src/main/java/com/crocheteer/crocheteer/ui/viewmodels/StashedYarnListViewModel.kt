package com.crocheteer.crocheteer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.crocheteer.crocheteer.data.YarnStashRepository
import com.crocheteer.crocheteer.data.entities.YarnTypeWithColors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class StashedYarnListViewModel @Inject constructor(
    private val yarnStashRepository: YarnStashRepository
) : ViewModel() {
    fun yarnStashPagingDataFlow(searchTerm: String): Flow<PagingData<YarnTypeWithColors>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { yarnStashRepository.yarnStashPagingSource(searchTerm) }
        )
            .flow
            .cachedIn(viewModelScope)
    }
}
