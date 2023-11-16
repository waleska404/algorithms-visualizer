package com.waleska404.algorithms.domain.quicksort

import kotlinx.coroutines.flow.Flow

interface QuickSort {
    fun runQuickSort(list: MutableList<Int>, start: Int, end: Int): Flow<QuickSortDomainModel>
}
