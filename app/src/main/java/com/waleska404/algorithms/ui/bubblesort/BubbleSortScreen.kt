package com.waleska404.algorithms.ui.bubblesort

import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BubbleSortScreen(
    sortViewModel: BubbleSortViewModel = hiltViewModel(),
) {

    val listToSort: BubbleSortList by sortViewModel.listToSort.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Button(
            onClick = { sortViewModel.startSorting() },
        ) {
            Text(
                "Sort List",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }
        SortingList(listToSort = listToSort.list)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SortingList(listToSort: List<BubbleSortItem>) {
    LazyRow(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ){
        items(
            listToSort,
            key = {
                it.id
            }
        ){
            val borderStroke = if(it.isCurrentlyCompared){
                BorderStroke(width = 3.dp, Color.White,)
            }else{
                BorderStroke(width = 0.dp, Color.Transparent)
            }
            Box(
                modifier = Modifier
                    .width(15.dp)
                    .height(it.value.dp)
                    .background(it.color, RoundedCornerShape(15.dp))
                    .border(borderStroke, RoundedCornerShape(15.dp))
                    .animateItemPlacement(
                        tween(300)
                    ),
                contentAlignment = Alignment.Center
            ){
                Text(
                    "${it.value}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            }
        }
    }
}
