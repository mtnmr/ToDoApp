package com.example.todomvvm

import com.example.todomvvm.taskslist.TasksViewModel
import org.junit.Before

class TasksViewModelTest {

    lateinit var tasksViewModel: TasksViewModel

    @Before
    fun setup(){
        tasksViewModel = TasksViewModel(FakeRepository())
    }
}