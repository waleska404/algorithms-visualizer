package com.waleska404.algorithms.ui.bubblesort

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waleska404.algorithms.domain.bubblesort.BubbleSort
import com.waleska404.algorithms.ui.core.config.GAME_DELAY_IN_MS
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

    private var _listToSort = MutableStateFlow(getRandomSortingList())
    val listToSort: StateFlow<BubbleSortList> = _listToSort

    private fun getRandomSortingList(size: Int = 9): BubbleSortList {
        val list = (0 until size).map {
            BubbleSortItem(
                id = it,
                isCurrentlyCompared = false,
                value = (30..100).random(),
            )
        }
        return BubbleSortList(list = list)
    }

    fun randomizeCurrentList(size: Int = listToSort.value.list.size) {
        _listToSort.value = getRandomSortingList(size)
    }

    fun startSorting() {
        val dataList = _listToSort.value.toDataList()
        _isSorting.value = true
        viewModelScope.launch {
            bubbleSort.runBubbleSort(dataList, GAME_DELAY_IN_MS).collect { bubbleSortInfo ->
                val index = bubbleSortInfo.currentItem

                // mark current items as being compared
                val newListCompare = _listToSort.value.list.toMutableList()
                newListCompare[index] = newListCompare[index].copy(isCurrentlyCompared = true)
                if(index < newListCompare.size-1) newListCompare[index + 1] = newListCompare[index + 1].copy(isCurrentlyCompared = true)
                _listToSort.value = _listToSort.value.copy(list = newListCompare)
                delay(GAME_DELAY_IN_MS)

                // swap if necessary
                if (bubbleSortInfo.shouldSwap) {
                    val newListSwap = _listToSort.value.list.toMutableList()
                    val temp = newListSwap[index].copy()
                    newListSwap[index] = newListSwap[index + 1].copy()
                    newListSwap[index + 1] = temp
                    _listToSort.value = _listToSort.value.copy(list = newListSwap)
                }

                delay(GAME_DELAY_IN_MS)
                // uncheck as being compared
                val newList = _listToSort.value.list.toMutableList()
                newList[index] = newList[index].copy(isCurrentlyCompared = false)
                newList[index + 1] = newList[index + 1].copy(isCurrentlyCompared = false)
                _listToSort.value = _listToSort.value.copy(list = newList)
            }
            _isSorting.value = false
        }
    }

}
