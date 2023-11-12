package com.waleska404.algorithms.di

import com.waleska404.algorithms.domain.algoimlpementation.BubbleSortImpl
import com.waleska404.algorithms.domain.algointerface.BubbleSort
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun providesBubbleSort(): BubbleSort = BubbleSortImpl()

}