package com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.remote.model

import com.cerenb.samples.apps.rijksmuseumcollections.domain.model.ArtObjectDetail
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArtObjectDetailResponse(
    val artObject: ArtObjectResponse
) {
    @JsonClass(generateAdapter = true)
    data class ArtObjectResponse(
        val objectNumber: String,
        val title: String,
        val scLabelLine: String,
        val subTitle: String,
        val description: String,
        @Json(name = "webImage")
        val webImageResponse: WebImageResponse? = null
    )
}

@JsonClass(generateAdapter = true)
data class WebImageResponse(
    val url: String? = null
)

fun ArtObjectDetailResponse.toDomain() = ArtObjectDetail(
    objectNumber = artObject.objectNumber,
    imageUrl = artObject.webImageResponse?.url,
    title = artObject.title,
    makerLine = artObject.scLabelLine,
    subTitle = artObject.subTitle,
    description = artObject.description
)