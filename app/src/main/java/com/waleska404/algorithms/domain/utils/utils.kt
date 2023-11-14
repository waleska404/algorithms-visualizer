package com.waleska404.algorithms.domain.utils

fun <E> MutableList<E>.swap(i: Int, j: Int) {
    val temp = this[i]
    this[i] = this[j]
    this[j] = temp
}