package com.android.example.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    init {
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
            onGameFinish()
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
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }
}