package com.android.example.trackmyworkdayquality.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.example.trackmyworkdayquality.database.DatabaseDao

class DetailViewModelFactory(
    private val workdayKey: Long,
    private val dataSource: DatabaseDao
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(workdayKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}