package com.example.nytimes.ui.details

import android.app.Application
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.nytimes.data.remote.Article

class DetailsViewModel(private val app: Application) : AndroidViewModel(app) {
    private val _article = MutableLiveData<Article>()
    val article = _article


    fun setArticle(article: Article) {
        _article.value = article
    }

    fun openWebPage() {
        val webPage: Uri = Uri.parse(article.value?.url!!)
        val intent = Intent(Intent.ACTION_VIEW, webPage)
        intent.flags = FLAG_ACTIVITY_NEW_TASK
        if (intent.resolveActivity(app.packageManager) != null) {
            app.startActivity(intent)
        }
    }
}