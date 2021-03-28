package com.example.nytimes.data

import com.example.nytimes.data.remote.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class NYTimesRepositoryTest {

    private val popularResponse = PopularResponse(
        copyright = "abdo mostafa",
        numResults = 1,
        status = "OK",
        results = listOf(
            Result(
                url = "https://www.nytimes.com/2021/03/25/business/auto-shop-pennies.html",
                id = 100000007671920,
                assetId = 100000007671920,
                publishedDate = "2021-03-25",
                updated = "2021-03-26 13:08:27",
                byline = "By Heather Murphy",
                title = "A Man Demanded His Final Paycheck. The Auto Shop Delivered 91,500 Greasy Pennies.",
                abstract = "It’s not technically illegal to do so, according to the Department of Labor, but that doesn’t make it OK, according to the former employee’s new Instagram fans.",
                media = listOf(
                    Media(
                        caption = "",
                        mediaMetadata = listOf(
                            MediaMetadata(url = "https://static01.nyt.com/images/2021/03/24/multimedia/24xp-pennies-04/24xp-pennies-04-thumbStandard.jpg"),
                            MediaMetadata(
                                url = "https://static01.nyt.com/images/2021/03/24/multimedia/24xp-pennies-04/24xp-pennies-04-mediumThreeByTwo210.jpg"
                            ),
                            MediaMetadata(url = "https://static01.nyt.com/images/2021/03/24/multimedia/24xp-pennies-04/24xp-pennies-04-mediumThreeByTwo440.jpg")
                        )
                    ),
                )
            )
        )
    )

    // Use a fake sharedPreference to be injected into the viewModel
    private lateinit var fakeSharedPreference: FakeSharedPreference

    private lateinit var fakeRemoteDataSource: ArticlesDataSource

    private lateinit var fakeArticlesRepository: NYTimesRepository

    @Before
    fun createRepository() {
        fakeRemoteDataSource = FakeArticlesDataSource(popularResponse)

        fakeSharedPreference = FakeSharedPreference()

        fakeArticlesRepository = NYTimesRepository(fakeSharedPreference, fakeRemoteDataSource)
    }

    @Test
    fun getPopularArticles_requestsAllArticlesFromRemoteDataSource() = runBlockingTest {
        // When tasks are requested from the tasks repository
        val articles = fakeArticlesRepository.getPopularArticles()

        // Then tasks are loaded from the remote data source
        assertEquals(articles, popularResponse.asDomainModel())
    }
}