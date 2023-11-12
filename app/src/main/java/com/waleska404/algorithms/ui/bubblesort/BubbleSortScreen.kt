package com.waleska404.algorithms.ui.bubblesort

import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BubbleSortScreen(
    sortViewModel: BubbleSortViewModel = hiltViewModel(),
) {

    val listToSort: BubbleSortList by sortViewModel.listToSort.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
    ) {
        Text(text = "Bubble Sort", fontWeight = FontWeight.Bold, fontSize = 30.sp)
        Spacer(modifier = Modifier.weight(0.05f))
        SortingList(
            modifier = Modifier.weight(0.7f),
            listToSort = listToSort.list
        )
        Spacer(modifier = Modifier.weight(0.05f))
        BottomButtons( startSorting = sortViewModel::startSorting )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SortingList(
    listToSort: List<BubbleSortItem>,
    modifier: Modifier
) {
    val localDensity = LocalDensity.current
    var heightIs by remember {
        mutableStateOf(0.dp)
    }
    LazyRow(
        modifier = modifier
            .background(Color.White)
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                heightIs = with(localDensity) { coordinates.size.height.toDp() }
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        items(
            listToSort,
            key = {
                it.id
            }
        ) {
            BubbleSortItem(
                item = it,
                modifier = Modifier.animateItemPlacement(
                    tween(300)
                ),
                totalHeight = heightIs
            )
        }
    }
}

@Composable
fun BubbleSortItem(
    item: BubbleSortItem,
    modifier: Modifier,
    totalHeight: Dp
) {
    val borderStroke = if (item.isCurrentlyCompared) {
        BorderStroke(width = 3.dp, Color.Blue)
    } else {
        BorderStroke(width = 0.dp, Color.Transparent)
    }
    val itemHeight = item.value * totalHeight.value / 100
    Box(
        modifier = modifier
            .width(30.dp)
            .height(itemHeight.dp)
            .padding(6.dp)
            .background(item.color, RoundedCornerShape(15.dp))
            .border(borderStroke, RoundedCornerShape(15.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "${item.value}",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
    }
}


@Composable
fun BottomButtons(
    startSorting: () -> Unit
) {
    // TODO ADD ICONS TO BUTTONS
    Column {
        // range value
        Row {
            // random button
            Button(
                modifier = Modifier
                    .padding(6.dp)
                    .weight(1f),
                onClick = { },
            ) {
                Text(
                    "Random List",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            }

            // sort button
            Button(
                modifier = Modifier
                    .padding(6.dp)
                    .weight(1f),
                onClick = { startSorting() },
            ) {
                Text(
                    "Sort List",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            }
        }
    }

}