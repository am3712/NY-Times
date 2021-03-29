package com.example.nytimes.data

import com.example.nytimes.data.Result.*
import com.example.nytimes.data.remote.Article

class FakeTestRepository(private val articles: List<Article>) : INYTimesRepository {

    private var shouldReturnError = false

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }


    override suspend fun getPopularArticles(): Result<List<Article>> {
        if (shouldReturnError) {
            return Error(Exception("Article exception"))
        }
        return Success(articles)
    }
}