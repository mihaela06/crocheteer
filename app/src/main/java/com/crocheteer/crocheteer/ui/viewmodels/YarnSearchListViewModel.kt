package com.crocheteer.crocheteer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.crocheteer.crocheteer.data.entities.YarnType
import com.crocheteer.crocheteer.network.YarnSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class YarnSearchListViewModel @Inject constructor(
    private val yarnSearchRepository: YarnSearchRepository
) : ViewModel() {
    fun yarnSearchPagingDataFlow(searchTerm: String): Flow<PagingData<Pair<YarnType, Int>>> {
        return Pager(
            config = PagingConfig(pageSize = 50, enablePlaceholders = false),
            pagingSourceFactory = { yarnSearchRepository.yarnSearchItemPagingSource(searchTerm) }
        )
            .flow
            .cachedIn(viewModelScope)
    }
}