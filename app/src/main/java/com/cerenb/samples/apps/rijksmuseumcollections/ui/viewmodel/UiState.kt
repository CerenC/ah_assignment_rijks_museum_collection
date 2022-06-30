package com.cerenb.samples.apps.rijksmuseumcollections.ui.viewmodel

import com.cerenb.samples.apps.rijksmuseumcollections.domain.model.ArtObjectDetail

sealed class UiState {
    data class ShowDetails(val artObjectDetail: ArtObjectDetail) : UiState()
    object Loading : UiState()
    object Error : UiState()
}