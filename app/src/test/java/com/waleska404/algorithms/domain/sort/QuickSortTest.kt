package com.waleska404.algorithms.domain.sort

import com.waleska404.algorithms.domain.algoimlpementation.QuickSortImpl
import com.waleska404.algorithms.domain.utils.ListSortTestCases
import com.waleska404.algorithms.testrules.CoroutinesTestRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class QuickSortTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `quick sort algorithm sorts correctly`() {
        val quickSort = QuickSortImpl()
        val listMap = ListSortTestCases().listDictionary
        listMap.forEach { (list, sortedList) ->
            val myList = list.toMutableList()
            runBlocking {
                quickSort.runQuickSort(myList, 0, myList.size - 1).collect {}
            }
            assertEquals(myList, sortedList)
        }
    }
}