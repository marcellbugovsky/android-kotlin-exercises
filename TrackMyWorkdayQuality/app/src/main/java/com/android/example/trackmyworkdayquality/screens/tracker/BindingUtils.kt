package com.android.example.trackmyworkdayquality.screens.tracker

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.android.example.trackmyworkdayquality.R
import com.android.example.trackmyworkdayquality.convertDurationToFormatted
import com.android.example.trackmyworkdayquality.convertNumericQualityToString
import com.android.example.trackmyworkdayquality.database.Workday

@BindingAdapter("workDurationFormatted")
fun TextView.setWorkDurationFormatted(item: Workday?) {
    item?.let {
        text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, context.resources)
    }
}

@BindingAdapter("workQualityString")
fun TextView.setWorkQualityString(item: Workday?) {
    item?.let {
        text = convertNumericQualityToString(item.workdayQuality, context.resources)
    }
}

@BindingAdapter("qualityImage")
fun ImageView.setQualityImage(item: Workday?) {
    item?.let {
        setImageResource(
            when (item.workdayQuality) {
                0 -> R.drawable.ic_quality_0
                1 -> R.drawable.ic_quality_1
                2 -> R.drawable.ic_quality_2
                3 -> R.drawable.ic_quality_3
                4 -> R.drawable.ic_quality_4
                else -> R.drawable.ic_workday_active
            }
        )
    }
}