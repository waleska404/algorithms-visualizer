package com.waleska404.algorithms.domain.utils

class ListSortTestCases {
    val listDictionary = hashMapOf(
        list1 to list1Sorted,
        list2 to list2Sorted,
        list3 to list3Sorted,
        list4 to list4Sorted
    )
}

val list1 = listOf(8, 3, 6, 1)
val list1Sorted = listOf(1, 3, 6, 8)

val list2 = listOf(1, 1, 1, 1, 1, 1, 0)
val list2Sorted = listOf(0, 1, 1, 1, 1, 1, 1)

val list3 = listOf(456, 123, 789)
val list3Sorted = listOf(123, 456, 789)

val list4 = listOf(9, 1)
val list4Sorted = listOf(1, 9)




