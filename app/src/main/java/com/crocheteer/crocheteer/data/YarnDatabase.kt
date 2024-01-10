package com.crocheteer.crocheteer.data


import androidx.room.Database
import androidx.room.RoomDatabase
import com.crocheteer.crocheteer.data.entities.YarnColor
import com.crocheteer.crocheteer.data.entities.YarnType

@Database(
    entities = [YarnType::class, YarnColor::class],
    version = 1,
    exportSchema = false
)
abstract class YarnDatabase : RoomDatabase() {
    abstract fun yarnDao(): YarnDao
}