package com.waleska404.algorithms.domain.dijkstra

import com.waleska404.algorithms.domain.utils.findIndexByCell
import com.waleska404.algorithms.domain.utils.isAtPosition
import com.waleska404.algorithms.domain.utils.shift
import com.waleska404.algorithms.ui.dijkstra.GAME_DELAY_IN_MS
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.math.floor

class DijkstraImpl: Dijkstra {

    override suspend fun runDijkstra(
        gridSize: Int,
        row: Int,
        col: Int,
        start: Position,
        finish: Position,
        walls: List<Position>
    ): Flow<DijkstraDomainModel> {
        return flow {
            // set up initial variables
            val visitedNodesInOrder = mutableListOf<CellDomainData>()
            val startIndex = getIndexFromPosition(start, col)
            val finishIndex = getIndexFromPosition(finish, col)
            val unvisitedNodes = MutableList(gridSize) {
                CellDomainData(
                    id = it,
                    type = CellType.BACKGROUND,
                    position = getPositionFromIndex(it, col),
                    distance = Int.MAX_VALUE
                )
            }
            unvisitedNodes[startIndex] = unvisitedNodes[startIndex].copy(
                distance = 0,
                type = CellType.START,
            )
            unvisitedNodes[finishIndex] = unvisitedNodes[finishIndex].copy(
                type = CellType.FINISH
            )
            walls.forEach {
                val index = getIndexFromPosition(it, col)
                unvisitedNodes[index] = unvisitedNodes[index].copy(
                    type = CellType.WALL
                )
            }
            val allNodes = unvisitedNodes.toMutableList()

            // main loop
            while (unvisitedNodes.isNotEmpty()) {
                sortNodesByDistance(unvisitedNodes)
                val closestCell = unvisitedNodes.shift()

                // wall case
                if (closestCell.type == CellType.WALL) continue

                // target not reachable case
                if (closestCell.distance == Int.MAX_VALUE) {
                    emit(
                        DijkstraDomainModel(
                            finished = true,
                            shortestPath = null,
                            unreachable = true
                        )
                    )
                }

                // background case
                if(closestCell.type != CellType.WALL) {
                    emit(
                        DijkstraDomainModel(
                            position = closestCell.position,
                            type = closestCell.type,
                            visited = true,
                            finished = false
                        )
                    )
                }

                // update allNodes list
                allNodes[closestCell.id] = closestCell.copy(isVisited = true)
                // update visitedNodes list
                visitedNodesInOrder.add(allNodes[closestCell.id])

                // target reached case
                if (closestCell.isAtPosition(finish)) {
                    emit(
                        DijkstraDomainModel(
                            finished = true,
                            shortestPath = getShortestPathOrder(allNodes[finishIndex])
                        )
                    )
                }

                // update neighbors
                updateUnvisitedNeighbors(
                    cell = closestCell,
                    allNodes = allNodes,
                    unvisitedNodes = unvisitedNodes,
                    row = row,
                    col = col
                )
                // TODO: poner esta variable en un sitio más core, un config comun a todas las capas o algo
                // o del domain puede venir al ui????? igual si
                delay(GAME_DELAY_IN_MS)
            }
            emit(
                DijkstraDomainModel(
                    finished = true,
                    shortestPath = getShortestPathOrder(allNodes[finishIndex])
                )
            )
        }
    }

    private fun getPositionFromIndex(index: Int, col: Int): Position {
        return Position(
            row = floor(index.toDouble() / col).toInt(),
            column = index % col
        )
    }

    private fun getIndexFromPosition(position: Position, col: Int): Int {
        return position.row * col + position.column
    }

    private fun getShortestPathOrder(finishedCell: CellDomainData): List<CellDomainData> {
        val nodesInShortestPathOrder = mutableListOf<CellDomainData>()
        var currentCell: CellDomainData? = finishedCell
        while (currentCell != null) {
            nodesInShortestPathOrder.add(0, currentCell)
            currentCell = currentCell.previousShortestCell
        }
        return nodesInShortestPathOrder
    }

    private fun sortNodesByDistance(unvisitedNodes: MutableList<CellDomainData>) {
        unvisitedNodes.sortBy { it.distance }
    }

    private fun updateUnvisitedNeighbors(
        cell: CellDomainData,
        allNodes: MutableList<CellDomainData>,
        unvisitedNodes: MutableList<CellDomainData>,
        row: Int,
        col: Int
    ) {
        val unvisitedNeighbors = getUnvisitedNeighbors(cell, allNodes, row, col)
        for (neighbor in unvisitedNeighbors) {
            val index = unvisitedNodes.findIndexByCell(neighbor)
            if (index != -1) {
                unvisitedNodes[index].distance = cell.distance + 1
                unvisitedNodes[index].previousShortestCell = cell
            }
        }
    }

    private fun getUnvisitedNeighbors(
        cell: CellDomainData,
        grid: MutableList<CellDomainData>,
        maxRow: Int,
        maxCol: Int
    ): List<CellDomainData> {
        val neighbors = mutableListOf<CellDomainData>()
        val (row, column) = cell.position

        if (row > 0) neighbors.add(grid[getIndexFromPosition(Position(row - 1, column), maxCol)])
        if (row < maxRow - 1) neighbors.add(grid[getIndexFromPosition(Position(row + 1, column), maxCol)])
        if (column > 0) neighbors.add(grid[getIndexFromPosition(Position(row, column - 1), maxCol)])
        if (column < maxCol - 1) neighbors.add(grid[getIndexFromPosition(Position(row, column + 1), maxCol)])

        return neighbors.filter { !it.isVisited }
    }
}
