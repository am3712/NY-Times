package com.example.nytimes.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nytimes.data.remote.Article
import com.example.nytimes.data.remote.api.RetrofitBuilder
import com.example.nytimes.data.remote.asDomainModel
import timber.log.Timber

class NYTimesRepository {

    private val remoteDataSource = RetrofitBuilder.apiService

    // Internally, we use a MutableLiveData, because we will be updating the List of Articles
    // with new values
    private val _articles = MutableLiveData<List<Article>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val articles: LiveData<List<Article>>
        get() = _articles


    suspend fun getPopularArticles(days: String) {
        try {
            val popularResponse =
                remoteDataSource.fetchMostPopularDate(days = days)
            Timber.i(popularResponse.results[1].toString())
            if (popularResponse.status == "OK")
                _articles.value = popularResponse.asDomainModel()
            else
                throw Exception(popularResponse.status)
        } catch (e: Exception) {
            Timber.i(e)
            Timber.i(e.localizedMessage)
            throw Exception("No Internet Connection")
        }
    }
}