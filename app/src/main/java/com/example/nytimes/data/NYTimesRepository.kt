package com.example.nytimes.data

import android.content.SharedPreferences
import com.example.nytimes.data.remote.Article
import com.example.nytimes.data.remote.asDomainModel
import timber.log.Timber

class NYTimesRepository(
    private val sharedPreferences: SharedPreferences,
    private val remoteDataSource: ArticlesDataSource
) : INYTimesRepository {
    override suspend fun getPopularArticles(): List<Article> {
        try {
            val index = sharedPreferences.getString("period", "1.json")
            Timber.i("searchPeriod : $index")

            val popularResponse = remoteDataSource.getPopularArticles(index = index!!)
            if (popularResponse.status == "OK")
                return popularResponse.asDomainModel()
            else {
                Timber.i(popularResponse.status)
                throw Exception(popularResponse.status)
            }
        } catch (e: retrofit2.HttpException) {
            Timber.i(e)
            throw Exception("Too Many Requests")
        } catch (e: Exception) {
            Timber.i(e)
            throw e
        }
    }
}