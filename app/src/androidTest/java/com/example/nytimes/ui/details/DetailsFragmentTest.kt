package com.example.nytimes.ui.details

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.nytimes.R
import com.example.nytimes.data.remote.Article
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class DetailsFragmentTest {

    @Test
    fun passArticle_DisplayInUI() {
        // GIVEN - an article should display
        val fakeArticle = Article(
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

        // WHEN - Details fragment launched to display task
        val fragmentArgs = DetailsFragmentArgs(fakeArticle).toBundle()
        launchFragmentInContainer<DetailsFragment>(fragmentArgs, R.style.Theme_NYTimes)
        Thread.sleep(3000)

    }

}