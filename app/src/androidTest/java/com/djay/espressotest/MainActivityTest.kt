package com.djay.espressotest

import android.view.View
import android.widget.EditText
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun editTextSelection() {
        onView(withId(R.id.spinner)).perform(click())
        onData(anything()).atPosition(0).perform(click())
        onView(withId(R.id.editText)).check(matches(isDisplayed()))
        onView(withId(R.id.tvSelection)).check(matches(withText("EditText is selected")))
    }

    @Test
    fun textViewSelection() {
        onView(withId(R.id.spinner)).perform(click())
        onData(anything()).atPosition(1).perform(click())
        onView(withId(R.id.textView)).check(matches(isDisplayed()))
        onView(withId(R.id.tvSelection)).check(matches(withText("TextView is selected")))
    }

    @Test
    fun checkboxSelection() {
        onView(withId(R.id.spinner)).perform(click())
        onData(anything()).atPosition(2).perform(click())
        onView(withId(R.id.checkbox)).check(matches(isDisplayed()))
        onView(withId(R.id.tvSelection)).check(matches(withText("CheckBox is selected")))
    }

    @Test
    fun listViewSelection() {
        onView(withId(R.id.spinner)).perform(click())
        onData(anything()).atPosition(3).perform(click())
        onView(withId(R.id.listView)).check(matches(isDisplayed()))
        onView(withId(R.id.tvSelection)).check(matches(withText("ListView is selected")))
    }

    @Test
    fun editTextError() {
        onView(withId(R.id.spinner)).perform(click())
        onData(anything()).atPosition(0).perform(click())
        onView(withId(R.id.editText)).perform(typeText("Demo12"), closeSoftKeyboard())
        onView(withId(R.id.editText)).check(matches(matchError(activityRule.activity.getString(R.string.only_alphabets_allowed))))
    }

    @Test
    fun listViewItemClick() {
        onView(withId(R.id.spinner)).perform(click())
        onData(anything()).atPosition(3).perform(click())
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(2).perform(click())
        onView(withText("Item 3 clicked")).inRoot(
            withDecorView(not(`is`(activityRule.activity.window.decorView)))
        ).check(matches(isDisplayed()))
    }

    private fun matchError(expected: String): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Not found error message$expected, find it!")
            }

            override fun matchesSafely(v: View): Boolean {
                return if (v is EditText) {
                    v.error.toString() == expected
                } else false
            }
        }
    }

}