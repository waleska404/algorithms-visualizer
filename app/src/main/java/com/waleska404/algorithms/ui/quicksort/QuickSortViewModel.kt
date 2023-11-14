package com.waleska404.algorithms.ui.quicksort

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waleska404.algorithms.domain.algointerface.QuickSort
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
        viewModelScope.launch {
            quickSort.runQuickSort(dataList, 0, dataList.size-1).collect { quickSortInfo ->
                val pivot = quickSortInfo.currentPivot
                val leftPointer = quickSortInfo.currentLeft
                val rightPointer = quickSortInfo.currentRight

                // mark current items as being compared
                val newListCompare = _listToSort.value.list.toMutableList()
                newListCompare[pivot] = newListCompare[pivot].copy(isPivot = true)
                newListCompare[leftPointer] = newListCompare[leftPointer].copy(isLeftPointer = true)
                newListCompare[rightPointer] = newListCompare[rightPointer].copy(isRightPointer = true)
                _listToSort.value = _listToSort.value.copy(list = newListCompare)
                delay(500)

                if(quickSortInfo.shouldSwapPointers) {
                    val newListSwap = _listToSort.value.list.toMutableList()
                    val temp = newListSwap[leftPointer].copy()
                    newListSwap[leftPointer] = newListSwap[rightPointer].copy()
                    newListSwap[rightPointer] = temp
                    _listToSort.value = _listToSort.value.copy(list = newListSwap)
                }

                if(quickSortInfo.shouldSwapPivot) {
                    val newListSwap = _listToSort.value.list.toMutableList()
                    val temp = newListSwap[pivot].copy(alreadyOrdered = true)
                    newListSwap[pivot] = newListSwap[rightPointer].copy()
                    newListSwap[rightPointer] = temp
                    _listToSort.value = _listToSort.value.copy(list = newListSwap)
                }

                delay(500)
                // uncheck as being compared
                val newList = _listToSort.value.list.toMutableList()
                newList[leftPointer] = newList[leftPointer].copy(isLeftPointer = false)
                newList[rightPointer] = newList[rightPointer].copy(isRightPointer = false)
                _listToSort.value = _listToSort.value.copy(list = newList)
            }
        }
    }

}





















