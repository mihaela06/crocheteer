package com.crocheteer.crocheteer.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "yarn_types",
    indices = [
        Index(value = ["name", "company_name"], unique = true)
    ]
)
data class YarnType(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,

    @ColumnInfo(name = "company_name")
    val companyName: String,

    val grams: Int,

    val length: Int,

    @ColumnInfo(name = "machine_washable")
    val machineWashable: Boolean? = null,

    val weight: YarnWeight? = null,

    @ColumnInfo(name = "generic_photo_url")
    val genericPhotoUrl: String? = null,
)