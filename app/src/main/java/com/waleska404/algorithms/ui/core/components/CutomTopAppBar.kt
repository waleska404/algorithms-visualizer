package com.waleska404.algorithms.ui.core.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.waleska404.algorithms.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    navigateToHome: () -> Boolean,
    title: Int,
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.padding(7.dp).height(35.dp),
        title = {
            Text(
                text = stringResource(id = title),
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.secondary,
            )
        },
        navigationIcon = {
            Box(
                modifier = Modifier.padding(7.dp).size(30.dp).clickable {
                    navigateToHome()
                },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.leftarrow),
                    contentDescription = stringResource(id = R.string.down_arrow_icon),
                    tint = MaterialTheme.colorScheme.secondary,
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
        )
    )
}