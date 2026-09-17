package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.data.model.CardRarity
import com.example.data.model.CollectedCard
import com.example.data.model.DailyTask
import com.example.data.model.UserProfile

class Converters {
    @TypeConverter
    fun fromRarity(value: CardRarity): String = value.name

    @TypeConverter
    fun toRarity(value: String): CardRarity = try {
        CardRarity.valueOf(value)
    } catch (e: Exception) {
        CardRarity.COMMON
    }
}

@Database(
    entities = [
        UserProfile::class,
        DailyTask::class,
        CollectedCard::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class NonaDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun taskDao(): TaskDao
    abstract fun cardDao(): CardDao

    companion object {
        @Volatile
        private var INSTANCE: NonaDatabase? = null

        fun getDatabase(context: Context): NonaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NonaDatabase::class.java,
                    "nona_zen.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
