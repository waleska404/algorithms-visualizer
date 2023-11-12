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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.waleska404.algorithms.R
import com.waleska404.algorithms.ui.core.components.CustomIconButton
import com.waleska404.algorithms.ui.core.components.CustomSlider

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BubbleSortScreen(
    sortViewModel: BubbleSortViewModel = hiltViewModel(),
) {

    val listToSort: BubbleSortList by sortViewModel.listToSort.collectAsState()
    val isSorting: Boolean by sortViewModel.isSorting.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(15.dp),
    ) {
        Text(
            text = stringResource(id = R.string.bubble_sort_title),
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
            sliderChange = sortViewModel::randomList,
            listSizeInit = listToSort.list.size,
            isSorting = isSorting
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
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(30.dp)
                .height(itemHeight.dp)
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
    modifier: Modifier = Modifier,
    listSizeInit: Int,
    isSorting: Boolean
) {
    var sliderValue by remember { mutableFloatStateOf(listSizeInit.toFloat()) }
    Column(
        modifier = modifier
    ) {
        // range slider
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.list_size),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            CustomSlider(
                modifier = Modifier.weight(1f),
                value = sliderValue,
                valueRange = 1f..30f,
                enabled = !isSorting,
                onValueChange = {
                    val newValue = it.toInt()
                    if (newValue != sliderValue.toInt()) {
                        sliderValue = newValue.toFloat()
                        sliderChange(sliderValue.toInt())
                    }
                },
                color = Color.Black,
                disabledColor = Color.LightGray
            )
        }
        Row {
            // random button
            CustomIconButton(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.random),
                onClick = { randomList() },
                iconResource = R.drawable.shuffle,
                iconDescriptionResource = R.string.sort_icon,
                iconTint = Color.White,
                enabled = !isSorting
            )
            Spacer(modifier = Modifier.width(20.dp))
            // sort button
            CustomIconButton(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.sort),
                onClick = { startSorting() },
                iconResource = R.drawable.sort,
                iconDescriptionResource = R.string.sort_icon,
                iconTint = Color.White,
                enabled = !isSorting
            )
        }
    }
}
