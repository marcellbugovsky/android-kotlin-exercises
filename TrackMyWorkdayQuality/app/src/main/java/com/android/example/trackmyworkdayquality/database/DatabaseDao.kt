package com.android.example.trackmyworkdayquality.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DatabaseDao {

    @Insert
    fun insert(day: Workday)

    @Update
    fun update(day: Workday)

    @Query("SELECT * FROM daily_workday_quality_table WHERE dayId = :key")
    fun get(key: Long): Workday?

    @Query("DELETE FROM daily_workday_quality_table")
    fun clear()

    @Query("SELECT * FROM daily_workday_quality_table ORDER BY dayId DESC LIMIT 1")
    fun getToday(): Workday

    @Query("SELECT * FROM daily_workday_quality_table ORDER BY dayId DESC")
    fun getAllDays(): LiveData<List<Workday>>
}