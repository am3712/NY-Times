package com.example.nytimes.ui.popular

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.nytimes.R
import com.example.nytimes.ServiceLocator
import com.example.nytimes.data.FakeAndroidTestRepository
import com.example.nytimes.data.remote.Article
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
@MediumTest
class PopularFragmentTest {

    private lateinit var repository: FakeAndroidTestRepository


    private val article1 = Article(
        url = "url1",
        id = 1,
        publishedDate = "12-1-2021",
        updatedDate = "25-1-2021",
        byline = "By Heather Murphy",
        title = "A Man Demanded His Final Paycheck. The Auto Shop Delivered 91,500 Greasy Pennies.",
        caption = "Caption 1",
        abstract = "Abstract 1",
        thumbnailUrl = "https://static01.nyt.com/images/2021/03/24/multimedia/24xp-pennies-04/24xp-pennies-04-thumbStandard.jpg",
        coverUrl = "https://static01.nyt.com/images/2021/03/24/multimedia/24xp-pennies-04/24xp-pennies-04-mediumThreeByTwo440.jpg"
    )

    private val article2 = Article(
        url = "url2",
        id = 2,
        publishedDate = "12-1-2021",
        updatedDate = "25-1-2021",
        byline = "By William Grimes",
        title = "Beverly Cleary, Beloved Children’s Book Author, Dies at 104",
        caption = "Caption 2",
        abstract = "Abstract 2",
        thumbnailUrl = "https://static01.nyt.com/images/2021/03/27/automobiles/27cleary-obit-1/27cleary-obit-1-thumbStandard-v3.jpg",
        coverUrl = "https://static01.nyt.com/images/2021/03/27/automobiles/27cleary-obit-1/27cleary-obit-1-mediumThreeByTwo440-v3.jpg"
    )

    @Before
    fun initRepository() {
        repository = FakeAndroidTestRepository(listOf(article1, article2))
        ServiceLocator.nyTimesRepository = repository
    }

    @Test
    fun clickArticle_navigateToDetailFragmentOne() {
        // GIVEN - On the home screen
        val scenario = launchFragmentInContainer<PopularFragment>(Bundle(), R.style.Theme_NYTimes)

        val navController = mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.articlesList))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,
                    ViewActions.click()
                )
            )

        // THEN - Verify that we navigate the first detail screen
        Mockito.verify(navController).navigate(
            PopularFragmentDirections.actionNavPopularToDetailsFragment(article2)
        )
    }

    @Test
    fun loadApiDataWithError_showSnackBarWithErrorMessage() {
        //GIVEN - set repo to return Result.Error
        repository.setReturnError(true)

        // WHEN - On the home screen launch
        launchFragmentInContainer<PopularFragment>(Bundle(), R.style.Theme_NYTimes)

        // THEN - Verify that snackBar appears
        onView(withText("Article exception")).check(matches(ViewMatchers.isDisplayed()))

        Thread.sleep(3000)
    }

}