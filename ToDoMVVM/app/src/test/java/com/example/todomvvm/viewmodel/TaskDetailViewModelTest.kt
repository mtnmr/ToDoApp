package com.example.todomvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.todomvvm.FakeRepository
import com.example.todomvvm.data.Task
import com.example.todomvvm.getOrAwaitValue
import com.example.todomvvm.taskdetail.TaskDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class TaskDetailViewModelTest {

    private lateinit var taskDetailViewModel: TaskDetailViewModel

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup(){
        taskDetailViewModel = TaskDetailViewModel(FakeRepository())
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun updateTaskId_to_changeCurrentTask() {
        taskDetailViewModel.updateTaskId(1)
        assertEquals(taskDetailViewModel.currentTask.getOrAwaitValue(),
            Task(id = 1, title = "test task")
        )
    }
}