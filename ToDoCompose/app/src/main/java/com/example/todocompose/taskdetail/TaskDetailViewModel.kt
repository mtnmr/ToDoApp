package com.example.todocompose.taskdetail

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todocompose.data.Task
import com.example.todocompose.data.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(private val repository: TaskRepository) :
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