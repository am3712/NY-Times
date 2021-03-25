package com.example.nytimes.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nytimes.data.remote.Article

class DetailsViewModel : ViewModel() {
    private val _article = MutableLiveData<Article>()
    val article = _article


    fun setArticle(article: Article) {
        _article.value = article
    }
}