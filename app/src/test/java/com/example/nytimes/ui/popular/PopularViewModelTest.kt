package com.example.nytimes.ui.popular

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nytimes.data.FakeTestNYTimesRepository
import com.example.nytimes.data.remote.Article
import com.example.nytimes.getOrAwaitValue
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PopularViewModelTest {

    // Use a fake repository to be injected into the viewModel
    private lateinit var testNyTimesRepository: FakeTestNYTimesRepository


    private lateinit var fakeArticles: List<Article>

    // Subject under test
    private lateinit var popularViewModel: PopularViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        // We initialise the articles to 2

        val article1 = Article(
            url = "url1",
            id = 1,
            publishedDate = "12-1-2021",
            updatedDate = "25-1-2021",
            byline = "BY ME 1",
            title = "Hello World 1",
            caption = "Caption 1",
            abstract = "Abstract 1",
            thumbnailUrl = null,
            coverUrl = null
        )

        val article2 = Article(
            url = "url2",
            id = 2,
            publishedDate = "12-1-2021",
            updatedDate = "25-1-2021",
            byline = "BY ME 2",
            title = "Hello World 2",
            caption = "Caption 2",
            abstract = "Abstract 2",
            thumbnailUrl = null,
            coverUrl = null
        )



        fakeArticles = listOf(article1, article2)

        testNyTimesRepository = FakeTestNYTimesRepository(fakeArticles)

        popularViewModel = PopularViewModel(testNyTimesRepository)
    }

    @Test
    fun getPopularArticles_requestPopularArticlesData() {
        // When request popular articles data
        popularViewModel.getPopularArticles()

        // Then the articles live data triggered and equals
        MatcherAssert.assertThat(
            popularViewModel.articles.getOrAwaitValue(),
            CoreMatchers.`is`(fakeArticles)
        )
    }
}