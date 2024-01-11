package com.crocheteer.crocheteer.network.entities

import com.google.gson.annotations.SerializedName;

data class YarnSearchResponse (
    @SerializedName("yarns"     ) var yarns     : ArrayList<YarnResponse>  = arrayListOf(),
    @SerializedName("paginator" ) var paginator : PaginatorResponse?       = PaginatorResponse()
)