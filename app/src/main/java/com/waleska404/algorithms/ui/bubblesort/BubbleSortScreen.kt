package com.waleska404.algorithms.ui.bubblesort

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import com.waleska404.algorithms.ui.core.components.CustomSlider

@RequiresApi(Build.VERSION_CODES.O)
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
            .padding(15.dp),
    ) {
        Text(
            text = "Bubble Sort",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        SortingList(
            modifier = Modifier.weight(0.7f),
            listToSort = listToSort.list
        )
        Spacer(modifier = Modifier.height(10.dp))
        BottomButtons(
            startSorting = sortViewModel::startSorting,
            randomList = sortViewModel::randomList,
            sliderChange = sortViewModel::randomList
        )
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
    modifier: Modifier = Modifier,
    totalHeight: Dp
) {
    val borderStroke = if (item.isCurrentlyCompared) {
        BorderStroke(width = 3.dp, Color.Blue)
    } else {
        BorderStroke(width = 0.dp, Color.Transparent)
    }
    val itemHeight = (item.value * totalHeight.value / 100) - 40
    Column(
        modifier = modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(30.dp)
                .height(itemHeight.dp)
                .padding(6.dp)
                .background(item.color, RoundedCornerShape(15.dp))
                .border(borderStroke, RoundedCornerShape(15.dp)),
        )
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "${item.value}",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomButtons(
    startSorting: () -> Unit,
    randomList: () -> Unit,
    sliderChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO: disable buttons and slider if the list is ordering
    // TODO ADD ICONS TO BUTTONS
    var sliderValue by remember { mutableFloatStateOf(0f) }
    Column(
        modifier = modifier
    ) {
        // range slider
        CustomSlider(
            value = sliderValue,
            valueRange = 1f..30f,
            onValueChange = {
                sliderValue = it
                sliderChange(sliderValue.toInt())
            },
        )
        Row {
            // random button
            Button(
                modifier = Modifier
                    .height(40.dp)
                    .padding(horizontal = 10.dp)
                    .weight(1f),
                onClick = { randomList() },
            ) {
                Text(
                    "Random List",
                    fontSize = 18.sp
                )
            }
            // sort button
            Button(
                modifier = Modifier
                    .height(40.dp)
                    .padding(horizontal = 10.dp)
                    .weight(1f),
                onClick = { startSorting() },
            ) {
                Text(
                    "Sort List",
                    fontSize = 18.sp
                )
            }
        }
    }

}
