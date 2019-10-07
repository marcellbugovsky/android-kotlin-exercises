package com.android.example.trackmyworkdayquality.screens.tracker

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.example.trackmyworkdayquality.R
import com.android.example.trackmyworkdayquality.TextItemViewHolder
import com.android.example.trackmyworkdayquality.convertDurationToFormatted
import com.android.example.trackmyworkdayquality.convertNumericQualityToString
import com.android.example.trackmyworkdayquality.database.Workday

class WorkdayAdapter: RecyclerView.Adapter<WorkdayAdapter.ViewHolder>() {

    var data = listOf<Workday>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources
        holder.workdayLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
        holder.quality.text = convertNumericQualityToString(item.workdayQuality, res)
        holder.qualityImage.setImageResource(when (item.workdayQuality) {
            0 -> R.drawable.ic_quality_0
            1 -> R.drawable.ic_quality_1
            2 -> R.drawable.ic_quality_2
            3 -> R.drawable.ic_quality_3
            4 -> R.drawable.ic_quality_4
            else -> R.drawable.ic_workday_active
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_workday, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val workdayLength: TextView = itemView.findViewById(R.id.workday_length)
        val quality: TextView = itemView.findViewById(R.id.quality_string)
        val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)
    }

}