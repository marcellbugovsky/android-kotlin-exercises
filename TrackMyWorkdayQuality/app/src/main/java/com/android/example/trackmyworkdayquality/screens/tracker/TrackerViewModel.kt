package com.android.example.trackmyworkdayquality.screens.tracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.example.trackmyworkdayquality.database.DatabaseDao

class TrackerViewModel(
        val database: DatabaseDao,
        application: Application) : AndroidViewModel(application) {

}