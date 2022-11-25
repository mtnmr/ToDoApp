package com.example.todomvvm.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.todomvvm.MainActivity
import com.example.todomvvm.R
import com.example.todomvvm.clickItemWithId
import com.example.todomvvm.data.ITaskRepository
import com.example.todomvvm.data.Task
import com.example.todomvvm.taskslist.TasksListAdapter
import com.example.todomvvm.withDescendantViewAtPosition
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class TasksListFragmentTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var repository: ITaskRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun init(){
        hiltRule.inject()
        runTest(UnconfinedTestDispatcher()) {
            repository.insertTask(Task(id=1, title = "task1", isChecked = false))
            repository.insertTask(Task(id=2, title = "task2", isChecked = false))
            repository.insertTask(Task(id=3, title = "task3", isChecked = true))
            repository.insertTask(Task(id=4, title = "task4", isChecked = true))
            repository.insertTask(Task(id=5, title = "task5", isChecked = true))

            delay(1000)
        }
    }

    @Test
    fun taskList_isShow() {
        onView(withText("task1")).check(matches(isDisplayed()))
        onView(withText("task2")).check(matches(isDisplayed()))
        onView(withText("task3")).check(matches(isDisplayed()))
        onView(withText("task4")).check(matches(isDisplayed()))
        onView(withText("task5")).check(matches(isDisplayed()))
    }

    @Test
    fun taskFilterChangeActive_showOnlyActiveTask() {
        onView(withId(R.id.menu_filter)).perform(click())
        onView(withText("Active Tasks")).perform(click())

        onView(withId(R.id.filter_text)).check(matches(withText("Active Tasks")))
        onView(withText("task1")).check(matches(isDisplayed()))
        onView(withText("task2")).check(matches(isDisplayed()))
        onView(withText("task3")).check(doesNotExist())
        onView(withText("task4")).check(doesNotExist())
        onView(withText("task5")).check(doesNotExist())
    }

    @Test
    fun taskFilterChangeCompleted_showOnlyCompletedTask() {

        onView(withId(R.id.menu_filter)).perform(click())
        onView(withText("Completed Tasks")).perform(click())

        Thread.sleep(2000)

        onView(withId(R.id.filter_text)).check(matches(withText("Completed Tasks")))
        onView(withText("task1")).check(doesNotExist())
        onView(withText("task2")).check(doesNotExist())
        onView(withText("task3")).check(matches(isDisplayed()))
        onView(withText("task4")).check(matches(isDisplayed()))
        onView(withText("task5")).check(matches(isDisplayed()))
    }

    @Test
    fun taskCheckBox_falseToTrue_showAsCompletedTask(){
        onView(withText("task1")).check(matches(isDisplayed()))

        onView(withId(R.id.tasks_recyclerview)).perform(
            RecyclerViewActions.actionOnItemAtPosition<TasksListAdapter.TaskViewHolder>(
                0, clickItemWithId(R.id.task_checkbox)
            )
        )

        onView(
            withDescendantViewAtPosition(
            R.id.tasks_recyclerview, // RecyclerView の ID
            R.id.task_checkbox,    // 見つけたいビューの ID（ここでは追加ボタン）
            0         // リストアイテムの位置
        )
        ).check(matches(isChecked()))

        onView(withId(R.id.menu_filter)).perform(click())
        onView(withText("Completed Tasks")).perform(click())

        Thread.sleep(2000)

        onView(withText("task1")).check(matches(isDisplayed()))
    }

    @Test
    fun addTaskButtonClick_NavigateAddEditFragment(){
        onView(withId(R.id.add_task_button)).perform(click())

        onView(withId(R.id.task_title_edit)).check(matches(isDisplayed()))
        onView(withId(R.id.task_description_edit)).check(matches(isDisplayed()))
    }
}