package com.example.nytimes.data.remote.api

import com.example.nytimes.data.ArticlesDataSource

class ApiHelper(private val apiService: ApiService) : ArticlesDataSource {

    override suspend fun getPopularArticles(index: String) =
        apiService.fetchMostPopularDate(index = index)

}