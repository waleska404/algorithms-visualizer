package com.waleska404.algorithms.domain.model

class QuickSortInfo(
    val currentPivot: Int,
    val currentLeft: Int,
    val currentRight: Int,
    val shouldSwapPointers: Boolean,
    val shouldSwapPivot: Boolean,
    val sortingSubarray: Pair<Int, Int>,
)