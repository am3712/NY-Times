package com.example.nytimes.ui.details

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.nytimes.MainActivity
import com.example.nytimes.R
import com.example.nytimes.data.remote.Article
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@MediumTest
@RunWith(AndroidJUnit4::class)
class DetailsFragmentTest {

    private lateinit var fakeArticle: Article

    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        // GIVEN - an article should display
        fakeArticle = Article(
            url = "https://www.nytimes.com/2021/03/25/business/auto-shop-pennies.html",
            id = 100000007671920,
            publishedDate = "2021-03-25",
            updatedDate = "2021-03-26 13:08:27",
            byline = "By Heather Murphy",
            title = "A Man Demanded His Final Paycheck. The Auto Shop Delivered 91,500 Greasy Pennies.",
            abstract = "It’s not technically illegal to do so, according to the Department of Labor, but that doesn’t make it OK, according to the former employee’s new Instagram fans.",
            caption = "",
            thumbnailUrl = "https://static01.nyt.com/images/2021/03/24/multimedia/24xp-pennies-04/24xp-pennies-04-thumbStandard.jpg",
            coverUrl = "https://static01.nyt.com/images/2021/03/24/multimedia/24xp-pennies-04/24xp-pennies-04-mediumThreeByTwo440.jpg"
        )
    }

    @Test
    fun passArticle_DisplayInUI() {
        // WHEN - Details fragment launched to display Article
        val fragmentArgs = DetailsFragmentArgs(fakeArticle).toBundle()
        launchFragmentInContainer<DetailsFragment>(fragmentArgs, R.style.Theme_NYTimes)


        // THEN - Article details are displayed on the screen
        // make sure that the title/byline/abstract/caption are both shown and correct
        val titleText = onView(withId(R.id.title_text))
        titleText.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        titleText.check(ViewAssertions.matches(ViewMatchers.withText(fakeArticle.title)))

        val byLine = onView(withId(R.id.by_line))
        byLine.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        byLine.check(ViewAssertions.matches(ViewMatchers.withText(fakeArticle.byline)))

        val caption = onView(withId(R.id.caption))
        caption.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        caption.check(ViewAssertions.matches(ViewMatchers.withText(fakeArticle.caption)))

        val abstract = onView(withId(R.id.article_abstract))
        abstract.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        abstract.check(ViewAssertions.matches(ViewMatchers.withText(fakeArticle.abstract)))

        // and make sure the floating action button is appear
        val openWeb = onView(withId(R.id.open_web_fab))
        openWeb.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    @Test
    fun clickOpenInBrowserButton_sendAndroidIntent() {
        // GIVEN - Details fragment launched to display Article
        val fragmentArgs = DetailsFragmentArgs(fakeArticle).toBundle()
        launchFragmentInContainer<DetailsFragment>(fragmentArgs, R.style.Theme_NYTimes)

        // WHEN - Click on openWebPageButton
        onView(withId(R.id.open_web_fab))
            .perform(click())

        // THEN - Verify that outgoing intent that starts a browser.
        intended(
            allOf(
                IntentMatchers.hasAction(Intent.ACTION_VIEW),
                IntentMatchers.hasData(Uri.parse(fakeArticle.url))
            )
        )
    }
}