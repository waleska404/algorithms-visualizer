package com.waleska404.algorithms.ui.dijkstra

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.waleska404.algorithms.domain.dijkstra.Position
import com.waleska404.algorithms.ui.core.theme.AccentContentDark

@Composable
fun Cell(cellData: CellData, onClick: (Position) -> Unit) {
    val bgColor = animateColorAsState(
        targetValue = getBackgroundByType(cellData),
        animationSpec = tween(durationMillis = 700), label = ""
    )

    val boxModifier = Modifier
        .padding(0.dp)
        .border(
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        )
        .height(40.dp)
        .background(bgColor.value)
        .fillMaxWidth()
        .clickable { onClick(cellData.position) }

    Box(modifier = boxModifier)
}

@Composable
private fun getBackgroundByType(cellData: CellData): Color {
    if (cellData.isShortestPath && cellData.type != CellType.START && cellData.type != CellType.FINISH) return CELL_PATH
    if (cellData.isVisited && cellData.type != CellType.START && cellData.type != CellType.FINISH) return CELL_VISITED

    return when (cellData.type) {
        CellType.BACKGROUND -> MaterialTheme.colorScheme.primary
        CellType.WALL -> MaterialTheme.colorScheme.secondary
        CellType.START -> CELL_START
        CellType.FINISH -> CELL_FINISH
    }
}

enum class CellType {
    START,
    FINISH,
    WALL,
    BACKGROUND,
}

val CELL_START = Color.Red
val CELL_FINISH = Color.Green
val CELL_VISITED = AccentContentDark
val CELL_PATH = Color.Yellow