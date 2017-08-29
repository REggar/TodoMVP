package com.reggar.todomvp.feature.main

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.action.ViewActions.pressImeActionButton
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isChecked
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.reggar.todomvp.R
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule @JvmField val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun onTextTypeButtonClicked_todoCreated() {
        /* Given */
        val todoText = "Button Todo"

        /* When */
        onView(withId(R.id.edittext_maion))
                .perform(typeText(todoText))
        onView(withId(R.id.button_main_add))
                .perform(click())
                .perform(closeSoftKeyboard())

        /* Then */
        onView(allOf(withId(R.id.checkbox_todo), withText(todoText)))
                .check(matches(isDisplayed()))
    }

    @Test
    fun onTextTypeKeyboardEditorAction_todoCreated() {
        /* Given */
        val todoText = "IME Action Todo"

        /* When */
        onView(withId(R.id.edittext_maion))
                .perform(typeText(todoText))
                .perform(pressImeActionButton())
                .perform(closeSoftKeyboard())

        /* Then */
        onView(allOf(withId(R.id.checkbox_todo), withText(todoText)))
                .check(matches(isDisplayed()))
    }

    @Test
    fun onTodoSelected_markedAsComplete() {
        /* Given */
        val todoText = "To be marked as complete"

        /* When */
        createNewTodo(todoText)
        val todoCheckbox = onView(allOf(withId(R.id.checkbox_todo), withText(todoText)))
                .perform(click())

        /* Then */
        todoCheckbox
                .check(matches(isChecked()))
    }

    private fun createNewTodo(todoText: String) {
        onView(withId(R.id.edittext_maion))
                .perform(typeText(todoText))
                .perform(pressImeActionButton())
                .perform(closeSoftKeyboard())
    }
}
