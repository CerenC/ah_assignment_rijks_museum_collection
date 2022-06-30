package com.cerenb.samples.apps.rijksmuseumcollections.di

import com.cerenb.samples.apps.rijksmuseumcollections.data.repository.ArtCollectionRepositoryImpl
import com.cerenb.samples.apps.rijksmuseumcollections.domain.repository.ArtCollectionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(
        artCollectionRepositoryImpl: ArtCollectionRepositoryImpl
    ): ArtCollectionRepository

}