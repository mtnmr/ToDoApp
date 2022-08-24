package com.example.todocompose.addedit

import androidx.lifecycle.*
import com.example.todocompose.data.Task
import com.example.todocompose.data.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskAddViewModel @Inject constructor(private val repository: TaskRepository): ViewModel() {

    private val _taskId = MutableLiveData<Int>()

    fun updateTaskId(id:Int){
        _taskId.value = id
    }

    val currentTask : LiveData<Task> = _taskId.switchMap {
        repository.getTask(it).asLiveData()
    }

    fun addOrEditTask(id:Int, title:String, description: String){
        if(title == ""){
            return
        }

        if(id != -1){
            editTask(id, title, description, currentTask.value!!.isChecked)
        }else{
            addTask(title, description)
        }
    }


    private fun addTask(
        title:String,
        description:String,
        isChecked : Boolean = false
    ){
        val newTask = getNewTask(title, description, isChecked)
        insertTask(newTask)
    }

    private fun getNewTask(
        title:String,
        description:String,
        isChecked : Boolean = false
    ): Task {
        return Task(title = title, description = description, isChecked = isChecked)
    }


    private fun insertTask(task: Task){
        viewModelScope.launch {
            repository.insertTask(task)
        }
    }

    private fun editTask(
        id:Int,
        title:String,
        description:String,
        isChecked : Boolean
    ){
        val newTask = getUpdatedTask(id, title, description, isChecked)
        updateTask(newTask)
    }

    private fun getUpdatedTask(
        id:Int,
        title:String,
        description:String,
        isChecked : Boolean
    ): Task {
        return Task(id = id, title = title, description = description, isChecked = isChecked)
    }


    private fun updateTask(task: Task){
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }
}