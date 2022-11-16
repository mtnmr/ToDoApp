package com.example.todomvvm

import com.example.todomvvm.data.ITaskRepository
import com.example.todomvvm.data.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeRepository :ITaskRepository {

    override val allTask: Flow<List<Task>> = flow {
        listOf(
            Task(id=1, title = "sample", isChecked = false),
            Task(id=2, title = "sample", isChecked = false),
            Task(id=3, title = "sample", isChecked = true),
            Task(id=4, title = "sample", isChecked = true),
            Task(id=5, title = "sample", isChecked = true),
        )
    }

    override fun getTask(id: Int): Flow<Task> {
        return if(id == 1){
            flowOf(Task(id = 1, title = "test task"))
        }else{
            flowOf(Task(id = 2, title = "test task2"))
        }
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