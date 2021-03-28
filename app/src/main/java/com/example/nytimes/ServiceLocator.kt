package com.example.nytimes

import android.content.Context
import androidx.preference.PreferenceManager
import com.example.nytimes.data.INYTimesRepository
import com.example.nytimes.data.NYTimesRepository
import com.example.nytimes.data.remote.api.ApiHelper
import com.example.nytimes.data.remote.api.RetrofitBuilder
import timber.log.Timber

object ServiceLocator {

    @Volatile
    var nyTimesRepository: INYTimesRepository? = null


    fun provideNYTimesRepository(context: Context): INYTimesRepository {
        synchronized(this) {
            return nyTimesRepository ?: createNYTimesRepository(context)
        }
    }

    private fun createNYTimesRepository(context: Context): INYTimesRepository {
        Timber.i("nyTimesRepository is created!!!")
        val newRepo =
            NYTimesRepository(
                PreferenceManager.getDefaultSharedPreferences(context),
                ApiHelper(RetrofitBuilder.apiService)
            )
        nyTimesRepository = newRepo
        return newRepo
    }
}