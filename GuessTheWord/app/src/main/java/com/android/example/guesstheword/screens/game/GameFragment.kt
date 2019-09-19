package com.android.example.guesstheword.screens.game


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.android.example.guesstheword.R
import com.android.example.guesstheword.databinding.GameFragmentBinding
import com.android.example.guesstheword.misc.ResourceProvider

/**
 * A simple [Fragment] subclass.
 *
 */
class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var binding: GameFragmentBinding

    private lateinit var resourceProvider: ResourceProvider

    override fun onAttach(context: Context) {
        super.onAttach(context)
        resourceProvider = ResourceProvider(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.game_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        viewModel.initialize(resourceProvider)

        // LiveData Observation for score and word
        viewModel.score.observe(this, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })
        viewModel.word.observe(this, Observer { newWord ->
            binding.wordText.text = newWord
        })

        //Setting onClickListener
        binding.endGameButton.setOnClickListener{ onEndgame() }
        binding.correctButton.setOnClickListener{ onCorrect() }
        binding.skipButton.setOnClickListener{ onSkip() }

        return binding.root
    }

    private fun onSkip() {
        viewModel.onSkip()
    }

    private fun onCorrect() {
        viewModel.onCorrect()
    }


    private fun onEndgame() {
        gameFinished()
    }

    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameFragmentToScoreFragment()
        action.score = viewModel.score.value?:0
        NavHostFragment.findNavController(this).navigate(action)
    }
}
