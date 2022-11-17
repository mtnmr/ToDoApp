package com.example.todomvvm.statistics

import androidx.lifecycle.*
import com.example.todomvvm.data.ITaskRepository
import com.example.todomvvm.data.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(private val repository: ITaskRepository) :
    ViewModel() {
//
//    private var _allTask = MutableLiveData<List<Task>>()
//    val allTasks: LiveData<List<Task>> = _allTask
//
//    init {
//        viewModelScope.launch {
//            repository.allTask.collect{
//                _allTask.value = it
//            }
//        }
//    }

    val allTasks: LiveData<List<Task>> = repository.allTask.asLiveData()

    private val stats : LiveData<StatsResult> = allTasks.map {
        getActiveAndCompletedTask(it)
    }

    val activeTaskPercent:LiveData<Float> = stats.map { it.activeTaskPercent }
    val completedTaskPercent:LiveData<Float> = stats.map { it.completedTaskPercent }

    fun getActiveAndCompletedTask(tasks: List<Task>?) :StatsResult{

        return if (tasks == null || tasks.isEmpty()){
            StatsResult(0f, 0f)
        }else{
            val taskSize = tasks.size
            val completedTaskSize = tasks.count {it.isChecked}
            StatsResult(
                activeTaskPercent =  100f * (taskSize - completedTaskSize) / taskSize,
                completedTaskPercent = 100f * completedTaskSize / taskSize
            )
        }
    }
}

data class StatsResult(
    val activeTaskPercent: Float,
    val completedTaskPercent: Float
)