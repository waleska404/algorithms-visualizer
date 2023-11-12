package com.waleska404.algorithms.domain.algointerface

import com.waleska404.algorithms.domain.model.BubbleSortInfo
import kotlinx.coroutines.flow.Flow

interface BubbleSort {
    fun runBubbleSort(list: MutableList<Int>): Flow<BubbleSortInfo>
}

