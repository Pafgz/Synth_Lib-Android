package com.paf.synthlib.implementation.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.paf.synthlib.implementation.database.preset.PresetDao
import com.paf.synthlib.implementation.database.preset.TagDao
import com.paf.synthlib.implementation.database.preset.model.PresetEntity
import com.paf.synthlib.implementation.database.preset.model.TagEntity

@Database(
    entities = [
        PresetEntity::class,
        TagEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun presetDao(): PresetDao

    abstract fun tagDao(): TagDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "db"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}