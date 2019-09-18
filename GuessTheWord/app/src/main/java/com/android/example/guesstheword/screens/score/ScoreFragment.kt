package com.android.example.guesstheword.screens.score


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.android.example.guesstheword.R
import com.android.example.guesstheword.databinding.ScoreFragmentBinding

/**
 * A simple [Fragment] subclass.
 *
 */
class ScoreFragment : Fragment() {

    private lateinit var binding: ScoreFragmentBinding
    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.score_fragment, container, false)
        viewModelFactory = ScoreViewModelFactory(ScoreFragmentArgs.fromBundle(arguments!!).score)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ScoreViewModel::class.java)
        binding.playAgainButton.setOnClickListener{
            view: View->
            view.findNavController().navigate(ScoreFragmentDirections.actionScoreFragmentToGameFragment())
        }

        binding.scoreText.text = viewModel.score.toString()
        return binding.root
    }


}
