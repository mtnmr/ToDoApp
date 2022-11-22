package com.example.todomvvm.di

import com.example.todomvvm.data.ITaskRepository
import com.example.todomvvm.data.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeRepository @Inject constructor():ITaskRepository {

//    val items = mutableListOf<Task>()

    override val allTask: Flow<List<Task>> = flowOf(
        listOf(
            Task(id=1, title = "task1", isChecked = false),
            Task(id=2, title = "task2", isChecked = false),
            Task(id=3, title = "task3", isChecked = true),
            Task(id=4, title = "task4", isChecked = true),
            Task(id=5, title = "task5", isChecked = true),
        )
    )

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