package com.waleska404.algorithms.domain.algoimlpementation

import com.waleska404.algorithms.domain.algointerface.QuickSort
import com.waleska404.algorithms.domain.model.QuickSortInfo
import com.waleska404.algorithms.domain.utils.swap
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class QuickSortImpl @Inject constructor() : QuickSort {

    override fun runQuickSort(list: MutableList<Int>, start: Int, end: Int): Flow<QuickSortInfo> = flow {
        if (start < end) {
            val pivot = start
            var l = start + 1
            var r = end

            while (r >= l) {
                var shouldSwap = false
                if (list[l] > list[pivot] && list[r] < list[pivot]) {
                    list.swap(l, r)
                    shouldSwap = true
                }
                if (list[l] <= list[pivot]) l++
                if (list[r] >= list[pivot]) r--
                emit(
                    QuickSortInfo(
                        currentPivot = pivot,
                        currentLeft = l,
                        currentRight = r,
                        shouldSwapPointers = shouldSwap,
                        shouldSwapPivot = false,
                        sortingSubarray = Pair(start, end),
                    )
                )
                delay(500)
            }
            list.swap(pivot, r)
            emit(
                QuickSortInfo(
                    currentPivot = pivot,
                    currentLeft = l,
                    currentRight = r,
                    shouldSwapPointers = false,
                    shouldSwapPivot = true,
                    sortingSubarray = Pair(start, end),
                )
            )
            val leftSubArrayIsSmaller = r - 1 - start < end - (r + 1)
            if (leftSubArrayIsSmaller) {
                runQuickSort(list, start, r - 1)
                runQuickSort(list, r + 1, end)
            } else {
                runQuickSort(list, r + 1, end)
                runQuickSort(list, start, r - 1)
            }
        }
    }
}