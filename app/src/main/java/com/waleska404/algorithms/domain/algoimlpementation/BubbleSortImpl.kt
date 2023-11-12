package com.waleska404.algorithms.domain.algoimlpementation

import android.util.Log
import com.waleska404.algorithms.domain.algointerface.BubbleSort
import com.waleska404.algorithms.domain.model.BubbleSortInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BubbleSortImpl @Inject constructor() : BubbleSort {

    override fun runBubbleSort(list: MutableList<Int>): Flow<BubbleSortInfo> = flow {
        Log.i("MYTAG", "runBubbleSort, $list")
        var size = list.size - 1
        while (size > 0) {
            var innerIndex = 0
            while (innerIndex < size) {
                Log.i("MYTAG", "runBubbleSort, $innerIndex, size: $size")
                // select the two elements to compare
                val currentItem = list[innerIndex]
                val nextItem = list[innerIndex + 1]
                emit(BubbleSortInfo(currentItem = innerIndex, shouldSwap = false, hadNoEffect = false))
                delay(500)

                // check if the two elements should swap
                if (currentItem > nextItem) {
                    list.swap(innerIndex, innerIndex + 1)
                    emit(BubbleSortInfo(currentItem = innerIndex, shouldSwap = true, hadNoEffect = false))
                } else {
                    emit(BubbleSortInfo(currentItem = innerIndex, shouldSwap = false, hadNoEffect = true))
                }
                delay(500)
                innerIndex += 1
            }
            size -= 1
        }
    }

}

private fun <E> MutableList<E>.swap(i: Int, j: Int) {
    val temp = this[i]
    this[i] = this[j]
    this[j] = temp
}