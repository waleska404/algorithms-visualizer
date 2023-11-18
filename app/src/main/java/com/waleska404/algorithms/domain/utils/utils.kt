package com.waleska404.algorithms.domain.utils

import com.waleska404.algorithms.domain.dijkstra.CellDomainData
import com.waleska404.algorithms.domain.dijkstra.Position
import com.waleska404.algorithms.ui.dijkstra.CellData

fun <E> MutableList<E>.swap(i: Int, j: Int) {
    val temp = this[i]
    this[i] = this[j]
    this[j] = temp
}

fun List<List<CellData>>.toLinearGrid(): MutableList<CellData> {
    val mutableList = mutableListOf<CellData>()
    for (i in this.indices) {
        for (j in this[i].indices) {
            mutableList.add(this[i][j])
        }
    }
    return mutableList
}

fun MutableList<CellDomainData>.shift(): CellDomainData {
    val first = this.first()
    this.removeAt(0)
    return first
}

fun CellDomainData.isAtPosition(position: Position) =
    this.position.row == position.row && this.position.column == position.column


fun MutableList<CellDomainData>.findIndexByCell(cell: CellDomainData): Int {
    for (i in 0 until this.size) {
        if (this[i].id == cell.id) {
            return i
        }
    }
    return -1
}