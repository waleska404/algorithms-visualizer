package com.waleska404.algorithms.domain.linearsearch

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LinearSearchImpl @Inject constructor() : LinearSearch {
    override suspend fun compare(
        list: MutableList<Int>,
        searchItem: Int,
        position: Int
    ): Flow<LinearSearchDomainModel> {
        return flow {
            emit(LinearSearchDomainModel(position, list[position] == searchItem))
        }
    }
}