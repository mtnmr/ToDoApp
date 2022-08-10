package com.example.todomvvm.taskslist

import androidx.lifecycle.*
import com.example.todomvvm.data.Task
import com.example.todomvvm.data.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel() {

    val allTasks : LiveData<List<Task>> = repository.allTask.asLiveData()

    fun updateChecked(id:Int, isChecked:Boolean){
        viewModelScope.launch {
            repository.updateChecked(id, isChecked)
        }
    }
}

val sampleList = listOf(
    Task(id = 1, title = "task1"),
    Task(id = 2, title = "task2"),
    Task(id = 3, title = "task3")
)