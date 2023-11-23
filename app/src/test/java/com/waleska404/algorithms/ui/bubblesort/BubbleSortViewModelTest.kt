package com.waleska404.algorithms.ui.bubblesort

import com.waleska404.algorithms.domain.bubblesort.BubbleSort
import com.waleska404.algorithms.domain.bubblesort.BubbleSortDomainModel
import com.waleska404.algorithms.testrules.CoroutinesTestRule
import com.waleska404.algorithms.testrules.GameDelayTestRule
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
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
    val gameDelayRule = GameDelayTestRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var bubbleSort: BubbleSort

    private lateinit var viewModel: BubbleSortViewModel

    @Before
    fun setUp() {
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `startSorting updates listToSort variable correctly`()  = runTest {
        // arrange
        every { bubbleSort.runBubbleSort(any(), any()) } returns flowOf(
            BubbleSortDomainModel(
                currentItem = 0,
                shouldSwap = false
            ),
            BubbleSortDomainModel(
                currentItem = 1,
                shouldSwap = true
            ),
        )
        viewModel.randomizeCurrentList(3)
        val oldList = viewModel.listToSort.value.list.toMutableList()

        // swap values on indexes 1 and 2
        val temp = oldList[1]
        oldList[1] = oldList[2]
        oldList[2] = temp

        // act
        launch {
            viewModel.startSorting()
        }
        advanceUntilIdle()

        // assert
        val newList = viewModel.listToSort.value.list.toMutableList()
        assertEquals(oldList, newList)
    }
}