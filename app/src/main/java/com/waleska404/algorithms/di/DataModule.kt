package com.waleska404.algorithms.di

import com.waleska404.algorithms.domain.bubblesort.BubbleSort
import com.waleska404.algorithms.domain.bubblesort.BubbleSortImpl
import com.waleska404.algorithms.domain.dijkstra.Dijkstra
import com.waleska404.algorithms.domain.dijkstra.DijkstraImpl
import com.waleska404.algorithms.domain.linearsearch.LinearSearch
import com.waleska404.algorithms.domain.linearsearch.LinearSearchImpl
import com.waleska404.algorithms.domain.quicksort.QuickSort
import com.waleska404.algorithms.domain.quicksort.QuickSortImpl
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

    @Singleton
    @Provides
    fun providesQuickSort(): QuickSort = QuickSortImpl()

    @Singleton
    @Provides
    fun providesDijkstra(): Dijkstra = DijkstraImpl()

    @Singleton
    @Provides
    fun providesLinearSearch(): LinearSearch = LinearSearchImpl()

}