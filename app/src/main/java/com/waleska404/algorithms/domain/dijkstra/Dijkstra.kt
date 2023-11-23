package com.waleska404.algorithms.domain.dijkstra

import kotlinx.coroutines.flow.Flow

interface Dijkstra {
    suspend fun runDijkstra(
        gridSize: Int,
        numberOfRows: Int,
        numberOfColumns: Int,
        start: Position,
        finish: Position,
        walls: List<Position>,
        delayInMs: Long
    ): Flow<DijkstraDomainModel>
}