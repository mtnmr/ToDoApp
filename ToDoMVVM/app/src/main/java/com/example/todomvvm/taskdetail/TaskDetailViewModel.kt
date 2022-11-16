package com.example.todomvvm.taskdetail

import androidx.lifecycle.*
import com.example.todomvvm.data.ITaskRepository
import com.example.todomvvm.data.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(private val repository: ITaskRepository):ViewModel() {

    private val _taskId = MutableLiveData<Int>()

    fun updateTaskId(id:Int){
        _taskId.value = id
    }

    val currentTask : LiveData<Task> = _taskId.switchMap {
        repository.getTask(it).asLiveData()
    }

    fun getTask(id:Int):LiveData<Task> = repository.getTask(id).asLiveData()
}