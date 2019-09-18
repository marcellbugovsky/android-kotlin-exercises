package com.android.example.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.ViewModel
import com.android.example.guesstheword.R
import com.android.example.guesstheword.misc.ResourceProvider

class GameViewModel : ViewModel() {

    // Resource Provider for the wordList
    private lateinit var resourceProvider: ResourceProvider
    // Current word
    var word = ""
    // Current score
    var score = 0
    // List of words
    private lateinit var wordList: MutableList<String>
    // Boolean if initial
    private var initial = true

    init {
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
            word = wordList.removeAt(0)
        }
    }

    fun onSkip() {
        if (!wordList.isEmpty()) {
            score--
        }
        nextWord()
    }

    fun onCorrect() {
        if (!wordList.isEmpty()) {
            score++
        }
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }
}