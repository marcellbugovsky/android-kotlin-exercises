package com.android.example.trackmyworkdayquality.screens.detail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.example.trackmyworkdayquality.R
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
        val arguments = DetailFragmentArgs.fromBundle(arguments)
        // TODO correct this bullshit

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

}
