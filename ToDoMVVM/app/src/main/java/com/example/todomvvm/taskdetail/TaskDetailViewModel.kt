package com.example.todomvvm.taskdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.todomvvm.data.Task
import com.example.todomvvm.data.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(private val repository: TaskRepository):ViewModel() {

    fun getTask(id:Int):LiveData<Task> = repository.getTask(id).asLiveData()

}