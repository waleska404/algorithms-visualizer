package com.waleska404.algorithms.ui.dijkstra

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waleska404.algorithms.domain.dijkstra.Dijkstra
import com.waleska404.algorithms.domain.dijkstra.Position
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//TODO: check best practice to do this
const val NUMBER_OF_ROWS = 15
const val NUMBER_OF_COLUMNS = 9

const val GAME_DELAY_IN_MS = 10.toLong()

@HiltViewModel
class DijkstraViewModel @Inject constructor(
    val dijkstra: Dijkstra
) : ViewModel() {

    //TODO: check best practice to do this
    private val startPosition = Position(1, 4)
    private val finishPosition = Position(14, 4)

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

    private fun toggleCellTypeToWall(p: Position) {
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

    fun animatedShortestPath() {
        viewModelScope.launch {
            _isVisualizing.value = true
            dijkstra.runDijkstra(
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
                        val p = it.position
                        updateCellIsShortestPathAtPosition(p, true)
                        delay(GAME_DELAY_IN_MS)
                    }
                    this.cancel(null)
                } else if (dijkstraInfo.position != null) {
                    updateCellIsVisitedAtPosition(dijkstraInfo.position, true)
                }
            }
        }
    }

    private fun getCellAtPosition(p: Position) = _gridState.value.grid[p.row][p.column]

    private fun getInitialGrid(): DijkstraGrid {
        val mutableGrid = List(NUMBER_OF_ROWS) {
            MutableList(NUMBER_OF_COLUMNS) {
                CellData(CellType.BACKGROUND, Position(0, 0))
            }
        }
        for (i in 0 until NUMBER_OF_ROWS) {
            for (j in 0 until NUMBER_OF_COLUMNS) {
                if (startPosition == Position(i, j)) {
                    mutableGrid[i][j] = CellData(CellType.START, Position(i, j))
                } else if (finishPosition == Position(i, j)) {
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

