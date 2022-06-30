package com.cerenb.samples.apps.rijksmuseumcollections.shared

sealed class ResourceResult<out R> {
    data class Success<out T>(val data: T) : ResourceResult<T>()
    data class Error(val exception: Exception) : ResourceResult<Nothing>()
    object Loading : ResourceResult<Nothing>()
}
