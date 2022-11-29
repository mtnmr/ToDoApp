package com.example.todocompose.taskdetail

import androidx.lifecycle.*
import com.example.todocompose.data.ITaskRepository
import com.example.todocompose.data.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(private val repository: ITaskRepository) :
    ViewModel() {

    private val _taskId = MutableLiveData<Int>()

    fun updateTaskId(id: Int) {
        _taskId.value = id
    }

    val currentTask: LiveData<Task> = _taskId.switchMap {
        repository.getTask(it).asLiveData()
    }

    fun getTask(id: Int): LiveData<Task> = repository.getTask(id).asLiveData()

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}