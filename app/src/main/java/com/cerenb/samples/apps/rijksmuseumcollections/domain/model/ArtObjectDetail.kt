package com.cerenb.samples.apps.rijksmuseumcollections.domain.model

data class ArtObjectDetail(
    val objectNumber: String,
    val imageUrl: String? = null,
    val title: String,
    val makerLine: String,
    val subTitle: String,
    val description: String
)
