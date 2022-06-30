package com.cerenb.samples.apps.rijksmuseumcollections.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cerenb.samples.apps.rijksmuseumcollections.di.IoDispatcher
import com.cerenb.samples.apps.rijksmuseumcollections.domain.usecase.GetArtObjectDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ArtObjectDetailViewModel @Inject constructor(
    private val getArtObjectDetailUseCase: GetArtObjectDetailUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    fun loadArtObjectDetail(objectNumber: String): StateFlow<UiState> {
        return getArtObjectDetailUseCase.execute(objectNumber)
            .onStart { UiState.Loading }
            .map { UiState.ShowDetails(it) }
            .catch { UiState.Error }
            .flowOn(dispatcher)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)
    }

}