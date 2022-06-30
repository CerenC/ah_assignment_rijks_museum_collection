package com.cerenb.samples.apps.rijksmuseumcollections.data.repository

import androidx.paging.*
import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.ArtObjectPagingDataSource
import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.ArtObjectsRemoteMediator
import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.local.db.ArtCollectionDatabase
import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.local.db.toDomain
import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.remote.model.toDomain
import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.remote.service.ArtCollectionService
import com.cerenb.samples.apps.rijksmuseumcollections.domain.model.ArtObject
import com.cerenb.samples.apps.rijksmuseumcollections.domain.model.ArtObjectDetail
import com.cerenb.samples.apps.rijksmuseumcollections.domain.repository.ArtCollectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArtCollectionRepositoryImpl @Inject constructor(
    private val artCollectionDatabase: ArtCollectionDatabase,
    private val artCollectionService: ArtCollectionService
) : ArtCollectionRepository {

    override fun getArtObjects() = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
        pagingSourceFactory = { ArtObjectPagingDataSource(artCollectionService, PAGE_SIZE) }
    ).flow

    override fun getArtObjectDetail(objectNumber: String): Flow<ArtObjectDetail> =
        flow { emit(artCollectionService.getArtObjectDetail(objectNumber).toDomain()) }

    @OptIn(ExperimentalPagingApi::class)
    override fun getArtObjectsWithDbBacking(): Flow<PagingData<ArtObject>> {
        val pagingSourceFactory = { artCollectionDatabase.artObjectDao().getArtObjects() }
        val pager = Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
            remoteMediator = ArtObjectsRemoteMediator(
                artCollectionDatabase,
                artCollectionService,
                PAGE_SIZE
            ),
            pagingSourceFactory = pagingSourceFactory
        )
        return pager.flow.map { pagingData ->
            pagingData.map { entity -> entity.toDomain() }
        }
    }

    companion object {
        private const val PAGE_SIZE = 30
    }
}