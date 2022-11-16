package com.example.todomvvm.data

import kotlinx.coroutines.flow.Flow

interface ITaskRepository {

    val allTask : Flow<List<Task>>

    fun getTask(id:Int) : Flow<Task>

    suspend fun updateChecked(id: Int, isChecked :Boolean)

    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(task:Task)
}