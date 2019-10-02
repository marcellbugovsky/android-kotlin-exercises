package com.android.example.trackmyworkdayquality.screens.quality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.trackmyworkdayquality.database.DatabaseDao
import kotlinx.coroutines.*

class QualityViewModel(
    private val workdayKey: Long = 0L,
    val database: DatabaseDao
): ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToTracker = MutableLiveData<Boolean?>()
    val navigateToTracker: LiveData<Boolean?>
        get() = _navigateToTracker

    fun doneNavigating() {
        _navigateToTracker.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onSetWorkdayQuality(quality: Int) {
        uiScope.launch {
            // IO is a thread pool for running operation that access the disk, such as the Room database
            withContext(Dispatchers.IO) {
                val today = database.get(workdayKey) ?: return@withContext
                today.workdayQuality = quality
                database.update(today)
            }

            _navigateToTracker.value = true
        }
    }

}