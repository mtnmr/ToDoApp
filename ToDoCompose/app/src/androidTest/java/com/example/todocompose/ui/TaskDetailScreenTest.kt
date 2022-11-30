package com.example.todocompose.ui

import androidx.compose.material.Surface
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.todocompose.HiltTestActivity
import com.example.todocompose.data.ITaskRepository
import com.example.todocompose.data.Task
import com.example.todocompose.taskdetail.TaskDetailScreen
import com.example.todocompose.taskdetail.TaskDetailViewModel
import com.example.todocompose.ui.theme.ToDoComposeTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class TaskDetailScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()
    private val activity get() = composeTestRule.activity

    @Inject
    lateinit var repository: ITaskRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun showDetailTask(){
        runBlocking {
            repository.insertTask(Task(id = 0, title = "TestTask", description = "test description"))
        }
        setContent(0)

        composeTestRule.onNodeWithText("TestTask").assertIsDisplayed()
        composeTestRule.onNodeWithText("test description").assertIsDisplayed()
    }

    private fun setContent(id:Int){
        composeTestRule.setContent {
            ToDoComposeTheme {
                Surface() {
                    TaskDetailScreen(
                        taskId = id,
                        onClick = { },
                        onBack = {  },
                        viewModel = TaskDetailViewModel(repository)
                    )
                }
            }
        }
    }
}
