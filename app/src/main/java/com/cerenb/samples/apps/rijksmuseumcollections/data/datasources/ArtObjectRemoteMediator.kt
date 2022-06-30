package com.cerenb.samples.apps.rijksmuseumcollections.data.datasources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.local.db.ArtCollectionDatabase
import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.local.db.ArtObjectEntity
import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.local.db.RemoteKey
import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.remote.model.toEntity
import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.remote.service.ArtCollectionService
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class ArtObjectsRemoteMediator(
    private val artCollectionDatabase: ArtCollectionDatabase,
    private val artCollectionService: ArtCollectionService,
    private val pageSize: Int
) : RemoteMediator<Int, ArtObjectEntity>() {

    private val artObjectDao = artCollectionDatabase.artObjectDao()
    private val remoteKeyDao = artCollectionDatabase.remoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, ArtObjectEntity>
    ): MediatorResult {

        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val response = artCollectionService.getArtObjects(page = page, pageSize = pageSize)
            val isEndOfList = response.artObjects.isEmpty()
            artCollectionDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    artObjectDao.deleteAll()
                    remoteKeyDao.deleteAll()
                }


                artObjectDao.insertArtObjects(response.artObjects.map { it.toEntity() })
                val latestArtObjectIndex = artObjectDao.getLastArtObjectIndex()

                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                remoteKeyDao.insert(
                    RemoteKey(
                        latestArtObjectIndex,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                )

            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKeyPageData(
        loadType: LoadType,
        state: PagingState<Int, ArtObjectEntity>
    ): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ArtObjectEntity>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.objectIndex?.let { repoId ->
                remoteKeyDao.remoteKeysIndex(repoId)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, ArtObjectEntity>): RemoteKey? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { artObject -> artObject.objectIndex?.let { remoteKeyDao.remoteKeysIndex(it) } }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, ArtObjectEntity>): RemoteKey? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { artObject -> artObject.objectIndex?.let { remoteKeyDao.remoteKeysIndex(it) } }
    }

}