package com.example.todocompose.addedit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todocompose.data.Task
import com.example.todocompose.data.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskAddViewModel @Inject constructor(private val repository: TaskRepository): ViewModel() {

    val taskTitle = MutableLiveData<String>("")
    val taskDescription = MutableLiveData<String>("")

    fun addTask(
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

    fun editTask(
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

    fun resetTaskText(){
        taskTitle.value = ""
        taskDescription.value = ""
    }

    fun setEditTask(task:Task){
        taskTitle.value = task.title
        taskDescription.value = task.description
    }
}