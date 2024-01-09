package com.crocheteer.crocheteer.data.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "yarn_colors",
    foreignKeys = [
        ForeignKey(
            entity = YarnType::class,
            parentColumns = ["id"],
            childColumns = ["yarn_type_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["yarn_type_id"], unique = false)]
)
data class YarnColor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "yarn_type_id")
    val yarnTypeId: Long,

    val quantity: Int,

    @ColumnInfo(name = "color_name")
    val colorName: String,

    @ColumnInfo(name = "color_code")
    val colorCode: String? = null,

    @ColumnInfo(name = "photo_uri")
    val photoUri: String? = null
)