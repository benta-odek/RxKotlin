package com.odhiambodevelopers.rxkotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DetailsEntity::class], exportSchema = false, version = 1)
abstract class DetailsDatabase :RoomDatabase() {
    abstract val detailsDao: DetailsDao

    companion object {

        @Volatile
        private var INSTANCE: DetailsDatabase? = null

        fun getInstance(context: Context): DetailsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        DetailsDatabase::class.java,
                        "notes_database").fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}