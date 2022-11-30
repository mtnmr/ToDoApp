package com.example.todocompose.di

import com.example.todocompose.data.ITaskRepository
import com.example.todocompose.data.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeRepository @Inject constructor():ITaskRepository {

    val items = mutableListOf<Task>()

    private val flow = MutableStateFlow<List<Task>>(emptyList())

    override val allTask: Flow<List<Task>> = flow
//        flowOf(
//        listOf(
//            Task(id=1, title = "task1", isChecked = false),
//            Task(id=2, title = "task2", isChecked = false),
//            Task(id=3, title = "task3", isChecked = true),
//            Task(id=4, title = "task4", isChecked = true),
//            Task(id=5, title = "task5", isChecked = true),
//        )
//    )

    override fun getTask(id: Int): Flow<Task> {
        return flowOf( items.find { it.id == id } ?: Task(id = -1, title = "Default Task") )
    }

    override suspend fun updateChecked(id: Int, isChecked: Boolean) {
        val newItems:List<Task> = items.map {
            if(it.id == id){
                Task(id = it.id, title = it.title, description = it.description, isChecked = isChecked)
            }else{
                it
            }
        }
        flow.emit(newItems)
    }

    override suspend fun insertTask(task: Task) {
        items.add(task)
        flow.emit(items)
    }

    override suspend fun updateTask(task: Task) {
        val newItems:List<Task> = items.map {
            if(it.id == task.id){
                task
            }else{
                it
            }
        }
        flow.emit(newItems)
    }

    override suspend fun deleteTask(task: Task) {
        items.remove(task)
        flow.emit(items)
    }
}