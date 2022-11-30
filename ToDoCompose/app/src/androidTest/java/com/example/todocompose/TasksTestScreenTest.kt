package com.example.todocompose

import androidx.annotation.StringRes
import androidx.compose.material.Surface
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.todocompose.data.ITaskRepository
import com.example.todocompose.data.Task
import com.example.todocompose.taskslist.TasksListScreen
import com.example.todocompose.taskslist.TasksListViewModel
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
class TasksListScreenTest {

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
    fun displayAllTask() {
        setTaskItem()
        setContent()

        composeTestRule.onNodeWithText("All Tasks").assertIsDisplayed()
        composeTestRule.onNodeWithText("task1").assertIsDisplayed()
        composeTestRule.onNodeWithText("task2").assertIsDisplayed()
        composeTestRule.onNodeWithText("task3").assertIsDisplayed()
        composeTestRule.onNodeWithText("task4").assertIsDisplayed()
        composeTestRule.onNodeWithText("task5").assertIsDisplayed()
    }

    @Test
    fun changeTaskFilterActive_showOnlyActiveTask(){
        setTaskItem()
        setContent()

        taskFilterChange(R.string.active_task)

        composeTestRule.onNodeWithText("Active Tasks").assertIsDisplayed()
        composeTestRule.onNodeWithText("task1").assertIsDisplayed()
        composeTestRule.onNodeWithText("task2").assertIsDisplayed()
        composeTestRule.onNodeWithText("task3").assertDoesNotExist()
        composeTestRule.onNodeWithText("task4").assertDoesNotExist()
        composeTestRule.onNodeWithText("task5").assertDoesNotExist()
    }

    @Test
    fun changeTaskFilterCompleted_showOnlyCompletedTask(){
        setTaskItem()
        setContent()

        taskFilterChange(R.string.completed_task)

        composeTestRule.onNodeWithText("Completed Tasks").assertIsDisplayed()
        composeTestRule.onNodeWithText("task1").assertDoesNotExist()
        composeTestRule.onNodeWithText("task2").assertDoesNotExist()
        composeTestRule.onNodeWithText("task3").assertIsDisplayed()
        composeTestRule.onNodeWithText("task4").assertIsDisplayed()
        composeTestRule.onNodeWithText("task5").assertIsDisplayed()
    }

    @Test
    fun activeTask_checkBoxTapped_changeCompleted(){
        runBlocking {
            repository.insertTask(Task(id = 0, title = "test task", isChecked = false))
        }
        setContent()

        composeTestRule.onNode(isToggleable()).assertIsOff()
        composeTestRule.onNode(isToggleable()).performClick()

        composeTestRule.onNodeWithText("test task").assertIsDisplayed()
        taskFilterChange(R.string.active_task)
        composeTestRule.onNodeWithText("test task").assertDoesNotExist()
        taskFilterChange(R.string.completed_task)
        composeTestRule.onNodeWithText("test task").assertIsDisplayed()
    }

    @Test
    fun completedTask_checkBoxTapped_changActive(){
        runBlocking {
            repository.insertTask(Task(id = 0, title = "test task", isChecked = true))
        }
        setContent()

        composeTestRule.onNode(isToggleable()).assertIsOn()
        composeTestRule.onNode(isToggleable()).performClick()

        composeTestRule.onNodeWithText("test task").assertIsDisplayed()
        taskFilterChange(R.string.active_task)
        composeTestRule.onNodeWithText("test task").assertIsDisplayed()
        taskFilterChange(R.string.completed_task)
        composeTestRule.onNodeWithText("test task").assertDoesNotExist()
    }

    private fun setContent(){
        composeTestRule.setContent {
            ToDoComposeTheme {
                Surface() {
                    TasksListScreen(
                        onFabClick = {  },
                        onItemClick = {  },
                        viewModel = TasksListViewModel(repository)
                    )
                }
            }
        }
    }

    private fun setTaskItem(){
        runBlocking {
            repository.insertTask(Task(id=1, title = "task1", description = "sample description",isChecked = false))
            repository.insertTask(Task(id=2, title = "task2", isChecked = false))
            repository.insertTask(Task(id=3, title = "task3", description = "task3 sample description",isChecked = true))
            repository.insertTask(Task(id=4, title = "task4", isChecked = true))
            repository.insertTask(Task(id=5, title = "task5", isChecked = true))
        }
    }

    private fun taskFilterChange(@StringRes filter:Int){
        composeTestRule.onNodeWithContentDescription(activity.getString(R.string.task_menu_filter)).performClick()
        composeTestRule.onNodeWithText(activity.getString(filter)).performClick()
    }
}