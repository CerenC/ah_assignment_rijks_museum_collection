package com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(remoteKey: RemoteKey)

    @Query("SELECT * FROM remote_keys WHERE objectIndex = :objectIndex")
    suspend fun remoteKeysIndex(objectIndex: Long): RemoteKey?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAll()

}