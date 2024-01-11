package com.crocheteer.crocheteer.network.entities

import com.google.gson.annotations.SerializedName

data class YarnWeightResponse  (
    @SerializedName("crochet_gauge" ) var crochetGauge : String? = null,
    @SerializedName("id"            ) var id           : Int?    = null,
    @SerializedName("knit_gauge"    ) var knitGauge    : String? = null,
    @SerializedName("max_gauge"     ) var maxGauge     : String? = null,
    @SerializedName("min_gauge"     ) var minGauge     : String? = null,
    @SerializedName("name"          ) var name         : String? = null,
    @SerializedName("ply"           ) var ply          : String? = null,
    @SerializedName("wpi"           ) var wpi          : String? = null
)