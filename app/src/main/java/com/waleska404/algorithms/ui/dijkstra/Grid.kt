package com.waleska404.algorithms.ui.dijkstra

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.waleska404.algorithms.domain.dijkstra.Position
import com.waleska404.algorithms.ui.core.config.NUMBER_OF_COLUMNS

@ExperimentalFoundationApi
@Composable
fun PathFindingGrid(
    cellData: List<CellData>,
    onClick: (Position) -> Unit,
    modifier: Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(NUMBER_OF_COLUMNS),
        modifier = modifier
            .border(border = BorderStroke(3.dp, MaterialTheme.colorScheme.secondary))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        items(cellData) {
            Cell(
                cellData = it,
                onClick = onClick,
            )
        }
    }
}