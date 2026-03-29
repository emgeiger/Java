package com.Geiger.receiptscanner

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumentation test for MainActivity
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    
    @Test
    fun mainActivity_launches() {
        // Basic test to ensure MainActivity launches without crashing
        activityRule.scenario.onActivity { activity ->
            // Activity launched successfully
        }
    }
}
