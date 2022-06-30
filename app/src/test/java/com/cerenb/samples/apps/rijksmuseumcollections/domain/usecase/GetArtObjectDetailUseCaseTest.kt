package com.cerenb.samples.apps.rijksmuseumcollections.domain.usecase

import com.cerenb.samples.apps.rijksmuseumcollections.domain.repository.ArtCollectionRepository
import com.cerenb.samples.apps.rijksmuseumcollections.test.FakeData
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.argWhere
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetArtObjectDetailUseCaseTest {

    private lateinit var repository: ArtCollectionRepository
    private lateinit var useCase: GetArtObjectDetailUseCase

    @Before
    fun setUp() {
        repository = mock()
        useCase = GetArtObjectDetailUseCase(repository)
    }

    @Test
    fun `when use case executed then should return art object detail back`() = runTest {
        val objectNumber = FakeData.artObjectDetail.objectNumber
        whenever(repository.getArtObjectDetail(objectNumber)).thenReturn(flow { FakeData.artObjectDetail })
        useCase.execute(objectNumber).collectLatest {
            assertEquals(FakeData.artObjectDetail, it)
        }
        verify(repository).getArtObjectDetail(argWhere { it == objectNumber })
    }

}