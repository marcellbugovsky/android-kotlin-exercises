package com.android.example.trackmyworkdayquality.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_workday_quality_table")
data class Workday(
    @PrimaryKey(autoGenerate = true)
    var dayId: Long = 0L,

    @ColumnInfo(name = "start_time_milli")
    var startTimeMilli: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "end_time_milli")
    var endTimeMilli: Long = startTimeMilli,

    @ColumnInfo(name = "quality_rating")
    var workdayQuality: Int = -1
)