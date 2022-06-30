package com.cerenb.samples.apps.rijksmuseumcollections.di

import android.content.Context
import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.local.db.ArtCollectionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideArtCollectionDatabase(@ApplicationContext context: Context): ArtCollectionDatabase {
        return ArtCollectionDatabase.getInstance(context)
    }

}