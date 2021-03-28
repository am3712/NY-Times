package com.example.nytimes.data

import com.example.nytimes.data.remote.PopularResponse

class FakeArticlesDataSource(private val popularResponse: PopularResponse) : ArticlesDataSource {
    override suspend fun getPopularArticles(index: String): PopularResponse {
        return popularResponse
    }

}