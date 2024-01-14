package com.waleska404.algorithms.ui.linearsearch

data class LinearSearchList(
    val list: List<LinearSearchItem>
) {
    fun toDataList(): MutableList<Int> {
        return list.map {
            it.value
        }.toMutableList()
    }
}

data class LinearSearchItem(
    val value: Int,
    var position: Int,
    var itemFound: Boolean
)