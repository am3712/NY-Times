package com.example.nytimes.data

import com.example.nytimes.data.remote.Article

class FakeAndroidTestRepository(private val articles: List<Article>) : INYTimesRepository {

    private var shouldReturnError = false

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }


    override suspend fun getPopularArticles(): Result<List<Article>> {
        if (shouldReturnError) {
            return Result.Error(Exception("Article exception"))
        }
        return Result.Success(articles)
    }
}