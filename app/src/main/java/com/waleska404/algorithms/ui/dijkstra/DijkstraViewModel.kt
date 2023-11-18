package com.waleska404.algorithms.ui.dijkstra

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waleska404.algorithms.domain.dijkstra.DijkstraImpl
import com.waleska404.algorithms.domain.dijkstra.Position
import com.waleska404.algorithms.ui.core.components.CellType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

const val NUMBER_OF_ROWS = 10
const val NUMBER_OF_COLUMNS = 6

const val GAME_DELAY_IN_MS = 10.toLong()

@HiltViewModel
class DijkstraViewModel @Inject constructor(
    val dijkstra: DijkstraImpl
) : ViewModel() {

    private var gridState: MutableList<MutableList<CellData>> = mutableListOf()
    private var walls: List<Position> = listOf()

    private val startPosition = Position((NUMBER_OF_ROWS / 2), (NUMBER_OF_COLUMNS / 4))
    private val finishPosition = Position((NUMBER_OF_ROWS / 2), (NUMBER_OF_COLUMNS / 4) * 3)

    var isVisualizing = false
        private set

    init {
        clear()
    }

    fun clear() {
        gridState = getInitGridState()
        walls = listOf()
        isVisualizing = false
        addStartAndFinishGrids()
    }

    fun randomizeWalls() {
        clear()
        val newWalls = mutableListOf<Position>()
        for (i in 0 until gridState.size) {
            for (j in 0 until gridState[i].size) {
                val position = gridState[i][j].position

                if (isPositionNotAtStartOrFinish(position)) {
                    updateCellTypeAtPosition(position, weightedRandomWall())
                    if (gridState[i][j].type == CellType.WALL) {
                        newWalls.add(position)
                    }
                }
            }
        }
        walls = newWalls
    }

    fun drawCurrentGridState(): List<List<CellData>> {
        val updatedGrid = getInitGridState()

        for (i in 0 until updatedGrid.size) {
            for (j in 0 until updatedGrid[i].size) {
                updatedGrid[i][j] = gridState[i][j]
            }
        }

        return updatedGrid
    }

    fun getFinishPosition() = finishPosition

    fun getCurrentGrid(): List<List<CellData>> = gridState

    fun toggleCellTypeToWall(p: Position) {
        if (getCellAtPosition(p).type == CellType.WALL) {
            updateCellTypeAtPosition(p, CellType.BACKGROUND)
        } else {
            updateCellTypeAtPosition(p, CellType.WALL)
        }
    }

    fun isPositionNotAtStartOrFinish(p: Position) =
        getCellAtPosition(p).type != CellType.START &&
                getCellAtPosition(p).type != CellType.FINISH


    private fun updateCellTypeAtPosition(p: Position, cellType: CellType) {
        gridState[p.row][p.column] = getCellAtPosition(p).copy(type = cellType)
    }

    fun setCellVisitedAtPosition(p: Position) {
        gridState[p.row][p.column] = getCellAtPosition(p).copy(isVisited = true)
    }

    fun animatedShortestPath() {
        viewModelScope.launch {
            isVisualizing = true
            val shortestPath = dijkstra.animatedDijkstra(
                gridSize = NUMBER_OF_ROWS * NUMBER_OF_COLUMNS,
                row = NUMBER_OF_ROWS,
                col = NUMBER_OF_COLUMNS,
                finish = finishPosition,
                start = startPosition,
                walls = walls
            ).collect { dijkstraInfo ->
                if (dijkstraInfo.finished && !dijkstraInfo.shortestPath.isNullOrEmpty()) {
                    dijkstraInfo.shortestPath.forEach {
                        Log.i("MYTAG", "shortestPath: ${it.position.row}, ${it.position.column}")
                        val p = it.position
                        gridState[p.row][p.column] = gridState[p.row][p.column].copy(isShortestPath = true)
                        delay(GAME_DELAY_IN_MS)
                    }
                } else if (dijkstraInfo.position != null) {
                    gridState[dijkstraInfo.position.row][dijkstraInfo.position.column] =
                        gridState[dijkstraInfo.position.row][dijkstraInfo.position.column].copy(
                            isVisited = true
                        )
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

    fun getCellAtPosition(p: Position) = gridState[p.row][p.column]

    private fun addStartAndFinishGrids() {
        gridState[startPosition.row][startPosition.column] =
            CellData(CellType.START, startPosition, distance = 0)
        gridState[finishPosition.row][finishPosition.column] =
            CellData(CellType.FINISH, finishPosition)
    }

    private fun getInitGridState() = getGridWithClearBackground()

    fun getGridWithClearBackground(): MutableList<MutableList<CellData>> {
        val mutableGrid = MutableList(NUMBER_OF_ROWS) {
            MutableList(NUMBER_OF_COLUMNS) {
                CellData(CellType.BACKGROUND, Position(0, 0))
            }
        }

        for (i in 0 until NUMBER_OF_ROWS) {
            for (j in 0 until NUMBER_OF_COLUMNS) {
                mutableGrid[i][j] = CellData(CellType.BACKGROUND, Position(i, j))
            }
        }

        return mutableGrid
    }

    private fun weightedRandomWall(): CellType {
        val random = (0..100).random()
        return if (random in 0..70) CellType.BACKGROUND else CellType.WALL
    }
}

