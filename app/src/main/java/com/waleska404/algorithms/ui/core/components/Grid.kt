package com.waleska404.algorithms.ui.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.waleska404.algorithms.domain.dijkstra.Position
import com.waleska404.algorithms.ui.dijkstra.CellData
import com.waleska404.algorithms.ui.dijkstra.NUMBER_OF_COLUMNS

@ExperimentalFoundationApi
@Composable
fun PathFindingGrid(
    cellData: List<CellData>,
    onClick: (Position) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(NUMBER_OF_COLUMNS),
        modifier = Modifier
            .padding(4.dp)
            .border(BorderStroke(6.dp, Color.Black))
    ) {
        items(cellData) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Cell(it, onClick)
            }
        }
    }
}