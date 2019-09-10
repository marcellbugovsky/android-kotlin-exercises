package com.android.example.guesstheword.fragment


import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.android.example.guesstheword.R
import com.android.example.guesstheword.databinding.GameFragmentBinding

/**
 * A simple [Fragment] subclass.
 *
 */
class GameFragment : Fragment() {

    private lateinit var res: Resources

    //Current word and score
    private var word = ""
    private var score = 0

    //Lateinit for wordList and binding
    private lateinit var wordList: MutableList<String>
    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.game_fragment, container, false)
        res = getResources()

        //Setting onClickListener
        binding.endGameButton.setOnClickListener{
            view: View->
            view.findNavController().navigate(R.id.action_gameFragment_to_scoreFragment)
        }
        binding.correctButton.setOnClickListener{ onCorrect() }
        binding.skipButton.setOnClickListener{ onSkip() }

        resetList()
        nextWord()

        return binding.root
    }

    //Setting the wordList and shuffling
    private fun resetList() {
        wordList = res.getStringArray( R.array.wordList ).toMutableList()
        wordList.shuffle()
    }

    private fun nextWord() {
        if (!wordList.isEmpty()) {
            word = wordList.removeAt(0)
        }
        updateWordText()
        updateScoreText()
    }

    private fun onSkip() {
        if (!wordList.isEmpty()) {
            score--
        }
        nextWord()
    }

    private fun onCorrect() {
        if (!wordList.isEmpty()) {
            score++
        }
        nextWord()
    }

    private fun updateWordText() {
        binding.wordText.text = word
    }

    private fun updateScoreText() {
        binding.scoreText.text = score.toString()
    }

}
