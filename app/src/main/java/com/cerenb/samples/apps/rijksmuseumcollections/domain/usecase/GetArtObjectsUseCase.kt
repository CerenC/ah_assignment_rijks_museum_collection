package com.cerenb.samples.apps.rijksmuseumcollections.domain.usecase

import androidx.paging.PagingData
import com.cerenb.samples.apps.rijksmuseumcollections.domain.model.ArtObject
import com.cerenb.samples.apps.rijksmuseumcollections.domain.repository.ArtCollectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArtObjectsUseCase @Inject constructor(
    private val artCollectionRepository: ArtCollectionRepository
) {

    fun execute(): Flow<PagingData<ArtObject>> =
        artCollectionRepository.getArtObjects()

}