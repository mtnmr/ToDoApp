package com.example.todomvvm.addedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todomvvm.data.Task
import com.example.todomvvm.data.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskAddViewModel @Inject constructor(private val repository: TaskRepository):ViewModel() {

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
}