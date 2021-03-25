package com.example.nytimes.ui.popular

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.example.nytimes.data.remote.Article
import com.example.nytimes.data.remote.api.RetrofitBuilder
import com.example.nytimes.data.remote.asDomainModel
import kotlinx.coroutines.launch
import timber.log.Timber

class PopularViewModel(private val app: Application) : AndroidViewModel(app) {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _loading = MutableLiveData<Boolean>()

    // The external immutable LiveData for the request status
    val loading: LiveData<Boolean>
        get() = _loading


    // The internal MutableLiveData that stores the error string message of the most recent request
    private val _error = MutableLiveData<String>()

    // The external immutable LiveData for the request error
    val error: LiveData<String>
        get() = _error


    // Internally, we use a MutableLiveData, because we will be updating the List of Articles
    // with new values
    private val _articles = MutableLiveData<List<Article>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val articles: LiveData<List<Article>>
        get() = _articles

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
                val popularResponse =
                    RetrofitBuilder.apiService.fetchMostPopularDate(days = searchPeriod!!)
                Timber.i(popularResponse.results[1].toString())
                _error.value = popularResponse.status
                if (popularResponse.status == "OK") {
                    _articles.value = popularResponse.asDomainModel()
                }
            } catch (e: Exception) {
                _error.value = "No Internet Connection"
                Timber.i(e)
                Timber.i(e.localizedMessage)
            } finally {
                _loading.value = false
            }
        }
    }

    fun clearErrorStatus() {
        _error.value = "OK"
    }
}