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
        binding.gameViewModel = viewModel
        // Specify the current activity as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = this

        // LiveData Observation for gameFinished
        viewModel.eventGameFinish.observe(this, Observer {hasFinished ->
            if (hasFinished) gameFinished()
        })

        return binding.root
    }

    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameFragmentToScoreFragment()
        action.score = viewModel.score.value?:0
        NavHostFragment.findNavController(this).navigate(action)
    }
}
