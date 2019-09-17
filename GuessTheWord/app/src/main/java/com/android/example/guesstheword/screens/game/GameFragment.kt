package com.android.example.guesstheword.screens.game


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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

        //Setting onClickListener
        binding.endGameButton.setOnClickListener{ onEndgame() }
        binding.correctButton.setOnClickListener{ onCorrect() }
        binding.skipButton.setOnClickListener{ onSkip() }

        updateScoreText()
        updateWordText()

        return binding.root
    }

    private fun onSkip() {
        viewModel.onSkip()
        updateScoreText()
        updateWordText()
    }

    private fun onCorrect() {
        viewModel.onCorrect()
        updateScoreText()
        updateWordText()
    }

    private fun updateWordText() {
        binding.wordText.text = viewModel.word
    }

    private fun updateScoreText() {
        binding.scoreText.text = viewModel.score.toString()
    }


    private fun onEndgame() {
        gameFinished()
    }

    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameFragmentToScoreFragment(viewModel.score)
        NavHostFragment.findNavController(this).navigate(action)
    }
}
