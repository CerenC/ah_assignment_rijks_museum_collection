package com.cerenb.samples.apps.rijksmuseumcollections.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cerenb.samples.apps.rijksmuseumcollections.domain.repository.ArtCollectionRepository
import com.cerenb.samples.apps.rijksmuseumcollections.domain.usecase.GetArtObjectDetailUseCase
import com.cerenb.samples.apps.rijksmuseumcollections.test.FakeData
import com.cerenb.samples.apps.rijksmuseumcollections.test.FakeRepository
import com.cerenb.samples.apps.rijksmuseumcollections.test.MainCoroutineRule
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ArtObjectDetailViewModelTest {

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var artCollectionRepository: ArtCollectionRepository
    private lateinit var getArtObjectDetailUseCase: GetArtObjectDetailUseCase
    private lateinit var viewModel: ArtObjectDetailViewModel

    private val testDispatcher = coroutineRule.testDispatcher

    @Before
    fun setUp() {
        artCollectionRepository = FakeRepository()
        getArtObjectDetailUseCase = GetArtObjectDetailUseCase(artCollectionRepository)
        viewModel = ArtObjectDetailViewModel(getArtObjectDetailUseCase, testDispatcher)
    }

    @Test
    fun whenArtObjectDetailRetrievedSuccessfullyThenReturnCorrectUiStates() =
        runTest(coroutineRule.testDispatcher) {
            val results = mutableListOf<UiState>()

            val job = launch {
                viewModel.loadArtObjectDetail("1").toList(results)
            }

            assertEquals(UiState.Loading, results.first())
            assertEquals(UiState.ShowDetails(FakeData.artObjectDetail), results.last())

            job.cancel()
        }

}