package com.android.example.trackmyworkdayquality.screens.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.trackmyworkdayquality.database.DatabaseDao
import com.android.example.trackmyworkdayquality.database.Workday
import kotlinx.coroutines.Job

class DetailViewModel(
    private val workdayKey: Long = 0L,
    dataSource: DatabaseDao
) : ViewModel() {

    val database = dataSource

    // Coroutine variables
    private val viewModelJob = Job()
    private val workday: LiveData<Workday>
    fun getWorkday() = workday

    // Variable to navigate to TrackerFragment
    private val _navigateToTracker = MutableLiveData<Boolean?>()
    val navigateToTracker: LiveData<Boolean?>
        get() =_navigateToTracker

    init {
        workday = database.getWorkdayWithId(workdayKey)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun doneNavigating() {
        _navigateToTracker.value = null
    }

    fun onClose() {
        _navigateToTracker.value = true
    }

}