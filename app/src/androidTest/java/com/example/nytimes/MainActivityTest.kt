package com.example.nytimes

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.nytimes.data.INYTimesRepository
import com.example.nytimes.util.DataBindingIdlingResource
import com.example.nytimes.util.EspressoIdlingResource
import com.example.nytimes.util.monitorActivity
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    // An idling resource that waits for Data Binding to have no pending bindings.
    private val dataBindingIdlingResource = DataBindingIdlingResource()


    private lateinit var repository: INYTimesRepository

    @Before
    fun init() {
        repository =
            ServiceLocator.provideNYTimesRepository(ApplicationProvider.getApplicationContext())
    }

    /**
     * Idling resources tell Espresso that the app is idle or busy. This is needed when operations
     * are not scheduled in the main Looper (for example when executed on a different thread).
     */
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }


    @Test
    fun endToEndTest_changeApiTimeSettings() = runBlocking() {


        // Start up Tasks screen.
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario) // LOOK HERE

        // Open the options menu OR open the overflow menu, depending on whether
        // the device has a hardware or software overflow menu button.
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext())

        //make sure setting item is displayed
        onView(withText(R.string.action_settings)).check(matches(isDisplayed()))

        //click on settings item
        onView(withText(R.string.action_settings)).perform(click())

        //make sure Time setting displayed
        onView(allOf(withText(R.string.period_title), isDisplayed()))

        //click on setting and make sure menu options appear
        onView(allOf(withText(R.string.period_title), isDisplayed())).perform(click())
        onView(
            allOf(
                withText("1 day"),
                withText("7 days"),
                withText("30 days"),
                isDisplayed()
            )
        )

        //change settings of api to 30 days
        onView(withText("30 days")).perform(click())

        // Make sure the activity is closed.
        activityScenario.close()
    }

    @Test
    fun endToEndTest_clickOnListItem_openArticleInBrowser() = runBlocking {
        // Start up Tasks screen.
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario) // LOOK HERE

        // Click on the first item on the list
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.articlesList))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    ViewActions.click()
                )
            )

        //make sure article title is displayed
        onView(withId(R.id.title_text)).check(matches(isDisplayed()))

        //make sure fab button is displayed
        onView(withId(R.id.open_web_fab)).check(matches(isDisplayed()))

        //click on fab button
        onView(withId(R.id.open_web_fab)).perform(click())

        // Make sure the activity is closed.
        activityScenario.close()
    }

}