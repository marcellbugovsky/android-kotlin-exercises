package com.android.example.trackmyworkdayquality.screens.detail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.android.example.trackmyworkdayquality.R
import com.android.example.trackmyworkdayquality.database.WorkDatabase
import com.android.example.trackmyworkdayquality.databinding.DetailFragmentBinding

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: DetailFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        val application = requireNotNull(this.activity).application
        val arguments = DetailFragmentArgs.fromBundle(arguments!!)

        // ViewModel
        val dataSource = WorkDatabase.getInstance(application).databaseDao
        val viewModelFactory = DetailViewModelFactory(arguments.workdayKey, dataSource)
        val detailViewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)

        binding.detailViewModel = detailViewModel
        binding.setLifecycleOwner(this)

        detailViewModel.navigateToTracker.observe(this, Observer {
            if (it == true) {
                findNavController(this).navigate(DetailFragmentDirections.actionDetailFragmentToTrackerFragment())
                detailViewModel.doneNavigating()
            }
        })

        return binding.root
    }

}
