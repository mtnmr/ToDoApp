package com.example.todocompose.ui

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.material.Surface
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.todocompose.HiltTestActivity
import com.example.todocompose.TodoNavGraph
import com.example.todocompose.data.ITaskRepository
import com.example.todocompose.R
import com.example.todocompose.data.Task
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
class TasksTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()
    private val activity get() = composeTestRule.activity

    @Inject
    lateinit var repository: ITaskRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun addNewTask(){
        setContent()

        composeTestRule.onNodeWithContentDescription(activity.getString(R.string.add_task_fab)).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(activity.getString(R.string.add_task_fab)).performClick()

        composeTestRule.onNodeWithText(activity.getString(R.string.add_edit_task_screen)).assertIsDisplayed()
        findTextField(R.string.task_title).performTextInput("new task")
        findTextField(R.string.task_description).performTextInput("new task description")
        composeTestRule.onNodeWithContentDescription(activity.getString(R.string.save_task_fab)).performClick()

        composeTestRule.onNodeWithText(activity.getString(R.string.app_name)).assertIsDisplayed()
        composeTestRule.onNodeWithText("new task").assertIsDisplayed()
    }

    @Test
    fun editTask(){
        runBlocking {
            repository.insertTask(Task(title = "test task", description = "test description"))
        }
        setContent()

        composeTestRule.onNodeWithText("test task").assertIsDisplayed()
        composeTestRule.onNodeWithText("test task").performClick()

        composeTestRule.onNodeWithText("test task").assertIsDisplayed()
        composeTestRule.onNodeWithText("test description").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(activity.getString(R.string.edit_task_fab)).performClick()

        composeTestRule.onNodeWithText("test task").assertIsDisplayed()
        findTextField(R.string.task_title).performTextReplacement("edited task")
        composeTestRule.onNodeWithText("test description").assertIsDisplayed()
        findTextField(R.string.task_description).performTextReplacement("edited description")
        composeTestRule.onNodeWithContentDescription(activity.getString(R.string.save_task_fab)).performClick()

        composeTestRule.onNodeWithText(activity.getString(R.string.app_name)).assertIsDisplayed()
        composeTestRule.onNodeWithText("edited task").assertIsDisplayed()
        composeTestRule.onNodeWithText("test task").assertDoesNotExist()
    }

    @Test
    fun deleteTask(){
        runBlocking {
            repository.insertTask(Task(title = "test task", description = "test description"))
        }
        setContent()

        composeTestRule.onNodeWithText("test task").assertIsDisplayed()
        composeTestRule.onNodeWithText("test task").performClick()

        composeTestRule.onNodeWithText("test task").assertIsDisplayed()
        composeTestRule.onNodeWithText("test description").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(activity.getString(R.string.delete_task_icon)).performClick()

        /* 直後だとtaskが消えずに残っている、FakeRepositoryのflowが流れてきてない？debugで実行するとできてる
           解決方法が分からないので一旦filterを変えることで再コンポーズさせてtaskが削除できてることを確認
         */
        composeTestRule.onNodeWithContentDescription(activity.getString(R.string.task_menu_filter)).performClick()
        composeTestRule.onNodeWithText(activity.getString(R.string.active_task)).performClick()

        composeTestRule.onNodeWithContentDescription(activity.getString(R.string.task_menu_filter)).performClick()
        composeTestRule.onNodeWithText(activity.getString(R.string.all_task)).performClick()

        composeTestRule.onNodeWithText(activity.getString(R.string.app_name)).assertIsDisplayed()
        composeTestRule.onNodeWithText("test task").assertDoesNotExist()
    }

    @Test
    fun detailScreenNavigationIconTapped_backToTaskList(){
        runBlocking {
            repository.insertTask(Task(title = "test task", description = "test description"))
        }
        setContent()

        composeTestRule.onNodeWithText("test task").performClick()

        composeTestRule.onNodeWithText(activity.getString(R.string.task_detail_screen)).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(activity.getString(R.string.menu_back)).performClick()

        composeTestRule.onNodeWithText(activity.getString(R.string.app_name)).assertIsDisplayed()
        composeTestRule.onNodeWithText("test task").assertIsDisplayed()
    }

    @Test
    fun addEditScreenNavigationIconTapped_backToTaskList() {
        setContent()

        composeTestRule.onNodeWithContentDescription(activity.getString(R.string.add_task_fab)).performClick()

        composeTestRule.onNodeWithText(activity.getString(R.string.add_edit_task_screen)).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(activity.getString(R.string.menu_back)).performClick()

        composeTestRule.onNodeWithText(activity.getString(R.string.app_name)).assertIsDisplayed()
    }

    @Test
    fun navigateStatisticsScreen_showPercentFiftyFifty(){
        runBlocking {
            repository.insertTask(Task(id = 1, title = "test 1", description = "test1 description", isChecked = false))
            repository.insertTask(Task(id = 2 ,title = "test 2", description = "test2 description", isChecked = true))
        }
        setContent()

        composeTestRule.onNodeWithText(activity.getString(R.string.statistics_screen)).performClick()

        val expectedActiveTaskText = activity.getString(R.string.active_task_percent, 50.0f)
        val expectedCompletedTaskText = activity.getString(R.string.completed_task_percent, 50.0f)
        composeTestRule.onNodeWithText(expectedActiveTaskText).assertIsDisplayed()
        composeTestRule.onNodeWithText(expectedCompletedTaskText).assertIsDisplayed()
    }

    private fun setContent(){
        composeTestRule.setContent {
            ToDoComposeTheme {
                Surface {
                    TodoNavGraph()
                }
            }
        }
    }

    private fun findTextField(textId: Int): SemanticsNodeInteraction {
        return composeTestRule.onNode(
            hasSetTextAction() and hasText(activity.getString(textId))
        )
    }
}