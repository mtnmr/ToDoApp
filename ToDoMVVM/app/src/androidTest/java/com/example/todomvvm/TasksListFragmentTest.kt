package com.example.todomvvm

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.todomvvm.di.FakeRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
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
    lateinit var repository: FakeRepository

    @Before
    fun init(){
        hiltRule.inject()
    }

    @Test
    fun taskList_isShow(){
        onView(withText("task1")).check(matches(isDisplayed()))
        onView(withText("task2")).check(matches(isDisplayed()))
        onView(withText("task3")).check(matches(isDisplayed()))
        onView(withText("task4")).check(matches(isDisplayed()))
        onView(withText("task5")).check(matches(isDisplayed()))
    }

    @Test
    fun taskFilterChangeActive_showOnlyActiveTask(){
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
    fun taskFilterChangeCompleted_showOnlyCompletedTask(){
        onView(withId(R.id.menu_filter)).perform(click())
        onView(withText("Completed Tasks")).perform(click())

        onView(withId(R.id.filter_text)).check(matches(withText("Completed Tasks")))
        onView(withText("task1")).check(doesNotExist())
        onView(withText("task2")).check(doesNotExist())
        onView(withText("task3")).check(matches(isDisplayed()))
        onView(withText("task4")).check(matches(isDisplayed()))
        onView(withText("task5")).check(matches(isDisplayed()))
    }
}