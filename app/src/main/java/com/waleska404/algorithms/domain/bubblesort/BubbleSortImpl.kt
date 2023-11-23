package com.waleska404.algorithms.domain.bubblesort

import com.waleska404.algorithms.domain.utils.swap
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BubbleSortImpl @Inject constructor() : BubbleSort {

    override fun runBubbleSort(list: MutableList<Int>, delayInMs: Long): Flow<BubbleSortDomainModel> = flow {
        var size = list.size - 1
        while (size > 0) {
            var innerIndex = 0
            while (innerIndex < size) {
                // select two elements to compare
                val currentItem = list[innerIndex]
                val nextItem = list[innerIndex + 1]

                // check if the elements should swap
                if (currentItem > nextItem) {
                    list.swap(innerIndex, innerIndex + 1)
                    emit(BubbleSortDomainModel(currentItem = innerIndex, shouldSwap = true))
                } else {
                    emit(BubbleSortDomainModel(currentItem = innerIndex, shouldSwap = false))
                }
                delay(delayInMs)
                innerIndex += 1
            }
            size -= 1
        }
    }
}