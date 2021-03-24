package com.example.nytimes

import android.app.Application
import timber.log.Timber

class NYTimesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}