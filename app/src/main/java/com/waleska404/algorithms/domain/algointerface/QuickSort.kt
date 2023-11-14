package com.waleska404.algorithms.domain.algointerface

import com.waleska404.algorithms.domain.model.QuickSortInfo
import kotlinx.coroutines.flow.Flow

interface QuickSort {
    fun runQuickSort(list: MutableList<Int>, start: Int, end: Int): Flow<QuickSortInfo>
}
