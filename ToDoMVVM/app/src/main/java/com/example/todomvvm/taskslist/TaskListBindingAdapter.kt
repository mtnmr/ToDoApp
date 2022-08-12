package com.example.todomvvm.taskslist

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todomvvm.R
import com.example.todomvvm.data.Task

@BindingAdapter("listData","filterId")
fun bindTaskRecyclerView(view:RecyclerView, data:List<Task>?, filterId:Int){
    val adapter = view.adapter as TasksListAdapter

    val toShowTask = ArrayList<Task>()

    if (data != null) {
        for (task in data){
            when(filterId){
                R.string.active_task -> if(!task.isChecked){
                    toShowTask.add(task)
                }
                R.string.completed_task -> if(task.isChecked){
                    toShowTask.add(task)
                }
                else -> toShowTask.add(task)
            }
        }
    }

    adapter.submitList(toShowTask)
}