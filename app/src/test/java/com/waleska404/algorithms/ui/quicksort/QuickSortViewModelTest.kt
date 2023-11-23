package com.waleska404.algorithms.ui.quicksort

import com.waleska404.algorithms.domain.quicksort.QuickSort
import com.waleska404.algorithms.domain.quicksort.QuickSortDomainModel
import com.waleska404.algorithms.testrules.CoroutinesTestRule
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuickSortViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var quickSort: QuickSort

    private lateinit var viewModel: QuickSortViewModel

    @Before
    fun setUp() {
        viewModel = QuickSortViewModel(quickSort)
    }

    @Test
    fun `list to sort is initialized`() {
        Assert.assertFalse(viewModel.listToSort.value.list.isEmpty())
    }

    @Test
    fun `isSorting variable is initialized to false`() {
        Assert.assertFalse(viewModel.isSorting.value)
    }

    @Test
    fun `randomList updates correctly listToSort variable`() {
        val oldList = viewModel.listToSort.value
        val oldSize = oldList.list.size

        viewModel.randomizeCurrentList(oldSize + 2)

        val newList = viewModel.listToSort.value
        Assert.assertNotEquals(oldList, newList)
    }

    @Test
    fun `startSorting updates isSorting variable to true`() {
        Assert.assertFalse(viewModel.isSorting.value)

        viewModel.startSorting()

        Assert.assertTrue(viewModel.isSorting.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `startSorting updates listToSort variable correctly`() = runTest {
        // arrange
        every { quickSort.runQuickSort(any(), any(), any()) } returns flowOf(
            QuickSortDomainModel(
                currentPivot = 0,
                currentLeft = 1,
                currentRight = 2,
                shouldSwapPointers = true,
                shouldSwapPivot = false,
                sortingSubarray = Pair(1, 2),
            ),
            QuickSortDomainModel(
                currentPivot = 0,
                currentLeft = 1,
                currentRight = 3,
                shouldSwapPointers = false,
                shouldSwapPivot = true,
                sortingSubarray = Pair(1, 3),
            )
        )

        viewModel.randomizeCurrentList(4)
        val oldList = viewModel.listToSort.value.list.toMutableList()

        // swap values on indexes 1 and 2
        var temp = oldList[1]
        oldList[1] = oldList[2].copy(inSortingRange = true)
        oldList[2] = temp.copy(inSortingRange = true)

        // swap values on indexes 0 and 3
        temp = oldList[0]
        oldList[0] = oldList[3].copy(inSortingRange = true)
        oldList[3] = temp

        // act
        launch {
            viewModel.startSorting()
        }
        advanceUntilIdle()

        // assert
        val newList = viewModel.listToSort.value.list.toMutableList()
        println("MYTAG NEW LIST: $newList")
        println("MYTAG OLD LIST: $oldList")
        Assert.assertEquals(oldList, newList)
    }
}