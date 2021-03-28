package com.example.nytimes

import android.app.Application
import com.example.nytimes.data.INYTimesRepository
import timber.log.Timber

class NYTimesApplication : Application() {

    val repository: INYTimesRepository
        get() = ServiceLocator.provideNYTimesRepository(this)

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}