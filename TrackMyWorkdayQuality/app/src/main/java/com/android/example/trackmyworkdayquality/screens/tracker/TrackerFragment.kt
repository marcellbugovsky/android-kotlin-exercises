package com.android.example.trackmyworkdayquality.screens.tracker


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.android.example.trackmyworkdayquality.R
import com.android.example.trackmyworkdayquality.database.WorkDatabase
import com.android.example.trackmyworkdayquality.database.Workday
import com.android.example.trackmyworkdayquality.databinding.TrackerFragmentBinding
import com.google.android.material.snackbar.Snackbar


/**
 * A simple [Fragment] subclass.
 */
class TrackerFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
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

        trackerViewModel.showSnackbarEvent.observe(this, Observer {
            if (it == true) {
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    getString(R.string.cleared_message),
                    Snackbar.LENGTH_SHORT
                ).show()
                trackerViewModel.doneShowSnackbarEvent()
            }
        })

        val adapter = WorkdayAdapter(WorkdayListener { dayId ->
            Toast.makeText(context, dayId.toString(), Toast.LENGTH_LONG).show()
            trackerViewModel.onWorkdayClicked(dayId)
        })

        binding.workdayList.adapter = adapter

        trackerViewModel.days.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        trackerViewModel.navigateToWorkdayDetail.observe(this, Observer { workday ->
            workday?.let {
                findNavController(this).navigate(TrackerFragmentDirections.actionTrackerFragmentToDetailFragment(workday))
                trackerViewModel.onWorkdayDetailNavigated()
            }
        })

        val manager = GridLayoutManager(activity, 3)
        binding.workdayList.layoutManager = manager

        return binding.root
    }


}