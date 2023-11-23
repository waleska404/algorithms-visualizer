package com.waleska404.algorithms.ui.dijkstra

import com.waleska404.algorithms.domain.dijkstra.Dijkstra
import com.waleska404.algorithms.domain.dijkstra.Position
import com.waleska404.algorithms.testrules.CoroutinesTestRule
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class DijkstraViewModelTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var dijkstra: Dijkstra

    private lateinit var viewModel: DijkstraViewModel

    @Before
    fun setUp() {
        viewModel = DijkstraViewModel(dijkstra)
    }

    @Test
    fun `grid is initialized`() {
        Assert.assertFalse(viewModel.gridState.value.grid.isEmpty())
    }

    @Test
    fun `isSorting variable is initialized to false`() {
        Assert.assertFalse(viewModel.isVisualizing.value)
    }

    @Test
    fun `randomizeWalls updates correctly walls variable`() {
        val oldWalls = getWallsFromCellList(viewModel.gridState.value.toLinearGrid())

        viewModel.randomizeWalls()

        val newWalls = getWallsFromCellList(viewModel.gridState.value.toLinearGrid())
        Assert.assertNotEquals(oldWalls, newWalls)
    }

    @Test
    fun `clear function updates correctly walls variable`() {
        viewModel.randomizeWalls()
        viewModel.clear()
        val walls = getWallsFromCellList(viewModel.gridState.value.toLinearGrid())
        Assert.assertEquals(0, walls.size)
    }

    @Test
    fun `onCellClicked updates correctly the cell if there is no animation running`() {
        viewModel.clear()
        val oldCellType = viewModel.gridState.value.grid[1][1].type

        viewModel.onCellClicked(Position(1, 1))

        val newCellType = viewModel.gridState.value.grid[1][1].type
        Assert.assertNotEquals(oldCellType, newCellType)
    }

    @Test
    fun `onCellClicked does not update the cell if there is an animation running`() {
        viewModel.clear()
        val oldCellType = viewModel.gridState.value.grid[1][1].type
        viewModel.animatedShortestPath()

        viewModel.onCellClicked(Position(1, 1))

        val newCellType = viewModel.gridState.value.grid[1][1].type
        Assert.assertEquals(oldCellType, newCellType)
    }


    private fun getWallsFromCellList(flattenList: MutableList<CellData>): List<Boolean> =
        flattenList.map { it.type == CellType.WALL }
}