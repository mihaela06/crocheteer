package com.crocheteer.crocheteer.network.entities

import com.google.gson.annotations.SerializedName

data class YarnPhotoResponse(
    @SerializedName("id"               ) var id              : Int?    = null,
    @SerializedName("sort_order"       ) var sortOrder       : Int?    = null,
    @SerializedName("user_id"          ) var userId          : Int?    = null,
    @SerializedName("x_offset"         ) var xOffset         : Int?    = null,
    @SerializedName("y_offset"         ) var yOffset         : Int?    = null,
    @SerializedName("square_url"       ) var squareUrl       : String? = null,
    @SerializedName("medium_url"       ) var mediumUrl       : String? = null,
    @SerializedName("thumbnail_url"    ) var thumbnailUrl    : String? = null,
    @SerializedName("small_url"        ) var smallUrl        : String? = null,
    @SerializedName("medium2_url"      ) var medium2Url      : String? = null,
    @SerializedName("small2_url"       ) var small2Url       : String? = null,
    @SerializedName("caption"          ) var caption         : String? = null,
    @SerializedName("caption_html"     ) var captionHtml     : String? = null,
    @SerializedName("copyright_holder" ) var copyrightHolder : String? = null
)
