package com.cerenb.samples.apps.rijksmuseumcollections.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
import com.cerenb.samples.apps.rijksmuseumcollections.di.IoDispatcher
import com.cerenb.samples.apps.rijksmuseumcollections.domain.usecase.GetArtObjectsUseCase
import com.cerenb.samples.apps.rijksmuseumcollections.ui.model.ArtObjectListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ArtObjectViewModel @Inject constructor(
    private val getArtObjectsUseCase: GetArtObjectsUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    fun getArtObjects(): Flow<PagingData<ArtObjectListItem>> {
        return getArtObjectsUseCase.execute()
            .map { pagingData ->
                pagingData.map { artObject ->
                    ArtObjectListItem.ArtObjectItem(artObject)
                }.insertSeparators { before: ArtObjectListItem?, after: ArtObjectListItem? ->
                    if (before == null && after == null) {
                        null
                    } else if (after == null) {
                        null
                    } else if (before == null) {
                        ArtObjectListItem.ArtObjectHeader(after.artistName)
                    } else if (!before.artistName.equals(after.artistName, ignoreCase = true)) {
                        ArtObjectListItem.ArtObjectHeader(after.artistName)
                    } else {
                        null
                    }
                }
            }
    }
}

