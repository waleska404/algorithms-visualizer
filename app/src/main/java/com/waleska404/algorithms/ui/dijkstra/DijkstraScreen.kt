package com.waleska404.algorithms.ui.dijkstra

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.waleska404.algorithms.R
import com.waleska404.algorithms.ui.core.components.CustomIconButton


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DijkstraScreen(
    navigateToHome: () -> Unit,
    viewModel: DijkstraViewModel = hiltViewModel(),
) {
    val currentGridState: DijkstraGrid by viewModel.gridState.collectAsState()
    val isVisualizing: Boolean by viewModel.isVisualizing.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
    ) {
        Text(
            text = stringResource(id = R.string.dijkstras_algorithm),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.weight(1f))
        PathFindingGrid(
            cellData = currentGridState.toLinearGrid(),
            onClick = viewModel::onCellClicked
        )
        Legend()
        Spacer(modifier = Modifier.weight(1f))
        BottomButtons(
            onVisualize = { viewModel.animatedShortestPath() },
            onRandomizeWalls = { viewModel.randomizeWalls() },
            onClear = { viewModel.clear() },
            isVisualizing = isVisualizing
        )
        Spacer(modifier = Modifier.weight(0.5f))
    }
}


@Composable
fun BottomButtons(
    onVisualize: () -> Unit,
    onRandomizeWalls: () -> Unit,
    onClear: () -> Unit,
    isVisualizing: Boolean,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        CustomIconButton(
            modifier = Modifier.padding(start = 7.dp),
            onClick = onVisualize,
            text = stringResource(id = R.string.run),
            enabled = !isVisualizing,
            iconResource = R.drawable.sort,
            iconDescriptionResource = R.string.sort_icon,
        )
        CustomIconButton(
            modifier = Modifier.padding(start = 7.dp),
            onClick = onRandomizeWalls,
            text = stringResource(id = R.string.walls),
            enabled = !isVisualizing,
            iconResource = R.drawable.shuffle,
            iconDescriptionResource = R.string.random,
        )
        CustomIconButton(
            modifier = Modifier.padding(horizontal = 7.dp),
            onClick = onClear,
            text = stringResource(id = R.string.clear),
            iconResource = R.drawable.broom,
            iconDescriptionResource = R.string.broom_icon,
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Legend() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        LegendItem(stringResource(id = R.string.start), CELL_START)
        LegendItem(stringResource(id = R.string.finish), CELL_FINISH)
        LegendItem(stringResource(id = R.string.visited), CELL_VISITED)
        LegendItem(stringResource(id = R.string.wall), MaterialTheme.colorScheme.secondary)
    }
}


@ExperimentalFoundationApi
@Composable
fun LegendItem(
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
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(modifier = boxModifier)
        Text(text = label, color = MaterialTheme.colorScheme.secondary)
    }

}