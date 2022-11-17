package com.example.todomvvm

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.todomvvm.data.Task
import com.example.todomvvm.data.TaskDao
import com.example.todomvvm.data.TaskDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var taskDao:TaskDao
    private lateinit var db:TaskDatabase

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            TaskDatabase::class.java
        ).build()
        taskDao = db.taskDao()
    }

    @After
    fun closeDb(){
        db.close()
    }

    @Test
    fun insertTask_readList() = runBlocking {
        val task1 = Task(id = 1, title = "test task")
        taskDao.insertTask(task1)

        val task2 = Task(id = 2, title = "test task")
        taskDao.insertTask(task2)

        val allTasks = taskDao.getAllTasks().first()
        assertEquals(allTasks, listOf(Task(id = 1, title = "test task"), Task(id = 2, title = "test task")))
    }

    @Test
    fun insertTask_getTaskById() = runBlocking{
        val task = Task(id = 1, title = "test task")
        taskDao.insertTask(task)

        val getTask = taskDao.getTask(1).first()
        assertEquals(getTask, Task(id = 1, title = "test task"))
    }
}