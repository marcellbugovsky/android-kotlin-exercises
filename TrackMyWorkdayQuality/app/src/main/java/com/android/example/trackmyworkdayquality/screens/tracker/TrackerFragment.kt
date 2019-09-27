package com.android.example.trackmyworkdayquality.screens.tracker


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.android.example.trackmyworkdayquality.R
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

        return binding.root
    }


}