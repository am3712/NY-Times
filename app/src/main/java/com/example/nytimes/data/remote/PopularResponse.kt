package com.example.nytimes.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import timber.log.Timber


data class PopularResponse(
    @SerializedName("copyright") val copyright: String,
    @SerializedName("num_results") val numResults: Int,
    @SerializedName("results") val results: List<Result>,
    @SerializedName("status") val status: String
)

data class Media(
    @SerializedName("caption") val caption: String,
    @SerializedName("media-metadata") val mediaMetadata: List<MediaMetadata>,
)


data class MediaMetadata(
    @SerializedName("url") val url: String,
)

data class Result(
    @SerializedName("abstract") val `abstract`: String,
    @SerializedName("asset_id") val assetId: Long,
    @SerializedName("byline") val byline: String,
    @SerializedName("id") val id: Long,
    @SerializedName("media") val media: List<Media>,
    @SerializedName("published_date") val publishedDate: String,
    @SerializedName("title") val title: String,
    @SerializedName("updated") val updated: String,
    @SerializedName("url") val url: String,
)

@Parcelize
data class Article(
    val url: String,
    val id: Long,
    val publishedDate: String,
    val updatedDate: String,
    val byline: String,
    val title: String,
    val caption: String?,
    val abstract: String,
    val thumbnailUrl: String?,
    val coverUrl: String?
) : Parcelable


/**
 * Convert Network results to domain model
 */
fun PopularResponse.asDomainModel(): List<Article> {

    Timber.i("size : ${results.size}")
    return results.map { result: Result ->
        val media = result.media.getOrNull(0)
        Article(
            id = result.id,
            publishedDate = result.publishedDate,
            updatedDate = result.updated,
            byline = result.byline,
            abstract = result.abstract,
            thumbnailUrl = media?.mediaMetadata?.getOrNull(0)?.url,
            coverUrl = media?.mediaMetadata?.getOrNull(2)?.url,
            title = result.title,
            url = result.url,
            caption = media?.caption
        )
    }
}