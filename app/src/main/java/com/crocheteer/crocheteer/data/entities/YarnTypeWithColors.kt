package com.crocheteer.crocheteer.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class YarnTypeWithColors(
    @Embedded
    val type: YarnType,

    @Relation(parentColumn = "id", entityColumn = "yarn_type_id", entity = YarnColor::class)
    val colors: List<YarnColor>?
)