package com.waleska404.algorithms.domain.dijkstra

data class Position(
    val row: Int,
    val column: Int
)

enum class CellType {
    START,
    FINISH,
    WALL,
    BACKGROUND,
}

data class CellDomainData(
    var id: Int,
    var type: CellType,
    val position: Position,
    val isVisited: Boolean = false,
    val isShortestPath: Boolean = false,
    var distance: Int = Int.MAX_VALUE,
    var previousShortestCell: CellDomainData? = null,
)

data class DijkstraDomainModel(
    val position: Position,
    val type: CellType,
    val visited: Boolean
)