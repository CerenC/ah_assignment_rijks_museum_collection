package com.cerenb.samples.apps.rijksmuseumcollections.data.datasources

import androidx.paging.PagingSource
import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.remote.model.toDomain
import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.remote.service.ArtCollectionService
import com.cerenb.samples.apps.rijksmuseumcollections.domain.model.ArtObject
import com.cerenb.samples.apps.rijksmuseumcollections.test.FakeData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


class ArtObjectPagingDataSourceTest {
    private lateinit var artCollectionService: ArtCollectionService
    private lateinit var pagingSource: ArtObjectPagingDataSource

    @Before
    fun setUp() {
        artCollectionService = mock()
        pagingSource = ArtObjectPagingDataSource(artCollectionService, TEST_PAGE_SIZE)
    }

    @Test
    fun `load ArtObject when successful load`() = runTest {
        whenever(
            artCollectionService.getArtObjects(
                any(),
                any(),
                any()
            )
        ).thenReturn(FakeData.artObjectsResponse)

        val artObjects = FakeData.artObjectsResponse.artObjects.map { it.toDomain() }
        val expectedPage: PagingSource.LoadResult<Int, ArtObject> = PagingSource.LoadResult.Page(
            data = listOf(artObjects[0], artObjects[1]),
            prevKey = null,
            nextKey = 2
        )

        val actual: PagingSource.LoadResult<Int, ArtObject> = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        assertEquals(expectedPage, actual)
    }

    @Test
    fun `when error occurred while loading art objects then throw exception`() = runTest {
        val error = RuntimeException("404", Throwable())
        whenever(artCollectionService.getArtObjects(any(), any(), any())).thenThrow(error)
        val expectedResult = PagingSource.LoadResult.Error<Int, ArtObject>(error)

        assertEquals(
            expectedResult, pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    companion object {
        const val TEST_PAGE_SIZE = 2
    }
}