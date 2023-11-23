package com.waleska404.algorithms.domain.quicksort

import com.waleska404.algorithms.domain.utils.swap
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class QuickSortImpl @Inject constructor() : QuickSort {

    override fun runQuickSort(list: MutableList<Int>, start: Int, end: Int): Flow<QuickSortDomainModel> = flow {
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
                emit(
                    QuickSortDomainModel(
                        currentPivot = pivot,
                        currentLeft = l,
                        currentRight = r,
                        shouldSwapPointers = shouldSwap,
                        shouldSwapPivot = false,
                        sortingSubarray = Pair(start, end),
                    )
                )
                delay(500)
                if (list[l] <= list[pivot]) l++
                if (list[r] >= list[pivot]) r--
            }
            list.swap(pivot, r)
            emit(
                QuickSortDomainModel(
                    currentPivot = pivot,
                    currentLeft = if(l < list.size) l else list.size-1,
                    currentRight = if (r >= 0) r else 0,
                    shouldSwapPointers = false,
                    shouldSwapPivot = true,
                    sortingSubarray = Pair(start, end),
                )
            )
            delay(500)
            val leftSubArrayIsSmaller = r - 1 - start < end - (r + 1)
            if (leftSubArrayIsSmaller) {
                runQuickSort(list, start, r - 1).collect { info -> emit(info) }
                runQuickSort(list, r + 1, end).collect { info -> emit(info) }
            } else {
                runQuickSort(list, r + 1, end).collect { info -> emit(info) }
                runQuickSort(list, start, r - 1).collect { info -> emit(info) }
            }
        } else {
            if (start < list.size) {
                emit(
                    QuickSortDomainModel(
                        baseCase = true,
                        currentPivot = start,
                        currentLeft = start,
                        currentRight = start,
                        shouldSwapPointers = false,
                        shouldSwapPivot = false,
                        sortingSubarray = Pair(start, end),
                    )
                )
            }
        }
    }
}