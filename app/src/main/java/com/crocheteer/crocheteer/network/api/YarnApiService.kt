package com.crocheteer.crocheteer.network.api

import com.crocheteer.crocheteer.network.entities.YarnSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface YarnApiService {
    @GET("yarns/search.json")
    suspend fun paginatedYarnSearch(@Query("query") searchTerm: String, @Query("page") pageId: Int):
            YarnSearchResponse
}