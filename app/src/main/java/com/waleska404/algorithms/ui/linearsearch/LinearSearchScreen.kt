package com.waleska404.algorithms.ui.linearsearch

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.waleska404.algorithms.R
import com.waleska404.algorithms.ui.core.components.CustomIconButton
import com.waleska404.algorithms.ui.core.components.CustomSlider
import com.waleska404.algorithms.ui.core.components.CustomTopAppBar
import java.util.UUID
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LinearSearchScreen(
    linearSearchViewModel: LinearSearchViewModel = hiltViewModel(),
    navigateToHome: () -> Boolean,
) {
    val list: LinearSearchList by linearSearchViewModel.list.collectAsState()
    var searchItemVal by remember {
        mutableIntStateOf(Random.nextInt(1, 99))
    }
    val currentIndex = linearSearchViewModel.currentIndex.collectAsState()
    val itemFound = linearSearchViewModel.searchItemFound.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
    ) {
        CustomTopAppBar(navigateToHome = navigateToHome, title = R.string.linear_search)
        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            items(list.list, key = {
                UUID.randomUUID().toString()
            }) {
                LinearSearchItem(item = it)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "i = ${currentIndex.value}, current element = ${if (currentIndex.value == -1) -1 else list.list[currentIndex.value].value}, search key = $searchItemVal\n ${if (currentIndex.value > list.list.size - 1) "Element not found, press reset to start again." else if (!itemFound.value) "No match and continue to search for the next match." else "Item found at index ${currentIndex.value}"}",
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 15.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        BottomButtons(
            stepOver = { linearSearchViewModel.stepOver(searchItemVal) },
            sliderChange = linearSearchViewModel::randomizeCurrentList,
            searchItemChange = {
                searchItemVal = it
            },
            resetClick = linearSearchViewModel::randomizeCurrentList,
            listSizeInit = 10,
            isEnabled = !itemFound.value && currentIndex.value < list.list.size - 1,
            searchItem = searchItemVal
        )
    }
}

@Composable
fun LinearSearchItem(item: LinearSearchItem) {
    Column(modifier = Modifier.padding(horizontal = 5.dp)) {
        Box(
            modifier = Modifier.size(50.dp).clip(
                RoundedCornerShape(15.dp)
            ).background(MaterialTheme.colorScheme.onSecondary),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = item.value.toString(), fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun BottomButtons(
    stepOver: () -> Unit,
    sliderChange: (Int) -> Unit,
    searchItemChange: (Int) -> Unit,
    resetClick: () -> Unit,
    modifier: Modifier = Modifier,
    listSizeInit: Int,
    isEnabled: Boolean,
    searchItem: Int
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
                valueRange = 1f..10f,
                enabled = isEnabled,
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
        Row(verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = stringResource(id = R.string.search_key),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )

            OutlinedTextField(
                value = searchItem.toString(),
                onValueChange = {
                    if (it.length <= 2) searchItemChange.invoke(if (it.isNotEmpty()) it.toInt() else 0)
                },
                enabled = isEnabled,
                modifier = Modifier
                    .size(50.dp),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSecondary
                ),
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            CustomIconButton(
                modifier = Modifier
                    .weight(1f),
                text = stringResource(id = R.string.step),
                onClick = { stepOver() },
                iconResource = R.drawable.baseline_keyboard_arrow_right_24,
                iconDescriptionResource = R.string.step,
                enabled = isEnabled
            )

            CustomIconButton(
                modifier = Modifier
                    .weight(1f),
                text = stringResource(id = R.string.reset),
                onClick = resetClick,
                iconResource = R.drawable.shines,
                iconDescriptionResource = R.string.reset,
            )
        }
    }
}