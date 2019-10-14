package com.android.example.trackmyworkdayquality.screens.tracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.example.trackmyworkdayquality.database.Workday
import com.android.example.trackmyworkdayquality.databinding.ListItemWorkdayBinding

class WorkdayAdapter(val clickListener: WorkdayListener) : ListAdapter<Workday, WorkdayAdapter.ViewHolder>(WorkdayDiffCallback()) {

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
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(
        val binding: ListItemWorkdayBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Workday,
            clickListener: WorkdayListener
        ) {
            binding.work = item
            binding.executePendingBindings()
            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemWorkdayBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

}

class WorkdayListener(val clickListener: (dayId: Long) -> Unit) {
    fun onClick(day: Workday) = clickListener(day.dayId)
}