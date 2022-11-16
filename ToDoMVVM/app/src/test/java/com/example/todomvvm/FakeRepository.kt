package com.example.todomvvm

import com.example.todomvvm.data.ITaskRepository
import com.example.todomvvm.data.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository :ITaskRepository {

    override val allTask: Flow<List<Task>> = flow {  }

    override fun getTask(id: Int): Flow<Task> {
        TODO("Not yet implemented")
    }

    override suspend fun updateChecked(id: Int, isChecked: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun insertTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(task: Task) {
        TODO("Not yet implemented")
    }
}