package com.example.exportify
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AddNewServiceInstrumentedTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(AddNewService::class.java)

    @Test
    fun addNewServiceTest() {
        // Find views
        onView(withId(R.id.edServiceTopic)).perform(typeText("Test Service"))
        onView(withId(R.id.edServiceType)).perform(typeText("Test Type"))
        onView(withId(R.id.edDescription)).perform(typeText("Test Description"))
        onView(withId(R.id.edUnits)).perform(typeText("10"))
        onView(withId(R.id.edPrice)).perform(typeText("50"))


        onView(withId(R.id.btnInsert)).perform(click())


    }
}