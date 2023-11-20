package com.waleska404.algorithms.ui.dijkstra

import com.waleska404.algorithms.domain.dijkstra.Position

data class DijkstraGrid(
    val grid: List<List<CellData>>
) {
    fun toLinearGrid(): MutableList<CellData> =
        grid.flatten().toMutableList()
}

data class CellData(
    var type: CellType,
    val position: Position,
    val isVisited: Boolean = false,
    val isShortestPath: Boolean = false,
    var distance: Int = Int.MAX_VALUE,
    var previousShortestCell: CellData? = null,
    var id: Int = (0..Int.MAX_VALUE).random()
)