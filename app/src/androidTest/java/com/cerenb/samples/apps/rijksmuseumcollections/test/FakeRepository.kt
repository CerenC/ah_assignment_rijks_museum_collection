package com.cerenb.samples.apps.rijksmuseumcollections.test

import androidx.paging.PagingData
import com.cerenb.samples.apps.rijksmuseumcollections.domain.model.ArtObject
import com.cerenb.samples.apps.rijksmuseumcollections.domain.model.ArtObjectDetail
import com.cerenb.samples.apps.rijksmuseumcollections.domain.repository.ArtCollectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository : ArtCollectionRepository {
    private val artObjects = FakeData.artObjects

    override fun getArtObjects(): Flow<PagingData<ArtObject>> = flow {
        emit(PagingData.from(artObjects))
    }

    override fun getArtObjectsWithDbBacking(): Flow<PagingData<ArtObject>> {
        TODO("Not yet implemented")
    }

    override fun getArtObjectDetail(objectNumber: String): Flow<ArtObjectDetail> = flow {
        emit(FakeData.artObjectDetail)
    }

}