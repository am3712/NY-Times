package com.example.nytimes.ui.popular

import androidx.lifecycle.*
import com.example.nytimes.data.INYTimesRepository
import com.example.nytimes.data.Result
import com.example.nytimes.data.remote.Article
import kotlinx.coroutines.launch

class PopularViewModel(private val nyTimesRepository: INYTimesRepository) : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _dataLoading = MutableLiveData<Boolean>()

    // The external immutable LiveData for the request status
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading


    // Internally, we use a MutableLiveData, because we will be updating the List of Articles
    // with new values
    private val _articles = MutableLiveData<List<Article>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val articles: LiveData<List<Article>>
        get() = _articles

    // The internal MutableLiveData that stores the error string message of the most recent request
    private val _error = MutableLiveData<String>()

    // The external immutable LiveData for the request error
    val error: LiveData<String>
        get() = _error

    init {
        getPopularArticles()
    }

    fun getPopularArticles() {
        _dataLoading.value = true
        viewModelScope.launch {
            val result = nyTimesRepository.getPopularArticles()
            if (result is Result.Success) {
                _articles.value = result.data!!
                _error.value = "OK"
            } else {
                _error.value = (result as Result.Error).exception.message
            }
            _dataLoading.value = false
        }
    }

    fun clearErrorStatus() {
        _error.value = "CLEAR"
    }

    @Suppress("UNCHECKED_CAST")
    class TasksViewModelFactory(
        private val nyTimesRepository: INYTimesRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            (PopularViewModel(nyTimesRepository = nyTimesRepository) as T)
    }
}