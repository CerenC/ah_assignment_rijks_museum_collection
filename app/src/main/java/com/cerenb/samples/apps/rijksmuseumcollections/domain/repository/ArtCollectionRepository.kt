package com.cerenb.samples.apps.rijksmuseumcollections.domain.repository

import androidx.paging.PagingData
import com.cerenb.samples.apps.rijksmuseumcollections.domain.model.ArtObject
import com.cerenb.samples.apps.rijksmuseumcollections.domain.model.ArtObjectDetail
import kotlinx.coroutines.flow.Flow

interface ArtCollectionRepository {

    fun getArtObjects(): Flow<PagingData<ArtObject>>

    fun getArtObjectsWithDbBacking(): Flow<PagingData<ArtObject>>

    fun getArtObjectDetail(objectNumber: String): Flow<ArtObjectDetail>

}