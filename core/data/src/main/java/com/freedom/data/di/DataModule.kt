package com.freedom.data.di

import com.freedom.data.BreedRepositoryImpl
import com.freedom.data.BreedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindDataStoreManager(breedRepositoryImpl: BreedRepositoryImpl): BreedRepository
}