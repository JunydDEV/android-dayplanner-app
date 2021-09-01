package com.android.dayplanner.app

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingRegistry
import com.android.dayplanner.app.ui.MainActivity
import com.android.dayplanner.app.ui.resourceidling.SimpleIdlingResource
import org.junit.After
import org.junit.Before


open class BaseUIClass {

    private var resourceIdling: SimpleIdlingResource? = null

    @Before
    open fun setup() {
        val activityScenario: ActivityScenario<MainActivity> =
            ActivityScenario.launch(MainActivity::class.java)

        resourceIdling = SimpleIdlingResource

        activityScenario.onActivity {
            IdlingRegistry.getInstance().register(resourceIdling)
        }
    }

    @After
    open fun unregisterIdlingResource() {
        resourceIdling?.let {
            IdlingRegistry.getInstance().unregister(SimpleIdlingResource)
        }
    }
}