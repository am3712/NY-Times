package com.example.nytimes.ui.popular

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.nytimes.R
import com.example.nytimes.ServiceLocator
import com.example.nytimes.data.FakeAndroidTestNYTimesRepository
import com.example.nytimes.data.INYTimesRepository
import com.example.nytimes.data.remote.Article
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class PopularFragmentTest {

    private lateinit var repository: INYTimesRepository


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
        repository = FakeAndroidTestNYTimesRepository(listOf(article1, article2))
        ServiceLocator.nyTimesRepository = repository
    }

    @Test
    fun givenTwoArticles_DisplayedInUi() = runBlockingTest {
        // GIVEN - repository with two articles
        // WHEN - Details fragment launched
        //THEN - display 2 article
        launchFragmentInContainer<PopularFragment>(null, R.style.Theme_NYTimes)
        Thread.sleep(4000)
    }

}