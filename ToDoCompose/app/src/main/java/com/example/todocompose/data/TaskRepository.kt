package com.example.todocompose.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao): ITaskRepository {

    override val allTask : Flow<List<Task>> = taskDao.getAllTasks()

    override fun getTask(id:Int) : Flow<Task> = taskDao.getTask(id)

    override suspend fun updateChecked(id: Int, isChecked :Boolean){
        taskDao.updateChecked(id, isChecked)
    }

    override suspend fun insertTask(task: Task){
        taskDao.insertTask(task)
    }

    override suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }

    override suspend fun deleteTask(task:Task){
        taskDao.deleteTask(task)
    }
}