package com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.remote.model

import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.local.db.ArtObjectEntity
import com.cerenb.samples.apps.rijksmuseumcollections.domain.model.ArtObject
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArtObjectsResponse(
    val count: Int,
    val artObjects: List<ArtObjectResponse>
)

@JsonClass(generateAdapter = true)
data class ArtObjectResponse(
    val objectNumber: String,
    val title: String,
    val longTitle: String,
    val principalOrFirstMaker: String,
    val headerImage: HeaderImageResponse? = null
)

@JsonClass(generateAdapter = true)
data class HeaderImageResponse(
    val url: String? = null
)

fun ArtObjectResponse.toEntity() = ArtObjectEntity(
    objectNumber = objectNumber,
    title = title,
    longTitle = longTitle,
    principalOrFirstMaker = principalOrFirstMaker,
    imageUrl = headerImage?.url
)


fun ArtObjectResponse.toDomain() = ArtObject(
    objectNumber = objectNumber,
    title = title,
    longTitle = longTitle,
    principalOrFirstMaker = principalOrFirstMaker,
    imageUrl = headerImage?.url
)
