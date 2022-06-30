package com.cerenb.samples.apps.rijksmuseumcollections.data.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.remote.model.toDomain
import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.remote.service.ArtCollectionService
import com.cerenb.samples.apps.rijksmuseumcollections.domain.model.ArtObject


private const val STARTING_PAGE_INDEX = 1

class ArtObjectPagingDataSource(
    private val service: ArtCollectionService,
    private val pageSize: Int
) : PagingSource<Int, ArtObject>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtObject> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = service.getArtObjects(pageIndex, pageSize)
            val results = response.artObjects.map { it.toDomain() }
            val isEndOfList = results.isEmpty()
            LoadResult.Page(
                data = results,
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex - 1,
                nextKey = if (isEndOfList) null else pageIndex + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArtObject>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}