package com.crocheteer.crocheteer.network

interface YarnSearchRepository {
    fun yarnSearchItemPagingSource(searchTerm: String): YarnSearchPagingSource
}