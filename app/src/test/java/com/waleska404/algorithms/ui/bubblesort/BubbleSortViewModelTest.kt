package com.waleska404.algorithms.ui.bubblesort

import com.waleska404.algorithms.domain.algointerface.BubbleSort
import com.waleska404.algorithms.domain.model.BubbleSortInfo
import com.waleska404.algorithms.testrules.CoroutinesTestRule
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BubbleSortViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var bubbleSort: BubbleSort

    private lateinit var viewModel: BubbleSortViewModel

    @Before
    fun setUp() {
        every { bubbleSort.runBubbleSort(any()) } returns flowOf(
            BubbleSortInfo(
                currentItem = 0,
                shouldSwap = false
            ),
            BubbleSortInfo(
                currentItem = 1,
                shouldSwap = true
            ),
            BubbleSortInfo(
                currentItem = 2,
                shouldSwap = false
            ),
        )
        viewModel = BubbleSortViewModel(bubbleSort)
    }

    @Test
    fun `list to sort is initialized`() {
        assertFalse(viewModel.listToSort.value.list.isEmpty())
    }

    @Test
    fun `isSorting variable is initialized to false`(){
        assertFalse(viewModel.isSorting.value)
    }

    @Test
    fun `randomList updates correctly listToSort variable`() {
        val oldList = viewModel.listToSort.value
        val oldSize = oldList.list.size

        viewModel.randomizeCurrentList(oldSize + 2)

        val newList = viewModel.listToSort.value
        assertNotEquals(oldList, newList)
    }

    @Test
    fun `startSorting updates isSorting variable to true`(){
        assertFalse(viewModel.isSorting.value)

        viewModel.startSorting()

        assertTrue(viewModel.isSorting.value)
    }

    @Test
    fun `startSorting updates listToSort variable correctly`() = runTest {
        // TODO
        /*
        viewModel.randomList(3)
        val oldList = viewModel.listToSort.value.list.toMutableList()
        println("MYTAG list1: $oldList")
        // swap values on indexes 1 and 2
        val temp = oldList[1]
        oldList[1] = oldList[2]
        oldList[2] = temp
        println("MYTAG list2: $oldList")
        viewModel.listToSort.test {
            viewModel.startSorting()
            delay(5000)
            assertEquals(oldList, awaitItem().list.toMutableList())
        }
        val newList = viewModel.listToSort.value.list.toMutableList()
        println("MYTAG list3: $newList")
        */
    }


}