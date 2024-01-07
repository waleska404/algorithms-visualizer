package com.waleska404.algorithms.ui.linearsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waleska404.algorithms.domain.linearsearch.LinearSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinearSearchViewModel @Inject constructor(private val linearSearch: LinearSearch) :
    ViewModel() {
    private var _searchItemFound = MutableStateFlow(false)
    val searchItemFound: StateFlow<Boolean> = _searchItemFound
    private var _currentIndex = MutableStateFlow(-1)
    val currentIndex: StateFlow<Int> = _currentIndex
    private var _list = MutableStateFlow(getRandomList())
    val list: StateFlow<LinearSearchList> = _list

    private fun getRandomList(size: Int = 9): LinearSearchList {
        _currentIndex.value = -1
        _searchItemFound.value = false
        val list = (0 until size).map {
            LinearSearchItem(
                value = (30..100).random(),
                position = _currentIndex.value,
                itemFound = false
            )
        }
        return LinearSearchList(list = list)
    }

    fun randomizeCurrentList(size: Int = list.value.list.size) {
        _list.value = getRandomList(size)
    }

     fun stepOver(searchItem: Int) {
        val dataList = _list.value.toDataList()
        _currentIndex.value++
        viewModelScope.launch {
            linearSearch.compare(dataList, searchItem, _currentIndex.value).collect {
                _searchItemFound.value = it.elementFound
                val updatedList = _list.value.list.toMutableList().also { mutableList ->
                    mutableList[_currentIndex.value] = LinearSearchItem(
                        value = _list.value.list[_currentIndex.value].value,
                        position = _currentIndex.value,
                        itemFound = it.elementFound
                    )
                }

                _list.value = _list.value.copy(
                    list = updatedList
                )
            }
        }
    }

}