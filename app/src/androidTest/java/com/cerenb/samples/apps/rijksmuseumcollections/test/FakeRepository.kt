package com.cerenb.samples.apps.rijksmuseumcollections.test

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cerenb.samples.apps.rijksmuseumcollections.domain.model.ArtObject
import com.cerenb.samples.apps.rijksmuseumcollections.domain.model.ArtObjectDetail
import com.cerenb.samples.apps.rijksmuseumcollections.domain.repository.ArtCollectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository : ArtCollectionRepository {
    val artObjects = FakeData.artObjects

    override fun getArtObjects(): Flow<PagingData<ArtObject>> = flow {
        object : PagingSource<Int, ArtObject>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtObject> {
                return LoadResult.Page(
                    data = artObjects,
                    prevKey = null,
                    nextKey = null,
                )
            }

            // Ignored in test.
            override fun getRefreshKey(state: PagingState<Int, ArtObject>): Int? = null
        }
    }


    override fun getArtObjectsWithDbBacking(): Flow<PagingData<ArtObject>> {
        TODO("Not yet implemented")
    }

    override fun getArtObjectDetail(objectNumber: String): Flow<ArtObjectDetail> = flow {
        emit(FakeData.artObjectDetail)
    }

}