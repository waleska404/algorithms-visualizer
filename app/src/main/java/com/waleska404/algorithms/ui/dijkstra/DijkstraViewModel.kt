package com.waleska404.algorithms.ui.dijkstra

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waleska404.algorithms.domain.dijkstra.DijkstraImpl
import com.waleska404.algorithms.domain.dijkstra.Position
import com.waleska404.algorithms.ui.core.components.CellType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val NUMBER_OF_ROWS = 20
const val NUMBER_OF_COLUMNS = 7

const val GAME_DELAY_IN_MS = 10.toLong()

@HiltViewModel
class DijkstraViewModel @Inject constructor(
    val dijkstra: DijkstraImpl
) : ViewModel() {

    //private val startPosition = Position((NUMBER_OF_ROWS / 2), (NUMBER_OF_COLUMNS / 4))
    private val startPosition = Position(5, 3)

    //private val finishPosition = Position((NUMBER_OF_ROWS / 2), (NUMBER_OF_COLUMNS / 4) * 3)
    private val finishPosition = Position(15, 3)

    private var walls: MutableList<Position> = mutableListOf()

    private var _gridState = MutableStateFlow(getInitialGrid())
    val gridState: StateFlow<DijkstraGrid> = _gridState

    private var _isVisualizing = MutableStateFlow(false)
    val isVisualizing: StateFlow<Boolean> = _isVisualizing

    fun clear() {
        walls = mutableListOf()
        _isVisualizing.value = false
        _gridState.value = getInitialGrid()
    }

    private fun fakeWalls(): MutableList<Position> {
        walls = mutableListOf(
            Position(8, 0),
            Position(8, 1),
            Position(8, 2),
            Position(8, 3),
            Position(8, 4),
            Position(8, 5),
            Position(8, 6),
        )
        return walls
    }

    fun randomizeWalls() {
        clear()
        val newWalls = mutableListOf<Position>()
        for (i in 0 until _gridState.value.grid.size) {
            for (j in 0 until _gridState.value.grid[i].size) {
                val position = _gridState.value.grid[i][j].position

                if (isPositionNotAtStartOrFinish(position)) {
                    updateCellTypeAtPosition(position, weightedRandomWall())
                    if (_gridState.value.grid[i][j].type == CellType.WALL) {
                        newWalls.add(position)
                    }
                }
            }
        }
        walls = newWalls
    }

    /*
    fun drawCurrentGridState(): List<List<CellData>> {
        val updatedGrid = getInitGridState()

        for (i in 0 until updatedGrid.size) {
            for (j in 0 until updatedGrid[i].size) {
                updatedGrid[i][j] = gridState[i][j]
            }
        }

        return updatedGrid
    }*/

    fun getFinishPosition() = finishPosition

    //fun getCurrentGrid(): List<List<CellData>> = gridState

    fun toggleCellTypeToWall(p: Position) {
        if (getCellAtPosition(p).type == CellType.WALL) {
            updateCellTypeAtPosition(p, CellType.BACKGROUND)
            walls.remove(p)
        } else {
            updateCellTypeAtPosition(p, CellType.WALL)
            walls.add(p)
        }
    }

    private fun isPositionNotAtStartOrFinish(p: Position) =
        getCellAtPosition(p).type != CellType.START &&
                getCellAtPosition(p).type != CellType.FINISH

    fun onCellClicked(p: Position) {
        if (isPositionNotAtStartOrFinish(p) && !isVisualizing.value) {
            toggleCellTypeToWall(p)
        }
    }

    private fun updateCellTypeAtPosition(position: Position, cellType: CellType) {
        val i = position.row
        val j = position.column
        val newGrid = _gridState.value.grid.toMutableList()
        val newRow = newGrid[i].toMutableList()
        newRow[j] = newRow[j].copy(type = cellType)
        newGrid[i] = newRow
        _gridState.value = _gridState.value.copy(grid = newGrid)
    }

    private fun updateCellIsVisitedAtPosition(position: Position, isVisited: Boolean) {
        val i = position.row
        val j = position.column
        val newGrid = _gridState.value.grid.toMutableList()
        val newRow = newGrid[i].toMutableList()
        newRow[j] = newRow[j].copy(isVisited = isVisited)
        newGrid[i] = newRow
        _gridState.value = _gridState.value.copy(grid = newGrid)
    }

    private fun updateCellIsShortestPathAtPosition(position: Position, isShortestPath: Boolean) {
        val i = position.row
        val j = position.column
        val newGrid = _gridState.value.grid.toMutableList()
        val newRow = newGrid[i].toMutableList()
        newRow[j] = newRow[j].copy(isShortestPath = isShortestPath)
        newGrid[i] = newRow
        _gridState.value = _gridState.value.copy(grid = newGrid)
    }

    /*
    fun setCellVisitedAtPosition(p: Position) {
        gridState[p.row][p.column] = getCellAtPosition(p).copy(isVisited = true)
    }*/

    fun animatedShortestPath() {
        viewModelScope.launch {
            _isVisualizing.value = true
            val shortestPath = dijkstra.animatedDijkstra(
                gridSize = NUMBER_OF_ROWS * NUMBER_OF_COLUMNS,
                row = NUMBER_OF_ROWS,
                col = NUMBER_OF_COLUMNS,
                finish = finishPosition,
                start = startPosition,
                walls = walls
            ).collect { dijkstraInfo ->
                if (dijkstraInfo.unreachable) this.cancel(null)
                if (dijkstraInfo.finished && !dijkstraInfo.shortestPath.isNullOrEmpty()) {
                    dijkstraInfo.shortestPath.forEach {
                        Log.i("MYTAG", "shortestPath: ${it.position.row}, ${it.position.column}")
                        val p = it.position
                        updateCellIsShortestPathAtPosition(p, true)
                        delay(GAME_DELAY_IN_MS)
                    }
                } else if (dijkstraInfo.position != null) {
                    updateCellIsVisitedAtPosition(dijkstraInfo.position, true)
                }
            }
            /*
            shortestPath.forEach {
                val p = it.position
                gridState[p.row][p.column] = getCellAtPosition(p).copy(isShortestPath = true)
                delay(GAME_DELAY_IN_MS)
            }*/
        }
    }

    fun getFinishCell() = getCellAtPosition(finishPosition)

    private fun getCellAtPosition(p: Position) = _gridState.value.grid[p.row][p.column]

    private fun getInitGridState() = getGridWithClearBackground()

    private fun getGridWithClearBackground(): DijkstraGrid {
        val mutableGrid = List(NUMBER_OF_ROWS) {
            MutableList(NUMBER_OF_COLUMNS) {
                CellData(CellType.BACKGROUND, Position(0, 0))
            }
        }
        for (i in 0 until NUMBER_OF_ROWS) {
            for (j in 0 until NUMBER_OF_COLUMNS) {
                mutableGrid[i][j] = CellData(CellType.BACKGROUND, Position(i, j))
            }
        }

        return DijkstraGrid(
            grid = mutableGrid
        )
    }

    private fun getInitialGrid(): DijkstraGrid {
        Log.i("MYTAG", "getInitialGRID: startPosition: $startPosition, finishPosition: $finishPosition")
        val mutableGrid = List(NUMBER_OF_ROWS) {
            MutableList(NUMBER_OF_COLUMNS) {
                CellData(CellType.BACKGROUND, Position(0, 0))
            }
        }
        for (i in 0 until NUMBER_OF_ROWS) {
            for (j in 0 until NUMBER_OF_COLUMNS) {
                if (startPosition == Position(i, j)) {
                    Log.i("MYTAG", "START POSITION: $i, $j")
                    mutableGrid[i][j] = CellData(CellType.START, Position(i, j))
                } else if (finishPosition == Position(i, j)) {
                    Log.i("MYTAG", "FINISH POSITION: $i, $j")
                    mutableGrid[i][j] = CellData(CellType.FINISH, Position(i, j))
                } else {
                    mutableGrid[i][j] = CellData(CellType.BACKGROUND, Position(i, j))
                }
            }
        }
        if (walls.isNotEmpty()) {
            for (p in walls) {
                mutableGrid[p.row][p.column] = CellData(CellType.WALL, Position(p.row, p.column))
            }
        }

        return DijkstraGrid(
            grid = mutableGrid
        )
    }

    private fun weightedRandomWall(): CellType {
        val random = (0..100).random()
        return if (random in 0..70) CellType.BACKGROUND else CellType.WALL
    }
}

