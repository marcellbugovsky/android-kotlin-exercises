package com.android.example.trackmyworkdayquality.screens.tracker

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.example.trackmyworkdayquality.R
import com.android.example.trackmyworkdayquality.database.Workday
import com.android.example.trackmyworkdayquality.databinding.ListItemWorkdayBinding

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class WorkdayAdapter(val clickListener: WorkdayListener) : ListAdapter<DataItem, RecyclerView.ViewHolder>(WorkdayDiffCallback()) {

    class WorkdayDiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (holder) {
            is ViewHolder -> {
                val item = getItem(position) as DataItem.WorkdayItem
                holder.bind(item.workday, clickListener)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType @{viewType}")
        }
    }

    fun addHeaderAndSubmitList(list: List<Workday>?) {
        val items = when (list) {
            null -> listOf(DataItem.Header)
            else -> listOf(DataItem.Header) + list.map { DataItem.WorkdayItem(it) }
        }
        submitList(items)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.WorkdayItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header, parent, false)
                return TextViewHolder(view)
            }
        }
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

sealed class DataItem {
    abstract val id: Long

    data class WorkdayItem(val workday: Workday): DataItem() {
        override val id = workday.dayId
    }

    object Header   : DataItem() {
        override val id = Long.MIN_VALUE
    }
}