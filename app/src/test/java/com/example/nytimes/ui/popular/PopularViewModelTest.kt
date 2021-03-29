package com.example.nytimes.ui.popular

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nytimes.MainCoroutineRule
import com.example.nytimes.data.FakeTestRepository
import com.example.nytimes.data.remote.Article
import com.example.nytimes.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class PopularViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Use a fake repository to be injected into the viewModel
    private lateinit var testRepository: FakeTestRepository

    // Subject under test
    private lateinit var popularViewModel: PopularViewModel

    private lateinit var fakeArticles: List<Article>

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

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

        testRepository = FakeTestRepository(fakeArticles)

        popularViewModel = PopularViewModel(testRepository)
    }

    @Test
    fun getPopularArticles_requestPopularArticlesData() {
        // When request popular articles data
        popularViewModel.getPopularArticles()

        // Then the articles live data triggered and equals
        assertThat(popularViewModel.articles.getOrAwaitValue(), `is`(fakeArticles))
    }


    @Test
    fun loadArticles_loading() {
        // Pause dispatcher so you can verify initial values.
        mainCoroutineRule.pauseDispatcher()

        // Load the task in the view model.
        popularViewModel.getPopularArticles()

        // Then assert that the progress indicator is shown.
        assertThat(popularViewModel.dataLoading.getOrAwaitValue(), `is`(true))

        // Execute pending coroutines actions.
        mainCoroutineRule.resumeDispatcher()

        // Then assert that the progress indicator is hidden.
        assertThat(popularViewModel.dataLoading.getOrAwaitValue(), `is`(false))
    }

    @Test
    fun loadArticlesWhenArticlesAreUnavailable_callErrorToDisplay() {
        // Make the repository return errors.
        testRepository.setReturnError(true)
        popularViewModel.getPopularArticles()

        // Then empty and error are true (which triggers an error message to be shown).
        assertThat(popularViewModel.error.getOrAwaitValue(), `is`(not("OK")))
    }
}