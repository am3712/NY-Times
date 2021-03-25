package com.example.nytimes.data.remote.api

import com.example.nytimes.data.remote.Constant
import com.example.nytimes.data.remote.Constant.API_KEY
import com.example.nytimes.data.remote.PopularResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("${Constant.MOST_POPULAR_VIEWED}{days}")
    suspend fun fetchMostPopularDate(
        @Path("days") days: String,
        @Query("api-key") key: String = API_KEY,
    ): PopularResponse
}

object RetrofitBuilder {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}