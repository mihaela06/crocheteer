package com.crocheteer.crocheteer.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {
        @Volatile
        private var Instance: YarnDatabase? = null

        fun getDatabase(context: Context): YarnDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, YarnDatabase::class.java, "item_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}