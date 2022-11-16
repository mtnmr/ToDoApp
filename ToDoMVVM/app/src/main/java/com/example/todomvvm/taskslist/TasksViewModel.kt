package com.example.todomvvm.taskslist


import androidx.lifecycle.*
import com.example.todomvvm.R
import com.example.todomvvm.data.ITaskRepository
import com.example.todomvvm.data.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(private val repository: ITaskRepository) : ViewModel() {

    private var _taskFilter = MutableLiveData<Int>(R.string.all_task)
    val taskFilter:LiveData<Int> = _taskFilter

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

val sampleList = listOf(
    Task(id = 1, title = "task1"),
    Task(id = 2, title = "task2"),
    Task(id = 3, title = "task3")
)
