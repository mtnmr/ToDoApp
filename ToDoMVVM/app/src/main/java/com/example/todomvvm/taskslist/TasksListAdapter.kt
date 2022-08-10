package com.example.todomvvm.taskslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todomvvm.data.Task
import com.example.todomvvm.databinding.TasksItemBinding
import com.example.todomvvm.taskslist.TasksListAdapter.TaskViewHolder

class TasksListAdapter(
    private val tasksViewModel:TasksViewModel,
    private val onItemClicked: (Task) -> Unit
) : ListAdapter<Task, TaskViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = TasksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = TaskViewHolder(view)

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.absoluteAdapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.binding.task = getItem(position)
        holder.binding.viewModel = tasksViewModel
    }

    class TaskViewHolder(val binding: TasksItemBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Task>() {
            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}