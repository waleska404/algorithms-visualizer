package com.waleska404.algorithms.ui.core.config

// DIJKSTRA CONFIG
const val NUMBER_OF_ROWS = 15
const val NUMBER_OF_COLUMNS = 9

const val START_POSITION_ROW = 1
const val START_POSITION_COLUMN = 4
const val FINISH_POSITION_ROW = 13
const val FINISH_POSITION_COLUMN = 4


// COMMON TO ALL ALGORITHMS CONFIG
var GAME_DELAY_IN_MS = 10.toLong()


fun setGameDelay(delay: Speed) {
    GAME_DELAY_IN_MS = when (delay) {
        Speed.NO_DELAY -> 0.toLong()
        Speed.FAST -> 3.toLong()
        Speed.AVERAGE -> 6.toLong()
        Speed.SLOW -> 10.toLong()
    }
}

enum class Speed {
    NO_DELAY,
    FAST,
    AVERAGE,
    SLOW,
}