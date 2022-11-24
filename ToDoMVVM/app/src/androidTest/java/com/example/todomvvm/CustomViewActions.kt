package com.example.todomvvm

import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

fun clickItemWithId(@IdRes id:Int): ViewAction {
    return object : ViewAction {
        override fun getDescription(): String {
            return "Click on a child view with specified id."
        }

        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.hasDescendant(ViewMatchers.withId(id))
        }

        override fun perform(uiController: UiController?, view: View?) {
            view?.findViewById<View>(id)?.also {
                it.performClick()
            }
        }
    }
}