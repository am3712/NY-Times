package com.example.nytimes.data

import com.example.nytimes.data.remote.Article

class FakeTestNYTimesRepository(private val articles: List<Article>) : INYTimesRepository {

    override suspend fun getPopularArticles(): List<Article> {
        return articles
    }
}