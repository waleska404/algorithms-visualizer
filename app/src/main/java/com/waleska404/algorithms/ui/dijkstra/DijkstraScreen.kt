package com.waleska404.algorithms.ui.dijkstra

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.waleska404.algorithms.R
import com.waleska404.algorithms.domain.dijkstra.Position
import com.waleska404.algorithms.domain.utils.toLinearGrid
import com.waleska404.algorithms.ui.core.components.CELL_FINISH
import com.waleska404.algorithms.ui.core.components.CELL_START
import com.waleska404.algorithms.ui.core.components.CELL_VISITED
import com.waleska404.algorithms.ui.core.components.CELL_WALL
import com.waleska404.algorithms.ui.core.components.CustomIconButton
import com.waleska404.algorithms.ui.core.components.PathFindingGrid
import kotlinx.coroutines.delay

//private val state = DijkstraViewModel(DijkstraImpl())
//private val scope = CoroutineScope(Dispatchers.Default)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DijkstraScreen(
    navigateToHome: () -> Unit,
    state: DijkstraViewModel = hiltViewModel(),
) {
    PathFindingApp(state)
}


@ExperimentalFoundationApi
@Composable
fun PathFindingApp(
    state: DijkstraViewModel,
) {
    val currentGridState = remember { mutableStateOf(state.drawCurrentGridState()) }

    val onCellClicked = { p: Position ->
        if (state.isPositionNotAtStartOrFinish(p) && !state.isVisualizing) {
            state.toggleCellTypeToWall(p)
            currentGridState.value = state.drawCurrentGridState()
        }
    }

    PathFindingUi(currentGridState.value, onCellClicked, state)
    LaunchedEffect(Unit) {
        while (true) {
            delay(GAME_DELAY_IN_MS)
            currentGridState.value = state.drawCurrentGridState()
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun PathFindingUi(cell: List<List<CellData>>, onClick: (Position) -> Unit, state: DijkstraViewModel) {
    val isVisualizeEnabled = remember { mutableStateOf(true) }
    val onVisualized: () -> Unit = {
        state.animatedShortestPath()
        isVisualizeEnabled.value = false
    }
    val onCleared: () -> Unit = {
        state.clear()
        isVisualizeEnabled.value = true
    }
    val onRandomizeWalls: () -> Unit = {
        state.randomizeWalls()
    }

    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PathFindingGrid(cell.toLinearGrid(), onClick)

        Column {
            Row {
                Legend("Start", CELL_START)
                Legend("Finish", CELL_FINISH)
                Legend("Visited", CELL_VISITED)
                Legend("Wall", CELL_WALL)
            }
            Row {
                VisualizeButton(
                    modifier = Modifier.padding(start = 16.dp),
                    onClick = onVisualized,
                    enabled = isVisualizeEnabled.value
                )
                RandomWallsButton(
                    modifier = Modifier.padding(start = 16.dp),
                    onClick = onRandomizeWalls,
                    enabled = isVisualizeEnabled.value
                )
                ClearButton(modifier = Modifier.padding(horizontal = 16.dp), onCleared)
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun Legend(
    label: String,
    color: Color,
    hasBorder: Boolean = false
) {
    val boxModifier = Modifier
        .border(BorderStroke(if (hasBorder) 0.5.dp else 0.dp, Color.Gray))
        .padding(4.dp)
        .height(if (hasBorder) 10.dp else 16.dp)
        .background(color)
        .width(if (hasBorder) 10.dp else 16.dp)

    Box(modifier = boxModifier)
    Text(text = label, color = Color.Black)
}

@ExperimentalFoundationApi
@Composable
fun VisualizeButton(modifier: Modifier = Modifier, onClick: () -> (Unit), enabled: Boolean = true) {
    CustomIconButton(
        modifier,
        onClick = onClick,
        text = "Vis",
        enabled = enabled,
        iconResource = R.drawable.sort,
        iconDescriptionResource = R.string.sort_icon,
    )
}

@ExperimentalFoundationApi
@Composable
fun ClearButton(modifier: Modifier = Modifier, onClick: () -> (Unit)) {
    CustomIconButton(
        modifier,
        onClick = onClick,
        text = "Clear",
        iconResource = R.drawable.sortdescending,
        iconDescriptionResource = R.string.sort_icon,
    )
}

@ExperimentalFoundationApi
@Composable
fun RandomWallsButton(
    modifier: Modifier = Modifier,
    onClick: () -> (Unit),
    enabled: Boolean = true
) {
    CustomIconButton(
        modifier,
        onClick = onClick,
        text = "Walls",
        enabled = enabled,
        iconResource = R.drawable.shuffle,
        iconDescriptionResource = R.string.random,
    )
}