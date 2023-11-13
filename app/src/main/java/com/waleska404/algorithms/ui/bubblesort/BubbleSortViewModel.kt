package com.waleska404.algorithms.ui.bubblesort

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waleska404.algorithms.domain.algointerface.BubbleSort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BubbleSortViewModel @Inject constructor(
    private val bubbleSort: BubbleSort
) : ViewModel() {

    private var _isSorting = MutableStateFlow(false)
    val isSorting: StateFlow<Boolean> = _isSorting

    private var _listToSort = MutableStateFlow(getSortingList())
    val listToSort: StateFlow<BubbleSortList> = _listToSort

    private fun getSortingList(size: Int = 9): BubbleSortList {
        val list = (0 until size).map {
            BubbleSortItem(
                id = it,
                isCurrentlyCompared = false,
                value = (30..100).random(),
            )
        }
        return BubbleSortList(list = list)
    }

    fun randomList(size: Int = listToSort.value.list.size) {
        _listToSort.value = getSortingList(size)
    }

    fun startSorting() {
        val dataList = _listToSort.value.toDataList()
        _isSorting.value = true
        viewModelScope.launch {
            bubbleSort.runBubbleSort(dataList).collect { bubbleSortInfo ->
                val index = bubbleSortInfo.currentItem

                // mark current items as being compared
                val newListCompare = _listToSort.value.list.toMutableList()
                newListCompare[index] = newListCompare[index].copy(isCurrentlyCompared = true)
                newListCompare[index + 1] = newListCompare[index + 1].copy(isCurrentlyCompared = true)
                _listToSort.value = _listToSort.value.copy(list = newListCompare)
                delay(500)

                // swap if necessary
                if (bubbleSortInfo.shouldSwap) {
                    val newListSwap = _listToSort.value.list.toMutableList()
                    val temp = newListSwap[index].copy()
                    newListSwap[index] = newListSwap[index + 1].copy()
                    newListSwap[index + 1] = temp
                    _listToSort.value = _listToSort.value.copy(list = newListSwap)
                }

                delay(500)
                // uncheck as being compared
                val newListEffect = _listToSort.value.list.toMutableList()
                newListEffect[index] = newListEffect[index].copy(isCurrentlyCompared = false)
                newListEffect[index + 1] = newListEffect[index + 1].copy(isCurrentlyCompared = false)
                _listToSort.value = _listToSort.value.copy(list = newListEffect)
            }
            _isSorting.value = false
        }

    }

}
