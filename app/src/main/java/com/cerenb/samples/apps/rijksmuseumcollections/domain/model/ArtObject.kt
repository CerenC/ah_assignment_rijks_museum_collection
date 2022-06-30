package com.cerenb.samples.apps.rijksmuseumcollections.domain.model

data class ArtObject(
    val objectNumber: String,
    val title: String,
    val longTitle: String,
    val principalOrFirstMaker: String,
    val imageUrl: String? = null
)