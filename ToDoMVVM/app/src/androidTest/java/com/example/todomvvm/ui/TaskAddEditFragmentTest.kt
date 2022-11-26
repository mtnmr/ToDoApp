package com.example.todomvvm.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.todomvvm.R
import com.example.todomvvm.addedit.TaskAddEditFragment
import com.example.todomvvm.addedit.TaskAddEditFragmentArgs
import com.example.todomvvm.data.ITaskRepository
import com.example.todomvvm.data.Task
import com.example.todomvvm.launchFragmentInHiltContainer
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
class TaskAddEditFragmentTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: ITaskRepository

    @Before
    fun init(){
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
    fun emptyTask_isNotSaved() {
        val bundle = TaskAddEditFragmentArgs(-1).toBundle()
        launchFragmentInHiltContainer<TaskAddEditFragment>(bundle, R.style.Theme_ToDoMVVM)

        onView(withId(R.id.task_title_edit)).check(matches(isDisplayed()))
        onView(withId(R.id.task_description_edit)).check(matches(isDisplayed()))

        onView(withId(R.id.task_title_edit_text)).perform(clearText())
        onView(withId(R.id.task_description_edit_text)).perform(clearText())
        onView(withId(R.id.task_save_fab_button)).perform(click())

        onView(withId(R.id.task_title_edit)).check(matches(isDisplayed()))
        onView(withId(R.id.task_description_edit)).check(matches(isDisplayed()))
    }
}