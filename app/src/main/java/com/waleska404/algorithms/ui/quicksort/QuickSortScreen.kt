package com.waleska404.algorithms.ui.quicksort

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import com.waleska404.algorithms.ui.core.components.CustomTopAppBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun QuickSortScreen(
    sortViewModel: QuickSortViewModel = hiltViewModel(),
    navigateToHome: () -> Boolean,
) {
    val listToSort: QuickSortList by sortViewModel.listToSort.collectAsState()
    val isSorting: Boolean by sortViewModel.isSorting.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
    ) {
        CustomTopAppBar(
            navigateToHome = navigateToHome,
            title = R.string.quick_sort_title,
        )
        Spacer(modifier = Modifier.height(10.dp))
        SortingList(
            modifier = Modifier.weight(0.7f),
            listToSort = listToSort.list,
            isSorting = isSorting,
        )
        Spacer(modifier = Modifier.height(10.dp))
        BottomButtons(
            startSorting = sortViewModel::startSorting,
            randomList = sortViewModel::randomizeCurrentList,
            sliderChange = sortViewModel::randomizeCurrentList,
            listSizeInit = listToSort.list.size,
            isSorting = isSorting,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SortingList(
    listToSort: List<QuickSortItem>,
    modifier: Modifier,
    isSorting: Boolean,
) {
    val localDensity = LocalDensity.current
    var heightIs by remember {
        mutableStateOf(0.dp)
    }
    LazyRow(
        modifier = modifier
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
            QuickSortItem(
                item = it,
                modifier = Modifier.animateItemPlacement(
                    animationSpec = tween()
                ),
                totalHeight = heightIs,
                isSorting = isSorting
            )
        }
    }
}

@Composable
fun QuickSortItem(
    item: QuickSortItem,
    modifier: Modifier = Modifier,
    totalHeight: Dp,
    isSorting: Boolean,
) {
    val hasStroke = item.isPivot || item.isLeftPointer || item.isRightPointer
    val colorStroke = if (item.isPivot) {
        MaterialTheme.colorScheme.outlineVariant
    } else {
        MaterialTheme.colorScheme.outline
    }
    val borderStroke = if (hasStroke) {
        BorderStroke(width = 3.dp, colorStroke)
    } else {
        BorderStroke(width = 0.dp, Color.Transparent)
    }
    val itemHeight = (item.value * totalHeight.value / 100) - 40 - 5 - 30 - 30
    val itemColor = if (item.alreadyOrdered) {
        MaterialTheme.colorScheme.surface
    } else {
        MaterialTheme.colorScheme.onSecondary
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Pointers(isLeftPointer = item.isLeftPointer, isRightPointer = item.isRightPointer, isPivot = item.isPivot)
        Box(
            modifier = Modifier
                .height(itemHeight.dp)
                .width(30.dp)
                .background(itemColor, RoundedCornerShape(15.dp))
                .border(borderStroke, RoundedCornerShape(15.dp))
        )
        ItemTextValue(value = item.value)
        if (item.inSortingRange && isSorting) {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(5.dp)
                    .background(MaterialTheme.colorScheme.onSecondary),
            )
        }
    }
}

@Composable
private fun ItemTextValue(
    value: Int
) {
    Box(
        modifier = Modifier
            .width(40.dp)
            .height(30.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$value",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.secondary,
        )
    }
}

@Composable
private fun Pointers(
    isLeftPointer: Boolean,
    isRightPointer: Boolean,
    isPivot: Boolean,
) {
    if (isLeftPointer) {
        Box(
            modifier = Modifier
                .size(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "L",
                color = MaterialTheme.colorScheme.outline,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }
    }
    if (isRightPointer) {
        Box(
            modifier = Modifier
                .size(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "R",
                color = MaterialTheme.colorScheme.outline,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }
    }
    if (isPivot) {
        Box(
            modifier = Modifier
                .size(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "P",
                color = MaterialTheme.colorScheme.outlineVariant,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun BottomButtons(
    startSorting: () -> Unit,
    randomList: () -> Unit,
    sliderChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    listSizeInit: Int,
    isSorting: Boolean,
) {
    var sliderValue by remember { mutableFloatStateOf(listSizeInit.toFloat()) }
    Column(
        modifier = modifier.padding(15.dp)
    ) {
        // range slider
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.list_size),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
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
                color = MaterialTheme.colorScheme.secondary,
                disabledColor = MaterialTheme.colorScheme.surface,
                textThumbColor = MaterialTheme.colorScheme.primary
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
                enabled = !isSorting,
                containerColor = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}