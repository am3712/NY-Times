package com.example.nytimes.data

import com.example.nytimes.data.remote.Article

interface INYTimesRepository {
    suspend fun getPopularArticles(): Result<List<Article>>
}