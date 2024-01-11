package com.crocheteer.crocheteer.network.entities

import com.google.gson.annotations.SerializedName

data class YarnResponse (
    @SerializedName("discontinued"        ) var discontinued       : Boolean?    = null,
    @SerializedName("gauge_divisor"       ) var gaugeDivisor       : Int?        = null,
    @SerializedName("grams"               ) var grams              : Int?        = null,
    @SerializedName("id"                  ) var id                 : Int?        = null,
    @SerializedName("machine_washable"    ) var machineWashable    : Boolean?    = null,
    @SerializedName("max_gauge"           ) var maxGauge           : String?     = null,
    @SerializedName("min_gauge"           ) var minGauge           : String?        = null,
    @SerializedName("name"                ) var name               : String?     = null,
    @SerializedName("permalink"           ) var permalink          : String?     = null,
    @SerializedName("rating_average"      ) var ratingAverage      : Double?     = null,
    @SerializedName("rating_count"        ) var ratingCount        : Int?        = null,
    @SerializedName("rating_total"        ) var ratingTotal        : Int?        = null,
    @SerializedName("texture"             ) var texture            : String?     = null,
    @SerializedName("thread_size"         ) var threadSize         : String?     = null,
    @SerializedName("wpi"                 ) var wpi                : String?     = null,
    @SerializedName("yardage"             ) var yardage            : Int?        = null,
    @SerializedName("yarn_company_name"   ) var yarnCompanyName    : String?     = null,
    @SerializedName("first_photo"         ) var firstPhoto         : YarnPhotoResponse? = YarnPhotoResponse(),
    @SerializedName("personal_attributes" ) var personalAttributes : String?     = null,
    @SerializedName("yarn_weight"         ) var yarnWeight         : YarnWeightResponse? = YarnWeightResponse()
)