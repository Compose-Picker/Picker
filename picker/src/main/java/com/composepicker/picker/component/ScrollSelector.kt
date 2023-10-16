package com.composepicker.picker.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Suppress("UNUSED", "UNUSED_PARAMETER")
@Composable
fun ScrollableSelector(
    modifier: Modifier,
    valueList: List<Int>,
    value: Int,
    suffix: @Composable (() -> Unit) = {},
    onValueChanged: (Int) -> Unit
) {
    /*
    TODO : Use This Selector at TimePicker & DatePicker. This Item must be scrolled with 3 text values with suffix on center.
     */
    Column {
    }
}

