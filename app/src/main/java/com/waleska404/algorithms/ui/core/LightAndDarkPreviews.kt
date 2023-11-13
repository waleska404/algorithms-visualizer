package com.waleska404.algorithms.ui.core

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "LightMode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    group = "LightMode"
)
@Preview(
    name = "DarkMode",
    uiMode = Configuration.UI_MODE_NIGHT_MASK and Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF0B0B0C,
    group = "DarkMode"
)
annotation class LightAndDarkPreviews