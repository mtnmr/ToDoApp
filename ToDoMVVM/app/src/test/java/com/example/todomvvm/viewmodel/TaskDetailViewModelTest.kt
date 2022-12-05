package com.example.todomvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.todomvvm.FakeRepository
import com.example.todomvvm.MainDispatcherRule
import com.example.todomvvm.data.ITaskRepository
import com.example.todomvvm.data.Task
import com.example.todomvvm.data.TaskRepository
import com.example.todomvvm.getOrAwaitValue
import com.example.todomvvm.taskdetail.TaskDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class TaskDetailViewModelTest {

    private lateinit var taskDetailViewModel: TaskDetailViewModel

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule: TestRule = MainDispatcherRule()

    @Before
    fun setup(){
        taskDetailViewModel = TaskDetailViewModel(FakeRepository())
    }

    @Test
    fun updateTaskId_to_changeCurrentTask() {
        taskDetailViewModel.updateTaskId(1)
        assertEquals(taskDetailViewModel.currentTask.getOrAwaitValue(),
            Task(id = 1, title = "test task")
        )
    }
}

/*mockitoを使った場合
@OptIn(ExperimentalCoroutinesApi::class)
class TaskDetailViewModelTest {

    private lateinit var taskDetailViewModel: TaskDetailViewModel

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule: TestRule = MainDispatcherRule()

    private lateinit var repository: TaskRepository

    @Before
    fun setup(){
        repository = mock(){
            on { getTask(any()) } doReturn flowOf(Task(id = 1, title = "test task"))
        }
        taskDetailViewModel = TaskDetailViewModel(repository)
    }

    @Test
    fun updateTaskId_to_changeCurrentTask() {
        taskDetailViewModel.updateTaskId(1)

        assertEquals(taskDetailViewModel.currentTask.getOrAwaitValue(),
            Task(id = 1, title = "test task")
        )

        verify(repository, times(1)).getTask(any())
    }
}

 */