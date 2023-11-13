package com.waleska404.algorithms.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.waleska404.algorithms.R
import com.waleska404.algorithms.ui.core.components.CustomCard

@Composable
fun HomeScreen() {
     Column(
         modifier = Modifier
             .fillMaxSize()
             .padding(horizontal = 15.dp)
             .verticalScroll(state = rememberScrollState()),
     ) {
         Spacer(modifier = Modifier.height(15.dp))
         BubbleSort()
     }
}

@Composable
fun BubbleSort() {
    CustomCard(
        text = "Bubble Sort",
        iconResource = R.drawable.sortdescending,
        iconDescriptionResource = R.string.sort_descending_icon,
        modifier = Modifier.padding(8.dp)
    )
}
