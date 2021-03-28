package com.example.nytimes.data

import com.example.nytimes.data.remote.PopularResponse

/**
 * Main entry point for accessing tasks data.
 */
interface ArticlesDataSource {

    suspend fun getPopularArticles(index: String): PopularResponse
}
