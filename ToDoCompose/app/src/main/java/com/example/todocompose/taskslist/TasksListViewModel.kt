package com.example.todocompose.taskslist

import androidx.lifecycle.*
import com.example.todocompose.R
import com.example.todocompose.data.Task
import com.example.todocompose.data.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TasksListViewModel@Inject constructor(private val repository: TaskRepository):ViewModel() {

    private var _taskFilter = MutableLiveData<Int>(R.string.all_task)
    val taskFilter: LiveData<Int> = _taskFilter

    val allTasks : LiveData<List<Task>> = repository.allTask.asLiveData()

    fun updateChecked(id:Int, isChecked:Boolean){
        viewModelScope.launch {
            repository.updateChecked(id, isChecked)
        }
    }

    fun taskFilterChange(taskFilter: TaskFilter){
        _taskFilter.value =
            when(taskFilter){
                TaskFilter.ALL_TASK -> R.string.all_task
                TaskFilter.COMPLETED_TASK -> R.string.completed_task
                TaskFilter.ACTIVE_TASK -> R.string.active_task
            }
    }
}

enum class TaskFilter{
    ALL_TASK,
    COMPLETED_TASK,
    ACTIVE_TASK
}