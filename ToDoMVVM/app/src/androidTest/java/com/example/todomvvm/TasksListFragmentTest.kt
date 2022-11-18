package com.example.todomvvm

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

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: FakeRepository

    @Before
    fun init(){
        hiltRule.inject()
    }

    @Test
    fun taskList_isShow(){

    }
}