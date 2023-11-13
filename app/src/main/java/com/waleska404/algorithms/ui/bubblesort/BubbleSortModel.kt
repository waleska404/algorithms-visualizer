package com.waleska404.algorithms.ui.bubblesort

data class BubbleSortList(
    val list: List<BubbleSortItem>
) {
    fun toDataList(): MutableList<Int> {
        return list.map {
            it.value
        }.toMutableList()
    }
}

data class BubbleSortItem(
    val id: Int,
    val isCurrentlyCompared: Boolean,
    val value: Int,
)
