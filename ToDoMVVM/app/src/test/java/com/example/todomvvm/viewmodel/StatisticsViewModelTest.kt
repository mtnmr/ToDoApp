package com.example.todomvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.todomvvm.FakeRepository
import com.example.todomvvm.data.Task
import com.example.todomvvm.getOrAwaitValue
import com.example.todomvvm.statistics.StatisticsViewModel
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
class StatisticsViewModelTest {

    private lateinit var viewModel:StatisticsViewModel

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup(){
        viewModel = StatisticsViewModel(FakeRepository())
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getActiveAndCompletedTask_emptyTasks_returnZeroZero(){
        val result = viewModel.getActiveAndCompletedTask(emptyList())

        assertEquals(result.activeTaskPercent, 0f)
        assertEquals(result.completedTaskPercent, 0f)
    }

    @Test
    fun getActiveAndCompletedTask_null_returnZeroZero(){
        val result = viewModel.getActiveAndCompletedTask(null)

        assertEquals(result.activeTaskPercent, 0f)
        assertEquals(result.completedTaskPercent, 0f)
    }

    @Test
    fun getActiveAndCompletedTask_tasks_returnFortySixty(){
        val tasks = listOf(
            Task(id=1, title = "sample", isChecked = false),
            Task(id=2, title = "sample", isChecked = false),
            Task(id=3, title = "sample", isChecked = true),
            Task(id=4, title = "sample", isChecked = true),
            Task(id=5, title = "sample", isChecked = true),
        )

        val result = viewModel.getActiveAndCompletedTask(tasks)

        assertEquals(result.activeTaskPercent, 40f)
        assertEquals(result.completedTaskPercent, 60f)
    }


    //Module with the Main dispatcher had failed to initializeになる？
    @Test
    fun activeAndCompletedTaskPercent(){

        assertEquals(viewModel.activeTaskPercent.getOrAwaitValue(), 40f)
        assertEquals(viewModel.completedTaskPercent.getOrAwaitValue(), 60f)
    }
}