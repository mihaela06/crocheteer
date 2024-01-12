package com.crocheteer.crocheteer.network

import com.crocheteer.crocheteer.network.api.YarnApiService
import javax.inject.Inject

class RavelryYarnSearchRepository @Inject constructor(
    private val ravelryApiService: YarnApiService
) : YarnSearchRepository {
    override fun yarnSearchItemPagingSource(searchTerm: String): YarnSearchPagingSource {
        return YarnSearchPagingSource(searchTerm, ravelryApiService)
    }
}