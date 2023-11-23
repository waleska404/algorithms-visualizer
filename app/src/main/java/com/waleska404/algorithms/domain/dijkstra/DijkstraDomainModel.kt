package com.waleska404.algorithms.domain.dijkstra

data class Position(
    val row: Int,
    val column: Int
)

enum class CellDomainType {
    START,
    FINISH,
    WALL,
    BACKGROUND,
}

data class CellDomainData(
    var id: Int,
    var type: CellDomainType,
    val position: Position,
    val isVisited: Boolean = false,
    var distance: Int = Int.MAX_VALUE,
    var previousShortestCell: CellDomainData? = null,
)

data class DijkstraDomainModel(
    val position: Position? = null,
    val visited: Boolean? = null,
    val finished: Boolean,
    val unreachable: Boolean = false,
    val shortestPath: List<CellDomainData>? = null,
)