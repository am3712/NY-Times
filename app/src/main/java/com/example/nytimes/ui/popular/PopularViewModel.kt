package com.example.nytimes.ui.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nytimes.data.remote.Article
import com.example.nytimes.data.remote.api.RetrofitBuilder
import com.example.nytimes.data.remote.asDomainModel
import kotlinx.coroutines.launch
import timber.log.Timber

class PopularViewModel : ViewModel() {

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

    init {
        Timber.i("PopularViewModel created")
        getPopularArticles()
    }

    fun getPopularArticles() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val popularResponse = RetrofitBuilder.apiService.fetchMostPopularDate()
                _error.value = popularResponse.status
                if (popularResponse.status == "OK") {
                    _articles.value = popularResponse.asDomainModel()
                }
            } catch (e: Exception) {
                _error.value = "No Internet Connection"
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