package com.android.example.trackmyworkdayquality.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Workday::class], version = 1, exportSchema = false)
abstract class WorkDatabase: RoomDatabase() {

    abstract val databaseDao: DatabaseDao
    companion object {
        @Volatile
        private var INSTANCE: WorkDatabase? = null

        fun getInstance(context: Context): WorkDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WorkDatabase::class.java,
                        "work_history_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}