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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.waleska404.algorithms.R
import com.waleska404.algorithms.domain.dijkstra.Position

@Composable
fun Cell(cellData: CellData, onClick: (Position) -> Unit) {
    val bgColor = animateColorAsState(
        targetValue = getBackgroundByType(cellData),
        animationSpec = tween(durationMillis = 700), label = ""
    )

    val boxModifier = Modifier
        .border(
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        )
        .height(40.dp)
        .background(bgColor.value)
        .fillMaxWidth()
        .clickable { onClick(cellData.position) }

    Box(modifier = boxModifier, contentAlignment = Alignment.Center) {
        if(cellData.type == CellType.START) {
            Icon(
                modifier = Modifier.padding(4.dp),
                painter = painterResource(id = R.drawable.downarrow),
                contentDescription = stringResource(id = R.string.target_icon),
                tint = MaterialTheme.colorScheme.secondary,
            )
        }
        else if(cellData.type == CellType.FINISH) {
            Icon(
                modifier = Modifier.padding(4.dp),
                painter = painterResource(id = R.drawable.target),
                contentDescription = stringResource(id = R.string.target_icon),
                tint = MaterialTheme.colorScheme.secondary,
            )
        }
    }
}

@Composable
private fun getBackgroundByType(cellData: CellData): Color {
    if (cellData.isShortestPath && cellData.type != CellType.START && cellData.type != CellType.FINISH) return MaterialTheme.colorScheme.inverseSurface
    if (cellData.isVisited && cellData.type != CellType.START && cellData.type != CellType.FINISH) return MaterialTheme.colorScheme.onSecondary

    return when (cellData.type) {
        CellType.BACKGROUND -> MaterialTheme.colorScheme.primary
        CellType.WALL -> MaterialTheme.colorScheme.secondary
        CellType.START -> MaterialTheme.colorScheme.primary
        CellType.FINISH -> MaterialTheme.colorScheme.primary
    }
}

enum class CellType {
    START,
    FINISH,
    WALL,
    BACKGROUND,
}