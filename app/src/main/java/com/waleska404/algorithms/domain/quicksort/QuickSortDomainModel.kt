package com.waleska404.algorithms.domain.quicksort

class QuickSortDomainModel(
    val baseCase: Boolean = false,
    val currentPivot: Int,
    val currentLeft: Int,
    val currentRight: Int,
    val shouldSwapPointers: Boolean,
    val shouldSwapPivot: Boolean,
    val sortingSubarray: Pair<Int, Int>,
)