package com.example.todocompose.taskslist


import androidx.lifecycle.*
import com.example.todocompose.R
import com.example.todocompose.data.ITaskRepository
import com.example.todocompose.data.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TasksListViewModel @Inject constructor(private val repository: ITaskRepository) : ViewModel() {

    val allTasks: LiveData<List<Task>> = repository.allTask.asLiveData()

    fun updateChecked(id: Int, isChecked: Boolean) {
        viewModelScope.launch {
            repository.updateChecked(id, isChecked)
        }
    }

    private var _taskFilter = MutableLiveData<Int>(R.string.all_task)
    val taskFilter: LiveData<Int> = _taskFilter

    fun taskFilterChange(taskFilter: TaskFilter) {
        _taskFilter.value =
            when (taskFilter) {
                TaskFilter.ALL_TASK -> R.string.all_task
                TaskFilter.COMPLETED_TASK -> R.string.completed_task
                TaskFilter.ACTIVE_TASK -> R.string.active_task
            }
    }

    val showTasksList: MediatorLiveData<List<Task>> = MediatorLiveData()

    init {
        showTasksList.addSource(taskFilter){
            val tasks = allTasks.value
            val filterType = it
            showTasksList.value = filterTask(tasks, filterType)
        }

        showTasksList.addSource(allTasks){
            val tasks = it
            val filterType = taskFilter.value
            showTasksList.value = filterTask(tasks, filterType)
        }
    }

    private fun filterTask(tasksList: List<Task>?, filter: Int?): List<Task> {
        val toShowTask = ArrayList<Task>()

        if (tasksList != null) {
            for (task in tasksList) {
                when (filter) {
                    R.string.active_task -> if (!task.isChecked) {
                        toShowTask.add(task)
                    }
                    R.string.completed_task -> if (task.isChecked) {
                        toShowTask.add(task)
                    }
                    else -> toShowTask.add(task)
                }
            }
        }

        return toShowTask
    }
}

enum class TaskFilter {
    ALL_TASK,
    COMPLETED_TASK,
    ACTIVE_TASK
}