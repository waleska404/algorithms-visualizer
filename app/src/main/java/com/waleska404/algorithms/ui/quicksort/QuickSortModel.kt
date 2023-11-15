package com.waleska404.algorithms.ui.quicksort

data class QuickSortList(
    val list: List<QuickSortItem>
) {
    fun toDataList(): MutableList<Int> {
        return list.map {
            it.value
        }.toMutableList()
    }
}

data class QuickSortItem(
    val id: Int,
    val isPivot: Boolean,
    val isLeftPointer: Boolean,
    val isRightPointer: Boolean,
    val alreadyOrdered: Boolean,
    val inSortingRange: Boolean,
    val value: Int,
)