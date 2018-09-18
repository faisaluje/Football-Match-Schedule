package com.faisaluje.footballmatchschedule.main

import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.faisaluje.footballmatchschedule.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest{
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAppBehaviour(){
        // Click menu
        openActionBarOverflowOrOptionsMenu(activityRule.activity.applicationContext)

        onView(withText("Italian Serie A"))
                .check(matches(isDisplayed()))
        onView(withText("Italian Serie A")).perform(click())

        Thread.sleep(1500)
        onView(withId(rv_event_list))
                .check(matches(isDisplayed()))
        onView(withId(rv_event_list))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withId(rv_event_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))

        Thread.sleep(500)
        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withId(snackbar_text))
//        onView(withText("Added to favorite"))
                .check(matches(isDisplayed()))
        pressBack()

        onView(withId(nav_button))
                .check(matches(isDisplayed()))
        onView(withId(navigation_favorite)).perform(click())
    }
}