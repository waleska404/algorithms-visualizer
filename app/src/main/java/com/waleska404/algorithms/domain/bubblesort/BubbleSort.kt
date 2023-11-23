package com.waleska404.algorithms.domain.bubblesort

import kotlinx.coroutines.flow.Flow

interface BubbleSort {
    fun runBubbleSort(list: MutableList<Int>, delayInMs: Long): Flow<BubbleSortDomainModel>
}

