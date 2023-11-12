package com.waleska404.algorithms.ui.bubblesort

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waleska404.algorithms.domain.algointerface.BubbleSort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BubbleSortViewModel @Inject constructor(
    private val bubbleSort: BubbleSort
) : ViewModel() {

    private var _listToSort = MutableStateFlow(getSortingList())
    val listToSort: StateFlow<BubbleSortList> = _listToSort

    private fun getSortingList(): BubbleSortList {
        val list =  (0 until 9).map {
            BubbleSortItem(
                id = it,
                isCurrentlyCompared = false,
                value = (30..100).random(),
                color = Color.Red
            )
        }
        return BubbleSortList(list = list)
    }

    fun startSorting() {
        Log.i("TAG", "startSorting")
        val dataList = _listToSort.value.toDataList()
        viewModelScope.launch {
            bubbleSort.runBubbleSort(dataList).collect { bubbleSortInfo ->
                Log.i("TAG", "runBubbleSort COLLECT, $bubbleSortInfo")
                val index = bubbleSortInfo.currentItem

                // mark current items as being compared
                val newListCompare = _listToSort.value.list.toMutableList()
                newListCompare[index] = newListCompare[index].copy(isCurrentlyCompared = true)
                newListCompare[index + 1] = newListCompare[index + 1].copy(isCurrentlyCompared = true)
                _listToSort.value = _listToSort.value.copy(list = newListCompare)

                // swap if necessary
                if (bubbleSortInfo.shouldSwap) {
                    val newListSwap = _listToSort.value.list.toMutableList()
                    val temp = newListSwap[index].copy(isCurrentlyCompared = false)
                    newListSwap[index] = newListSwap[index + 1].copy(isCurrentlyCompared = false)
                    newListSwap[index + 1] = temp
                    _listToSort.value = _listToSort.value.copy(list = newListSwap)
                }

                // there is no changes in the list
                if(bubbleSortInfo.hadNoEffect) {
                    val newListEffect = _listToSort.value.list.toMutableList()
                    newListEffect[index] = newListEffect[index].copy(isCurrentlyCompared = false)
                    newListEffect[index + 1] = newListEffect[index + 1].copy(isCurrentlyCompared = false)
                    _listToSort.value = _listToSort.value.copy(list = newListEffect)
                }
            }
        }

    }

}
