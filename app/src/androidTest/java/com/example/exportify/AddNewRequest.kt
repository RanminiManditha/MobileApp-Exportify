package com.example.exportify

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreateRequestTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<CreateRequest>()

    @Test
    fun testCreateRequest() {
        // Type input values
        onView(withId(R.id.etTopic)).perform(typeText("Topic"))
        onView(withId(R.id.etDes)).perform(typeText("Description"))
        onView(withId(R.id.etPriceRange)).perform(typeText("Price Range"))

        // Close soft keyboard
        closeSoftKeyboard()

        // Click on submit button
        onView(withId(R.id.btn_request)).perform(click())

        // Verify success toast message
        onView(withText("Your requests added successfully"))
            .inRoot(withDecorView(not(activityScenarioRule.scenario.window.decorView)))
            .check(matches(isDisplayed()))


    }
}