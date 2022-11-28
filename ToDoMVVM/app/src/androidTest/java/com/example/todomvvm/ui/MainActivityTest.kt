package com.example.todomvvm.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.todomvvm.MainActivity
import com.example.todomvvm.R
import com.example.todomvvm.data.ITaskRepository
import com.example.todomvvm.data.Task
import com.example.todomvvm.taskslist.TasksListAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var repository: ITaskRepository

    @Before
    fun init() {
        hiltRule.inject()
        runBlocking {
            repository.insertTask(Task(id=1, title = "task1", description = "sample description",isChecked = false))
            repository.insertTask(Task(id=2, title = "task2", isChecked = false))
            repository.insertTask(Task(id=3, title = "task3", description = "task3 sample description",isChecked = true))
            repository.insertTask(Task(id=4, title = "task4", isChecked = true))
            repository.insertTask(Task(id=5, title = "task5", isChecked = true))

            delay(1000)
        }
    }

    @Test
    fun addTaskButtonClick_NavigateAddEditFragment() {
        onView(withId(R.id.add_task_button)).perform(click())

        onView(withId(R.id.task_title_edit)).check(matches(isDisplayed()))
        onView(withId(R.id.task_description_edit)).check(matches(isDisplayed()))
    }


    @Test
    fun navigateAddEditFragment_saveButtonClicked_showNewTask(){
        onView(withId(R.id.add_task_button)).perform(click())

        onView(withId(R.id.task_title_edit_text)).perform(replaceText("new task"))
        onView(withId(R.id.task_description_edit_text)).perform(replaceText("new task description"))
        onView(withId(R.id.task_save_fab_button)).perform(click())

        onView(withId(R.id.tasks_recyclerview)).check(matches(isDisplayed()))
        onView(withText("new task")).check(matches(isDisplayed()))
    }


    @Test
    fun clickTask1_navigateTaskDetail() {
        onView(withId(R.id.tasks_recyclerview)).perform(
                RecyclerViewActions.actionOnItemAtPosition<TasksListAdapter.TaskViewHolder>(
                    0, click()
                )
            )

        onView(withId(R.id.task_detail_title)).check(matches(isDisplayed()))
        onView(withText("task1")).check(matches(isDisplayed()))
        onView(withId(R.id.task_detail_description)).check(matches(isDisplayed()))
        onView(withText("sample description")).check(matches(isDisplayed()))
    }

    @Test
    fun clickTask1_clickEditButton_navigateEditFragment(){
        onView(withId(R.id.tasks_recyclerview)).perform(
            RecyclerViewActions.actionOnItemAtPosition<TasksListAdapter.TaskViewHolder>(
                0, click()
            )
        )
        onView(withId(R.id.task_edit_button)).perform(click())

        onView(withId(R.id.task_title_edit)).check(matches(isDisplayed()))
        onView(withText("task1")).check(matches(isDisplayed()))
        onView(withId(R.id.task_description_edit)).check(matches(isDisplayed()))
        onView(withText("sample description")).check(matches(isDisplayed()))
    }

    @Test
    fun clickTask1_clickEditButton_editTitle_showEditedTask(){
        onView(withId(R.id.tasks_recyclerview)).perform(
            RecyclerViewActions.actionOnItemAtPosition<TasksListAdapter.TaskViewHolder>(
                0, click()
            )
        )
        onView(withId(R.id.task_edit_button)).perform(click())

        onView(withId(R.id.task_title_edit_text)).perform(replaceText("Edited Task"))
        onView(withId(R.id.task_save_fab_button)).perform(click())

        Thread.sleep(1000)

        onView(withId(R.id.tasks_recyclerview)).check(matches(isDisplayed()))
        onView(withText("Edited Task")).check(matches(isDisplayed()))
    }
}