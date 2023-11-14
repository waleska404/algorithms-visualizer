package com.waleska404.algorithms.ui.quicksort

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waleska404.algorithms.domain.quicksort.QuickSort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuickSortViewModel @Inject constructor(
    private val quickSort: QuickSort
) : ViewModel() {

    private var _isSorting = MutableStateFlow(false)
    val isSorting: StateFlow<Boolean> = _isSorting

    private var _listToSort = MutableStateFlow(getFakeSortingList())
    val listToSort: StateFlow<QuickSortList> = _listToSort

    private fun getRandomSortingList(size: Int = 9): QuickSortList {
        val list = (0 until size).map {
            QuickSortItem(
                id = it,
                isPivot = false,
                isLeftPointer = false,
                isRightPointer = false,
                alreadyOrdered = false,
                value = (30..100).random(),
            )
        }
        return QuickSortList(list = list)
    }

    private fun getFakeSortingList(): QuickSortList {
        return QuickSortList(
            list = listOf(
                QuickSortItem(
                    id = 0,
                    isPivot = false,
                    isLeftPointer = false,
                    isRightPointer = false,
                    alreadyOrdered = false,
                    value = 80,
                ),
                QuickSortItem(
                    id = 1,
                    isPivot = false,
                    isLeftPointer = false,
                    isRightPointer = false,
                    alreadyOrdered = false,
                    value = 50,
                ),
                QuickSortItem(
                    id = 2,
                    isPivot = false,
                    isLeftPointer = false,
                    isRightPointer = false,
                    alreadyOrdered = false,
                    value = 20,
                ),
                QuickSortItem(
                    id = 3,
                    isPivot = false,
                    isLeftPointer = false,
                    isRightPointer = false,
                    alreadyOrdered = false,
                    value = 90,
                ),
                QuickSortItem(
                    id = 4,
                    isPivot = false,
                    isLeftPointer = false,
                    isRightPointer = false,
                    alreadyOrdered = false,
                    value = 50,
                ),
                QuickSortItem(
                    id = 5,
                    isPivot = false,
                    isLeftPointer = false,
                    isRightPointer = false,
                    alreadyOrdered = false,
                    value = 60,
                ),
                QuickSortItem(
                    id = 6,
                    isPivot = false,
                    isLeftPointer = false,
                    isRightPointer = false,
                    alreadyOrdered = false,
                    value = 30,
                )
            )
        )
    }

    fun randomizeCurrentList(size: Int = listToSort.value.list.size) {
        //_listToSort.value = getRandomSortingList(size)
        _listToSort.value = getFakeSortingList()
    }

    fun startSorting() {
        val dataList = _listToSort.value.toDataList()
        _isSorting.value = true
        viewModelScope.launch {
            quickSort.runQuickSort(dataList, 0, dataList.size - 1).collect { quickSortInfo ->
                Log.i("MYTAG",
                    "currentPivot: ${quickSortInfo.currentPivot}, " +
                            "currentLeft: ${quickSortInfo.currentLeft}, " +
                            "currentRight: ${quickSortInfo.currentRight}, " +
                            "shouldSwapPointers: ${quickSortInfo.shouldSwapPointers}, " +
                            "shouldSwapPivot: ${quickSortInfo.shouldSwapPivot}, " +
                            "sortingSubarray: ${quickSortInfo.sortingSubarray}, "
                )
                val pivot = quickSortInfo.currentPivot
                val leftPointer = quickSortInfo.currentLeft
                val rightPointer = quickSortInfo.currentRight

                // mark current items as being compared
                val newListCompare = _listToSort.value.list.toMutableList()
                newListCompare[pivot] = newListCompare[pivot].copy(isPivot = true)
                newListCompare[leftPointer] = newListCompare[leftPointer].copy(isLeftPointer = true)
                newListCompare[rightPointer] = newListCompare[rightPointer].copy(isRightPointer = true)
                _listToSort.value = _listToSort.value.copy(list = newListCompare)
                delay(2000)

                if (quickSortInfo.shouldSwapPointers) {
                    swapOnCurrentList(leftPointer, rightPointer)
                }
                if (quickSortInfo.shouldSwapPivot) {
                    swapOnCurrentList(pivot, rightPointer)
                    delay(2000)
                    val newListAfterSwap = _listToSort.value.list.toMutableList()
                    newListAfterSwap[pivot] = newListAfterSwap[pivot].copy(isRightPointer = false)
                    newListAfterSwap[rightPointer] = newListAfterSwap[rightPointer].copy(alreadyOrdered = true, isPivot = false)
                    _listToSort.value = _listToSort.value.copy(list = newListAfterSwap)
                }

                delay(500)
                // uncheck as being compared
                val newList = _listToSort.value.list.toMutableList()
                newList[leftPointer] = newList[leftPointer].copy(isLeftPointer = false, isRightPointer = false)
                newList[rightPointer] = newList[rightPointer].copy(isRightPointer = false, isLeftPointer = false)
                _listToSort.value = _listToSort.value.copy(list = newList)
            }
            _isSorting.value = false
            resetColors()
        }
    }

    private fun resetColors() {
        val newList = mutableListOf<QuickSortItem>()
        for(i in _listToSort.value.list) {
            newList.add(i.copy(
                isPivot = false,
                isLeftPointer = false,
                isRightPointer = false,
                alreadyOrdered = false
            ))
        }
        _listToSort.value = _listToSort.value.copy(list = newList)
    }

    private fun swapOnCurrentList(i: Int, j: Int) {
        val newListSwap = _listToSort.value.list.toMutableList()
        val temp = newListSwap[i].copy()
        newListSwap[i] = newListSwap[j].copy()
        newListSwap[j] = temp
        _listToSort.value = _listToSort.value.copy(list = newListSwap)
    }

}





















