package com.example.todomvvm.taskslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todomvvm.data.Task

class TasksViewModel : ViewModel() {


}

val sampleList = listOf(
    Task(id = 1, title = "task1"),
    Task(id = 2, title = "task2"),
    Task(id = 3, title = "task3")
)