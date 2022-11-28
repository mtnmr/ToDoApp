package com.example.todomvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.todomvvm.FakeRepository
import com.example.todomvvm.MainDispatcherRule
import com.example.todomvvm.addedit.TaskAddViewModel
import com.example.todomvvm.data.Task
import com.example.todomvvm.getOrAwaitValue
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class TaskAddViewModelTest {
    private lateinit var taskAddViewModel: TaskAddViewModel

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule: TestRule = MainDispatcherRule()

    @Before
    fun setup(){
        taskAddViewModel = TaskAddViewModel(FakeRepository())
    }

    @Test
    fun resetTaskTest(){
        taskAddViewModel.resetTaskText()
        assertEquals(taskAddViewModel.taskTitle.getOrAwaitValue(), "")
        assertEquals(taskAddViewModel.taskDescription.getOrAwaitValue(), "")
    }

    @Test
    fun setTaskTest(){
        val task = Task(id = 1, title = "test task", description = "sample task description")
        taskAddViewModel.setEditTask(task)
        assertEquals(taskAddViewModel.taskTitle.getOrAwaitValue(), "test task")
        assertEquals(taskAddViewModel.taskDescription.getOrAwaitValue(), "sample task description")
    }
}