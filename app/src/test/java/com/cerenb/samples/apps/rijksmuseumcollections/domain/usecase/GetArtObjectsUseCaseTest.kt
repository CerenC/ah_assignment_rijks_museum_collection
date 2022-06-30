package com.cerenb.samples.apps.rijksmuseumcollections.domain.usecase

import androidx.paging.PagingSource
import com.cerenb.samples.apps.rijksmuseumcollections.domain.model.ArtObject
import com.cerenb.samples.apps.rijksmuseumcollections.domain.repository.ArtCollectionRepository
import com.cerenb.samples.apps.rijksmuseumcollections.test.FakeData
import com.cerenb.samples.apps.rijksmuseumcollections.test.FakeData.artObjects
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetArtObjectsUseCaseTest {

    private lateinit var repository: ArtCollectionRepository
    private lateinit var useCase: GetArtObjectsUseCase

    @Before
    fun setUp() {
        repository = mock()
        useCase = GetArtObjectsUseCase(repository)
    }

    @Test
    fun `when use case executed then should return art object paging data back`() = runTest {
        val artObjectPagingData: PagingSource.LoadResult<Int, ArtObject> =
            PagingSource.LoadResult.Page(
                data = artObjects,
                prevKey = null,
                nextKey = null
            )

        whenever(repository.getArtObjects()).thenReturn(flow { artObjectPagingData })
        useCase.execute().collectLatest {
            assertEquals(FakeData.artObjectDetail, it)
        }
        verify(repository).getArtObjects()
    }

}