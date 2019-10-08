package com.android.example.trackmyworkdayquality.screens.tracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.example.trackmyworkdayquality.R
import com.android.example.trackmyworkdayquality.convertDurationToFormatted
import com.android.example.trackmyworkdayquality.convertNumericQualityToString
import com.android.example.trackmyworkdayquality.database.Workday

class WorkdayAdapter : ListAdapter<Workday, WorkdayAdapter.ViewHolder>(WorkdayDiffCallback()) {

    class WorkdayDiffCallback : DiffUtil.ItemCallback<Workday>() {
        override fun areItemsTheSame(oldItem: Workday, newItem: Workday): Boolean {
            return oldItem.dayId == newItem.dayId
        }

        override fun areContentsTheSame(oldItem: Workday, newItem: Workday): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {
        val workdayLength: TextView = itemView.findViewById(R.id.workday_length)
        val quality: TextView = itemView.findViewById(R.id.quality_string)
        val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)

        fun bind(
            item: Workday
        ) {
            val res = itemView.context.resources
            workdayLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            quality.text = convertNumericQualityToString(item.workdayQuality, res)
            qualityImage.setImageResource(
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

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.list_item_workday, parent, false)
                return ViewHolder(view)
            }
        }

    }

}