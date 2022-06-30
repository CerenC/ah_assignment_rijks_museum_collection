package com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ArtObjectEntity::class, RemoteKey::class], version = 1, exportSchema = false)
abstract class ArtCollectionDatabase : RoomDatabase() {

    abstract fun artObjectDao(): ArtObjectDao
    abstract fun remoteKeyDao(): RemoteKeyDao

    companion object {
        private const val DATABASE_NAME: String = "art-collection-db"

        @Volatile
        private var instance: ArtCollectionDatabase? = null

        fun getInstance(context: Context): ArtCollectionDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): ArtCollectionDatabase {
            return Room.databaseBuilder(
                context, ArtCollectionDatabase::class.java, DATABASE_NAME
            ).build()
        }
    }
}