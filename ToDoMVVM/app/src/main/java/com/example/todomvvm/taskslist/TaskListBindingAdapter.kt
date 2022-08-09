package com.example.todomvvm.taskslist

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todomvvm.data.Task

@BindingAdapter("listData")
fun bindTaskRecyclerView(view:RecyclerView, data:List<Task>?){
    val adapter = view.adapter as TasksListAdapter
    adapter.submitList(data)
}