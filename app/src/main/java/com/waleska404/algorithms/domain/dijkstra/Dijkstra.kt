package com.waleska404.algorithms.domain.dijkstra

import kotlinx.coroutines.flow.Flow

interface Dijkstra {
    suspend fun runDijkstra(
    gridSize: Int,
    row: Int,
    col: Int,
    start: Position,
    finish: Position,
    walls: List<Position>
    ): Flow<DijkstraDomainModel>
}