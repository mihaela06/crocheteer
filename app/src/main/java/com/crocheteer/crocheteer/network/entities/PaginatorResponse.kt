package com.crocheteer.crocheteer.network.entities

import com.google.gson.annotations.SerializedName

data class PaginatorResponse (
    @SerializedName("page_count" ) var pageCount : Int? = null,
    @SerializedName("page"       ) var page      : Int? = null,
    @SerializedName("page_size"  ) var pageSize  : Int? = null,
    @SerializedName("results"    ) var results   : Int? = null,
    @SerializedName("last_page"  ) var lastPage  : Int? = null
)