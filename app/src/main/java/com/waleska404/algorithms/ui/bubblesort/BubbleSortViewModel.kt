package com.waleska404.algorithms.ui.bubblesort

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waleska404.algorithms.domain.bubblesort.BubbleSort
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
            bubbleSort.runBubbleSort(dataList).collect { bubbleSortInfo ->
                val index = bubbleSortInfo.currentItem

                // mark current items as being compared
                val newListCompare = _listToSort.value.list.toMutableList()
                newListCompare[index] = newListCompare[index].copy(isCurrentlyCompared = true)
                if(index < newListCompare.size-1) newListCompare[index + 1] = newListCompare[index + 1].copy(isCurrentlyCompared = true)
                _listToSort.value = _listToSort.value.copy(list = newListCompare)
                delay(500)
                println("-------------------------")
                println("-------------------------")
                println("---------Comparing----------------")
                println("marked current items as being compared: index: $index")
                if(index < newListCompare.size-1) println("marked next items as being compared: index + 1: ${index + 1}")
                println("current item: ${_listToSort.value.list[index]}")
                if(index < newListCompare.size-1) println("current item+1: ${_listToSort.value.list[index+1]}")

                // swap if necessary
                if (bubbleSortInfo.shouldSwap) {
                    println("---------Swaping----------------")
                    val newListSwap = _listToSort.value.list.toMutableList()
                    val temp = newListSwap[index].copy()
                    newListSwap[index] = newListSwap[index + 1].copy()
                    newListSwap[index + 1] = temp
                    _listToSort.value = _listToSort.value.copy(list = newListSwap)
                    println("current item: ${_listToSort.value.list[index]}")
                    println("current item: ${_listToSort.value.list[index+1]}")
                }

                delay(500)
                // uncheck as being compared
                val newList = _listToSort.value.list.toMutableList()
                newList[index] = newList[index].copy(isCurrentlyCompared = false)
                newList[index + 1] = newList[index + 1].copy(isCurrentlyCompared = false)
                _listToSort.value = _listToSort.value.copy(list = newList)
                println("---------Uncheck----------------")
                println("current item: ${_listToSort.value.list[index]}")
                println("current item+1: ${_listToSort.value.list[index+1]}")
            }
            _isSorting.value = false
        }
    }

}
