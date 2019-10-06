package com.jairrab.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jairrab.cache.dao.CachedDao
import com.jairrab.cache.entities.DataProperty
import com.jairrab.cache.entities.MapPointEntity
import javax.inject.Inject

@Database(
    entities = [
        MapPointEntity::class,
        DataProperty::class
    ],
    version = 1
)
abstract class AppDatabase @Inject constructor() : RoomDatabase() {

    abstract fun cachedDao(): CachedDao

    companion object {

        private var INSTANCE: AppDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, "projects.db"
                        ).build()
                    }
                    return INSTANCE as AppDatabase
                }
            }
            return INSTANCE as AppDatabase
        }
    }

}