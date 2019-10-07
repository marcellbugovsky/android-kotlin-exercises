package com.android.example.trackmyworkdayquality.screens.tracker

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.example.trackmyworkdayquality.R
import com.android.example.trackmyworkdayquality.TextItemViewHolder
import com.android.example.trackmyworkdayquality.database.Workday

class WorkdayAdapter: RecyclerView.Adapter<TextItemViewHolder>() {

    var data = listOf<Workday>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.workdayQuality.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.text_item_view, parent, false) as TextView
        return TextItemViewHolder(view)
    }

}