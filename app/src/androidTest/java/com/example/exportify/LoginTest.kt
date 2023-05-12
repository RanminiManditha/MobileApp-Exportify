package com.example.exportify

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class LoginTest {



    class LoginTest {

        @get:Rule
        val activityRule = ActivityScenarioRule(Login::class.java)

        @Test
        fun testLoginSuccess(){
            Espresso.onView(withId(R.id.etEmail)).perform(ViewActions.typeText("harith@gmail.com"))
            Espresso.onView(withId(R.id.etPwd))
                .perform(ViewActions.typeText("123456"), ViewActions.closeSoftKeyboard())


            Espresso.onView(withId(R.id.btnLogin)).perform(ViewActions.click())

            // Verify that the correct dashboard activity is launched
            Espresso.onView(ViewMatchers.withId(android.R.id.content))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            Espresso.onView(withId(R.id.btnBuyer))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }

        @Test
        fun testLoginInvalidEmail() {

            Espresso.onView(withId(R.id.etEmail)).perform(
                ViewActions.typeText("anything@gmail.com"),
                ViewActions.closeSoftKeyboard()
            )
            Espresso.onView(withId(R.id.etPwd))
                .perform(ViewActions.typeText("anything"), ViewActions.closeSoftKeyboard())


            Espresso.onView(withId(R.id.btnLogin)).perform(ViewActions.click())

            // Verify that an error message is displayed
            Espresso.onView(ViewMatchers.withText("Invalid email address"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
    }
}