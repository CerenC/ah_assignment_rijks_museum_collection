package com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey val objectIndex: Long?,
    val prevKey: Int?,
    val nextKey: Int?
)