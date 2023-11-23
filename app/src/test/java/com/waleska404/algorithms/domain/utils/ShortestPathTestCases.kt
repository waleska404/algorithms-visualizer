package com.waleska404.algorithms.domain.utils

import com.waleska404.algorithms.domain.dijkstra.Position

class ListShortestPathTestCases {
    val list = listOf(
        grid1,
        grid2,
        grid3
    )
}

data class ShortestPathTestModel(
    val gridSize: Int,
    val numberOfRows: Int,
    val numberOfColumns: Int,
    val start: Position,
    val finish: Position,
    val walls: List<Position>,
    val solutionPath: List<Position>,
)

val grid1 = ShortestPathTestModel(
    gridSize = 20,
    numberOfRows = 5,
    numberOfColumns = 4,
    start = Position(0, 0),
    finish = Position(0, 3),
    walls = listOf(),
    solutionPath = listOf(
        Position(0, 0),
        Position(0, 1),
        Position(0, 2),
        Position(0, 3)
    )
)

val grid2 = ShortestPathTestModel(
    gridSize = 20,
    numberOfRows = 5,
    numberOfColumns = 4,
    start = Position(0, 1),
    finish = Position(4, 1),
    walls = listOf(),
    solutionPath = listOf(
        Position(0, 1),
        Position(1, 1),
        Position(2, 1),
        Position(3, 1),
        Position(4, 1)
    )
)

val grid3 = ShortestPathTestModel(
    gridSize = 20,
    numberOfRows = 5,
    numberOfColumns = 4,
    start = Position(1, 3),
    finish = Position(4, 0),
    walls = listOf(
        Position(2, 1),
        Position(2, 2),
        Position(2, 3),
    ),
    solutionPath = listOf(
        Position(1, 3),
        Position(1, 2),
        Position(1, 1),
        Position(1, 0),
        Position(2, 0),
        Position(3, 0),
        Position(4, 0)
    )
)