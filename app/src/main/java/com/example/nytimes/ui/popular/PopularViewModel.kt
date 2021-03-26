package com.example.nytimes.ui.popular

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.example.nytimes.data.NYTimesRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class PopularViewModel(private val app: Application) : AndroidViewModel(app) {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _loading = MutableLiveData<Boolean>()

    // The external immutable LiveData for the request status
    val loading: LiveData<Boolean>
        get() = _loading

    private val repository = NYTimesRepository()

    val articles = repository.articles

    // The internal MutableLiveData that stores the error string message of the most recent request
    private val _error = MutableLiveData<String>()

    // The external immutable LiveData for the request error
    val error: LiveData<String>
        get() = _error


    var searchPeriod: String?

    init {
        Timber.i("PopularViewModel created")
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(app.applicationContext)
        searchPeriod = sharedPreferences.getString("period", "1.json")
        Timber.i("searchPeriod : $searchPeriod")
        getPopularArticles()
    }

    fun getPopularArticles() {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.getPopularArticles(days = searchPeriod!!)
                _error.value = "OK"
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun clearErrorStatus() {
        _error.value = "CLEAR"
    }
}