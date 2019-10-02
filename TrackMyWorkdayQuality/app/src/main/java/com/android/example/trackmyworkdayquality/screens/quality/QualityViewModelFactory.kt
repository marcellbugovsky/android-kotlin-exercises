package com.android.example.trackmyworkdayquality.screens.quality

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.example.trackmyworkdayquality.database.DatabaseDao
import java.lang.IllegalArgumentException

class QualityViewModelFactory(
    private val workdayKey: Long,
    private val dataSource: DatabaseDao): ViewModelProvider.Factory {

    @Suppress("unchecked cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QualityViewModel::class.java)) {
            return QualityViewModel(workdayKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}