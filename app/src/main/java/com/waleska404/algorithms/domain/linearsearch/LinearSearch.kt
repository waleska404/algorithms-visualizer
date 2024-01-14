package com.waleska404.algorithms.domain.linearsearch
import kotlinx.coroutines.flow.Flow
interface LinearSearch {
    suspend fun compare(list: MutableList<Int>, searchItem: Int, position: Int): Flow<LinearSearchDomainModel>
}