package com.android.example.trackmyworkdayquality.screens.quality


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
import com.android.example.trackmyworkdayquality.databinding.QualityFragmentBinding


/**
 * A simple [Fragment] subclass.
 */
class QualityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: QualityFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.quality_fragment, container, false)

        val application = requireNotNull(this.activity).application

        val arguments = QualityFragmentArgs.fromBundle(arguments!!)
        val dataSource = WorkDatabase.getInstance(application).databaseDao

        val viewModelFactory = QualityViewModelFactory(arguments.workdayKey, dataSource)
        val qualityViewModel = ViewModelProviders.of(this, viewModelFactory).get(QualityViewModel::class.java)
        binding.qualityViewModel = qualityViewModel

        qualityViewModel.navigateToTracker.observe(this, Observer {
            if (it == true) {
                findNavController(this).navigate(
                    QualityFragmentDirections.actionQualityFragmentToTrackerFragment())
                qualityViewModel.doneNavigating()
            }
        })

        return binding.root
    }


}
