package com.waleska404.algorithms.ui.dijkstra

import com.waleska404.algorithms.domain.dijkstra.CellDomainData
import com.waleska404.algorithms.domain.dijkstra.CellDomainType
import com.waleska404.algorithms.domain.dijkstra.Dijkstra
import com.waleska404.algorithms.domain.dijkstra.DijkstraDomainModel
import com.waleska404.algorithms.domain.dijkstra.Position
import com.waleska404.algorithms.testrules.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
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
        assertFalse(viewModel.gridState.value.grid.isEmpty())
    }

    @Test
    fun `isSorting variable is initialized to false`() {
        assertFalse(viewModel.isVisualizing.value)
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
        val walls = getWallsFromCellList(viewModel.gridState.value.toLinearGrid()).count { it }
        Assert.assertEquals(0, walls)
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `animatedShortestPath updates gridState variable correctly`() = runTest {
        assertFalse(viewModel.gridState.value.grid[1][1].isVisited)
        assertFalse(viewModel.gridState.value.grid[2][1].isVisited)

        // arrange
        coEvery { dijkstra.runDijkstra(any(), any(), any(), any(), any(), any(), any()) } returns flowOf(
            DijkstraDomainModel(
                position = Position(1, 1),
                visited = true,
                finished = false,
                unreachable = false,
            ),
            DijkstraDomainModel(
                position = Position(2, 1),
                visited = true,
                finished = true,
                unreachable = false,
                shortestPath = listOf(
                    CellDomainData(
                        id = 1,
                        type = CellDomainType.START,
                        position = Position(1, 1),
                        isVisited = true,
                        distance = 0
                    ),
                    CellDomainData(
                        id = 2,
                        type = CellDomainType.START,
                        position = Position(2, 1),
                        isVisited = true,
                        distance = 0
                    ),
                    CellDomainData(
                        id = 3,
                        type = CellDomainType.START,
                        position = Position(3, 1),
                        isVisited = true,
                        distance = 0
                    )
                )
            )
        )

        // act
        launch {
            viewModel.animatedShortestPath()
        }
        advanceUntilIdle()

        // assert
        assertTrue(viewModel.gridState.value.grid[1][1].isVisited)
        assertTrue(viewModel.gridState.value.grid[1][1].isShortestPath)
        assertTrue(viewModel.gridState.value.grid[2][1].isShortestPath)
        assertTrue(viewModel.gridState.value.grid[3][1].isShortestPath)
    }


    private fun getWallsFromCellList(flattenList: MutableList<CellData>): List<Boolean> =
        flattenList.map { it.type == CellType.WALL }
}