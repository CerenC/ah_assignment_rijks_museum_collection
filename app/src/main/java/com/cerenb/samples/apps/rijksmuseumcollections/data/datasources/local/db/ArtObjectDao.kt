package com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArtObjectDao {

    @Query("SELECT * FROM art_objects ORDER BY objectIndex ASC")
    fun getArtObjects(): PagingSource<Int, ArtObjectEntity>

    @Query("SELECT objectIndex FROM art_objects ORDER BY objectIndex DESC")
    fun getLastArtObjectIndex(): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArtObjects(artObjects: List<ArtObjectEntity>)

    @Query("DELETE FROM art_objects")
    fun deleteAll()

}