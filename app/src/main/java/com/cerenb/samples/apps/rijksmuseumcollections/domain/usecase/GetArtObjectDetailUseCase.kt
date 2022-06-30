package com.cerenb.samples.apps.rijksmuseumcollections.domain.usecase

import com.cerenb.samples.apps.rijksmuseumcollections.domain.model.ArtObjectDetail
import com.cerenb.samples.apps.rijksmuseumcollections.domain.repository.ArtCollectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArtObjectDetailUseCase @Inject constructor(
    private val repository: ArtCollectionRepository
) {

    fun execute(objectNumber: String): Flow<ArtObjectDetail> =
        repository.getArtObjectDetail(objectNumber)

}