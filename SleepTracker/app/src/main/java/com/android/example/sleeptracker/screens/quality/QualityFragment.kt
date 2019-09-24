package com.android.example.sleeptracker.screens.quality


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.android.example.sleeptracker.R

/**
 * A simple [Fragment] subclass.
 */
class QualityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.quality_fragment, container, false)
    }


}
