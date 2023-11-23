package com.waleska404.algorithms.domain.dijkstra

import com.waleska404.algorithms.domain.utils.ListShortestPathTestCases
import com.waleska404.algorithms.testrules.CoroutinesTestRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class DijkstraTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `dijkstra algorithm finds the shortest path correctly`() {
        val dijkstra = DijkstraImpl()
        val testCases = ListShortestPathTestCases().list
        testCases.forEach {
            runBlocking {
                dijkstra.runDijkstra(
                    gridSize = it.gridSize,
                    numberOfRows = it.numberOfRows,
                    numberOfColumns = it.numberOfColumns,
                    start = it.start,
                    finish = it.finish,
                    walls = it.walls,
                    delayInMs = 0
                ).collect { dijkstraInfo ->
                    if (dijkstraInfo.finished && !dijkstraInfo.shortestPath.isNullOrEmpty()) {
                        val actualPath = dijkstraInfo.shortestPath?.map { it.position }
                        Assert.assertEquals(it.solutionPath, actualPath)
                    }
                }
            }
        }
    }
}