package com.waleska404.algorithms.ui.dijkstra

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.waleska404.algorithms.domain.dijkstra.Position

@ExperimentalFoundationApi
@Composable
fun PathFindingGrid(
    cellData: List<CellData>,
    onClick: (Position) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(NUMBER_OF_COLUMNS),
        modifier = Modifier
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.primary)
            .border(
                border = BorderStroke(3.dp, MaterialTheme.colorScheme.secondary),
            )
    ) {
        items(cellData) {
            Cell(
                cellData = it,
                onClick = onClick,
            )
        }
    }
}