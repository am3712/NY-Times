package com.example.nytimes.data

import android.content.SharedPreferences
import com.example.nytimes.data.remote.Article
import com.example.nytimes.data.remote.asDomainModel
import com.example.nytimes.util.wrapEspressoIdlingResource
import timber.log.Timber

class NYTimesRepository(
    private val sharedPreferences: SharedPreferences,
    private val remoteDataSource: ArticlesDataSource
) : INYTimesRepository {
    override suspend fun getPopularArticles(): Result<List<Article>> {
        wrapEspressoIdlingResource {

            return try {
                val index = sharedPreferences.getString("period", "1.json")

                val popularResponse = remoteDataSource.getPopularArticles(index = index!!)

                if (popularResponse.status == "OK")
                    Result.Success(popularResponse.asDomainModel())
                else
                    Result.Error(Exception(popularResponse.status))
            } catch (e: retrofit2.HttpException) {
                Timber.i(e)
                Result.Error(Exception("Too Many Requests"))
            } catch (e: Exception) {
                Timber.i(e)
                Result.Error(e)
            }
        }
    }
}