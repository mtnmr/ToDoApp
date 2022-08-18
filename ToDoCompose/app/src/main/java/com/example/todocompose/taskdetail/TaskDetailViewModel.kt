package com.example.todocompose.taskdetail

import androidx.lifecycle.*
import com.example.todocompose.data.Task
import com.example.todocompose.data.TaskRepository
import javax.inject.Inject

class TaskDetailViewModel @Inject constructor(private val repository: TaskRepository):ViewModel() {

    private val _taskId = MutableLiveData<Int>()

    fun updateTaskId(id:Int){
        _taskId.value = id
    }

    val currentTask : LiveData<Task> = _taskId.switchMap {
        repository.getTask(it).asLiveData()
    }

    fun getTask(id:Int):LiveData<Task> = repository.getTask(id).asLiveData()
}