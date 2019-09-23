package com.android.example.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.android.example.guesstheword.R
import com.android.example.guesstheword.misc.ResourceProvider

class GameViewModel : ViewModel() {

    // Resource Provider for the wordList
    private lateinit var resourceProvider: ResourceProvider
    // Current word
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word
    // Current score
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score
    // List of words
    private lateinit var wordList: MutableList<String>
    // Boolean if initial
    private var initial = true
    // Event which triggers the end of the game
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish
    // Countdown Timer
    companion object{

        // Time when the game is over
        private const val DONE = 0L
        // Countdown time interval
        private const val ONE_SECOND = 1000L
        // Total time for the game
        private const val COUNTDOWN_TIME = 60000L

    }
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime
    private val timer: CountDownTimer
    // The string version of currentTime
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    init {
        // Initializing timer object
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished/ ONE_SECOND
            }

            override fun onFinish() {
                _currentTime.value = DONE
                onGameFinish()
            }
        }
        timer.start()

        _word.value = ""
        _score.value = 0
    }

    fun initialize(resourceProvider: ResourceProvider) {
        this.resourceProvider = resourceProvider
        if(initial) {
            resetList()
            nextWord()
            initial = false
        }
    }

    private fun resetList() {
        wordList = resourceProvider.getArrayString(R.array.wordList).toMutableList()
        wordList.shuffle()
    }

    private fun nextWord() {
        if (!wordList.isEmpty()) {
            _word.value = wordList.removeAt(0)
        } else {
            resetList()
        }
    }

    fun onSkip() {
        if (!wordList.isEmpty()) {
            _score.value = (score.value)?.minus(1)
        }
        nextWord()
    }

    fun onCorrect() {
        if (!wordList.isEmpty()) {
            _score.value = (score.value)?.plus(1)
        }
        nextWord()
    }

    fun onGameFinish() {
        _eventGameFinish.value = true
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }
}