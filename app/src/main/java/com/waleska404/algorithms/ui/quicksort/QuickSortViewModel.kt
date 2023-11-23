package com.waleska404.algorithms.ui.quicksort

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

    private var _listToSort = MutableStateFlow(getRandomSortingList())
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
                inSortingRange = false,
            )
        }
        return QuickSortList(list = list)
    }

    fun randomizeCurrentList(size: Int = listToSort.value.list.size) {
        _listToSort.value = getRandomSortingList(size)
    }

    fun startSorting() {
        val dataList = _listToSort.value.toDataList()
        _isSorting.value = true
        var lastLeftPointer = 0
        var lastRightPointer = 0
        viewModelScope.launch {
            quickSort.runQuickSort(dataList, 0, dataList.size - 1).collect { quickSortInfo ->
                // mark the elements that are being considered for the sorting
                checkInSortingRange(quickSortInfo.sortingSubarray)

                // base case: sorting only one element
                if (quickSortInfo.baseCase) {
                    val pivot = quickSortInfo.currentPivot
                    if (!listToSort.value.list[pivot].alreadyOrdered) {
                        val newListCompare = _listToSort.value.list.toMutableList()
                        newListCompare[pivot] = newListCompare[pivot].copy(isPivot = true)
                        _listToSort.value = _listToSort.value.copy(list = newListCompare)
                        delay(500)
                        val newListSorted = _listToSort.value.list.toMutableList()
                        newListSorted[pivot] = newListSorted[pivot].copy(isPivot = false, alreadyOrdered = true)
                        _listToSort.value = _listToSort.value.copy(list = newListSorted)
                    }
                } else {
                    // sorting more than one element
                    val pivot = quickSortInfo.currentPivot
                    val leftPointer = quickSortInfo.currentLeft
                    val rightPointer = quickSortInfo.currentRight

                    // uncheck latest items as being compared
                    val newListCompare = _listToSort.value.list.toMutableList()
                    newListCompare[lastLeftPointer] =
                        newListCompare[lastLeftPointer].copy(isLeftPointer = false, isRightPointer = false)
                    newListCompare[lastRightPointer] =
                        newListCompare[lastRightPointer].copy(isRightPointer = false, isLeftPointer = false)
                    lastLeftPointer = leftPointer
                    lastRightPointer = rightPointer

                    // mark current items as being compared
                    newListCompare[pivot] = newListCompare[pivot].copy(isPivot = true)
                    newListCompare[leftPointer] = newListCompare[leftPointer].copy(isLeftPointer = true)
                    newListCompare[rightPointer] = newListCompare[rightPointer].copy(isRightPointer = true)
                    _listToSort.value = _listToSort.value.copy(list = newListCompare)
                    delay(500)

                    // swap if necessary
                    if (quickSortInfo.shouldSwapPointers) {
                        swapOnCurrentList(leftPointer, rightPointer)
                    }
                    if (quickSortInfo.shouldSwapPivot) {
                        swapOnCurrentList(pivot, rightPointer)
                        delay(500)
                        val newListAfterSwap = _listToSort.value.list.toMutableList()
                        newListAfterSwap[pivot] = newListAfterSwap[pivot].copy(
                            isRightPointer = false,
                            isLeftPointer = false,
                        )
                        newListAfterSwap[rightPointer] = newListAfterSwap[rightPointer].copy(
                            alreadyOrdered = true,
                            isPivot = false,
                        )
                        newListAfterSwap[leftPointer] = newListAfterSwap[leftPointer].copy(
                            isRightPointer = false,
                            isLeftPointer = false,
                        )
                        _listToSort.value = _listToSort.value.copy(list = newListAfterSwap)
                    }
                    delay(500)
                }
            }
            _isSorting.value = false
            resetColors()
        }
    }

    private fun resetColors() {
        val newList = mutableListOf<QuickSortItem>()
        for (i in _listToSort.value.list) {
            newList.add(
                i.copy(
                    isPivot = false,
                    isLeftPointer = false,
                    isRightPointer = false,
                    alreadyOrdered = false
                )
            )
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

    private fun checkInSortingRange(range: Pair<Int, Int>) {
        val newList = _listToSort.value.list.toMutableList().mapIndexed { index, value ->
            value.copy(inSortingRange = index in range.first..range.second)
        }
        _listToSort.value = _listToSort.value.copy(list = newList)
    }
}





















