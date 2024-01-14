package com.waleska404.algorithms.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.waleska404.algorithms.R
import com.waleska404.algorithms.ui.core.components.CustomCard

@Composable
fun HomeScreen(
    navigateToBubbleSort: () -> Unit,
    navigateToQuickSort: () -> Unit,
    navigateToDijkstra: () -> Unit,
    navigateToLinearSearch: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
            .verticalScroll(state = rememberScrollState()),
    ) {
        Spacer(modifier = Modifier.height(15.dp))

        // title
        Text(
            text = stringResource(id = R.string.home_screen_title),
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(8.dp),
            lineHeight = 40.sp,
            textAlign = TextAlign.Center,
            maxLines = 2,
        )
        Spacer(modifier = Modifier.height(15.dp))

        // bubble sort
        AlgorithmListItem(
            title = R.string.bubble_sort_title,
            icon = R.drawable.sortdescending,
            iconDescription = R.string.sort_descending_icon,
            navigateToAlgorithm = navigateToBubbleSort
        )

        // quick sort
        AlgorithmListItem(
            title = R.string.quick_sort_title,
            icon = R.drawable.sortdescending,
            iconDescription = R.string.sort_descending_icon,
            navigateToAlgorithm = navigateToQuickSort
        )

        // Dijkstra's algorithm
        AlgorithmListItem(
            title = R.string.dijkstras_algorithm,
            icon = R.drawable.route,
            iconDescription = R.string.sort_descending_icon,
            navigateToAlgorithm = navigateToDijkstra
        )

        // linear search algorithm
        AlgorithmListItem(
            title = R.string.linear_search,
            icon = R.drawable.route,
            iconDescription = R.string.sort_descending_icon,
            navigateToAlgorithm = navigateToLinearSearch
        )
    }
}

@Composable
fun AlgorithmListItem(
    title: Int,
    icon: Int,
    iconDescription: Int,
    navigateToAlgorithm: () -> Unit
) {
    CustomCard(
        text = stringResource(id = title),
        iconResource = icon,
        iconDescriptionResource = iconDescription,
        modifier = Modifier.padding(8.dp),
        onClick = { navigateToAlgorithm() }
    )
}
