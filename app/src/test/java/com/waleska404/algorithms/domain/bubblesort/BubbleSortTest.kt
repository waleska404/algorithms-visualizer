package com.waleska404.algorithms.domain.bubblesort

import com.waleska404.algorithms.domain.algoimlpementation.BubbleSortImpl
import com.waleska404.algorithms.domain.utils.ListSortTestCases
import com.waleska404.algorithms.testrules.CoroutinesTestRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class BubbleSortTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `bubble sort algorithm sorts correctly`() {
        val bubbleSort = BubbleSortImpl()
        val listMap = ListSortTestCases().listDictionary
        listMap.forEach { (list, sortedList) ->
            val myList = list.toMutableList()
            runBlocking {
                bubbleSort.runBubbleSort(myList).collect {}
            }
            assertEquals(myList, sortedList)
        }
    }
}