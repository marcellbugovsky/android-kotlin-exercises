package com.android.example.trackmyworkdayquality.screens.tracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.android.example.trackmyworkdayquality.database.DatabaseDao
import com.android.example.trackmyworkdayquality.database.Workday
import com.android.example.trackmyworkdayquality.formatDays
import kotlinx.coroutines.*

class TrackerViewModel(
        val database: DatabaseDao,
        application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val days = database.getAllDays()
    val daysString = Transformations.map(days) { days ->
        formatDays(days, application.resources)
    }
    private var today = MutableLiveData<Workday?>()

    private val _navigateToSleepQuality = MutableLiveData<Workday>()
    val navigateToSleepQuality: LiveData<Workday>
        get() = _navigateToSleepQuality

    init {
        initializeToday()
    }

    private fun initializeToday() {
        uiScope.launch {
            today.value = getTodayFromDatabase()
        }
    }

    private suspend fun getTodayFromDatabase(): Workday? {
        // Use IO Dispatcher because this is an IO Operation not UI
        return withContext(Dispatchers.IO) {
            var day: Workday? = database.getToday()
            if (day?.endTimeMilli != day?.startTimeMilli) {
                day = null
            }
            day
        }
    }

    fun onStartTracking() {
        uiScope.launch {
            val newDay = Workday()
            insert(newDay)
            today.value = getTodayFromDatabase()
        }
    }

    fun onStopTracking() {
        uiScope.launch {
            val oldDay = today.value ?: return@launch
            oldDay.endTimeMilli = System.currentTimeMillis()
            _navigateToSleepQuality.value = oldDay
            update(oldDay)
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
            today.value = null
        }
    }

    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    private suspend fun insert(day: Workday) {
        withContext(Dispatchers.IO) {
            database.insert(day)
        }
    }

    private suspend fun update(day: Workday) {
        withContext(Dispatchers.IO) {
            database.update(day)
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}