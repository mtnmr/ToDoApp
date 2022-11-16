package com.example.todomvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.todomvvm.FakeRepository
import com.example.todomvvm.R
import com.example.todomvvm.getOrAwaitValue
import com.example.todomvvm.taskslist.TaskFilter
import com.example.todomvvm.taskslist.TasksViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class TasksViewModelTest {

    lateinit var tasksViewModel: TasksViewModel

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        tasksViewModel = TasksViewModel(FakeRepository())
    }

    @Test
    fun taskFilterChange_allTask_to_activeTask(){

        tasksViewModel.taskFilterChange(TaskFilter.ACTIVE_TASK)

        assertEquals(tasksViewModel.taskFilter.getOrAwaitValue(), R.string.active_task)

        /*  LiveDataのUtilを使わなかった場合
        val observer = Observer<Int>{}

        try {
            tasksViewModel.taskFilter.observeForever(observer)
            tasksViewModel.taskFilterChange(TaskFilter.ACTIVE_TASK)

            val value = tasksViewModel.taskFilter.value
            assertEquals(value, R.string.active_task)
        }finally {
            tasksViewModel.taskFilter.removeObserver(observer)
        }

         */
    }
}