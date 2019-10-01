package com.android.example.trackmyworkdayquality.screens.tracker


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.android.example.trackmyworkdayquality.R
import com.android.example.trackmyworkdayquality.database.WorkDatabase
import com.android.example.trackmyworkdayquality.databinding.TrackerFragmentBinding


/**
 * A simple [Fragment] subclass.
 */
class TrackerFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val binding: TrackerFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.tracker_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = WorkDatabase.getInstance(application).databaseDao
        val viewModelFactory = TrackerViewModelFactory(dataSource, application)
        val trackerViewModel = ViewModelProviders.of(this, viewModelFactory).get(TrackerViewModel::class.java)
        binding.trackerViewModel = trackerViewModel

        trackerViewModel.navigateToSleepQuality.observe(this, Observer {
            day ->
            day?.let {
                  findNavController(this).navigate(
                      TrackerFragmentDirections.actionTrackerFragmentToQualityFragment(day.dayId))
                        trackerViewModel.doneNavigating()
            }
        })
        binding.setLifecycleOwner(this)

        return binding.root
    }


}