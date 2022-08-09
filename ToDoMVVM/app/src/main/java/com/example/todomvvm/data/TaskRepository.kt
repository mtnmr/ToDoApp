package com.example.todomvvm.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    val allTask : Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun insertTask(task: Task){
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task:Task){
        taskDao.deleteTask(task)
    }
}