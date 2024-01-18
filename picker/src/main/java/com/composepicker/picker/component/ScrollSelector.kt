package com.composepicker.picker.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import com.composepicker.picker.common.PickerCommonConfiguration
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Suppress("UNUSED", "UNUSED_PARAMETER")
@Composable
fun ScrollableSelector(
    modifier: Modifier,
    valueList: List<String>,
    value: String,
    suffix: @Composable (() -> Unit) = {},
    onValueChanged: (String) -> Unit,
    configuration: PickerCommonConfiguration = PickerCommonConfiguration.Builder().build(),
    isYear: Boolean = false,
    is24Hour: Boolean = true
) {/*
    TODO : Use This Selector at TimePicker & DatePicker. This Item must be scrolled with 3 text values with suffix on center.
     */
    if (isYear || !is24Hour) {
        FiniteLazyColumn(valueList = valueList, value = value, limit = 5, onValueChanged = { onValueChanged(it) })
    } else {
        InfiniteLazyColumn(valueList = valueList, value = value, limit = 5, onValueChanged = { onValueChanged(it) })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FiniteLazyColumn(
    modifier: Modifier = Modifier,
    valueList: List<String>,
    value: String,
    limit: Int,
    suffix: @Composable (() -> Unit) = {},
    onValueChanged: (String) -> Unit,
    configuration: PickerCommonConfiguration = PickerCommonConfiguration.Builder().build(),
) {
    val yearValueList = valueList.toMutableList()

    yearValueList.add(yearValueList.lastIndex + 1, "")
    yearValueList.add(yearValueList.lastIndex + 1, "")
    yearValueList.add(0, "")
    yearValueList.add(0, "")

    val itemHeightPixels = remember { mutableStateOf(0) }
    val listState =
        rememberLazyListState(initialFirstVisibleItemIndex = (yearValueList.indexOf(value) - 2))
    val centerIndex = remember { mutableStateOf((listState.firstVisibleItemIndex + 2)) }
    val centerItem = remember { mutableStateOf(yearValueList[centerIndex.value]) }
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    LazyColumn(
        state = listState,
        modifier = modifier.height(pixelsToDp(pixels = itemHeightPixels.value * limit)),
        flingBehavior = flingBehavior
    ) {
        items(yearValueList.size) { index ->
            val item = yearValueList[index]

            Row(modifier = Modifier.onSizeChanged { size ->
                itemHeightPixels.value = size.height
            }) {
                if (item != "0") {
                    if (index == centerIndex.value) {
                        Text(text = "$item")
                        suffix()
                    } else {
                        Text(text = "$item")
                    }
                } else {
                    Text(text = "")
                }
            }
        }
    }

    // call center value
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }.distinctUntilChanged().map {
            (it % yearValueList.size) + limit / 2
        }.collectLatest { index ->
            centerIndex.value = index % yearValueList.size
            centerItem.value = yearValueList[centerIndex.value]
            onValueChanged(centerItem.value)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun InfiniteLazyColumn(
    modifier: Modifier = Modifier,
    valueList: List<String>,
    value: String,
    limit: Int,
    suffix: @Composable (() -> Unit) = {},
    onValueChanged: (String) -> Unit,
    configuration: PickerCommonConfiguration = PickerCommonConfiguration.Builder().build(),
) {
    val itemHeightPixels = remember { mutableStateOf(0) }
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = (value.toInt() + valueList.size - (limit / 2) - 1) % valueList.size
    )
    val centerIndex =
        remember { mutableStateOf((listState.firstVisibleItemIndex + limit / 2) % valueList.size) }
    val centerItem = remember { mutableStateOf(valueList[centerIndex.value]) }
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    LazyColumn(
        state = listState,
        modifier = modifier.height(pixelsToDp(pixels = itemHeightPixels.value * limit)),
        flingBehavior = flingBehavior
    ) {
        items(count = Int.MAX_VALUE) { index ->
            val idx = index % valueList.size
            val item = valueList[idx]

            if (idx == centerIndex.value) {
                Row(modifier = Modifier.onSizeChanged { size ->
                    itemHeightPixels.value = size.height
                }) {
                    Text(text = "$item")
                    suffix()
                }

            } else {
                Row(modifier = Modifier.onSizeChanged { size ->
                    itemHeightPixels.value = size.height
                }) {
                    Text(text = "$item")
                }
            }
        }
    }

    // 가운데 값 호출
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }.distinctUntilChanged().map {
            (it % valueList.size) + limit / 2
        }.collectLatest { index ->
            centerIndex.value = index % valueList.size
            centerItem.value = valueList[centerIndex.value]
            onValueChanged(centerItem.value)
        }
    }
}

@Composable
private fun pixelsToDp(pixels: Int) = with(LocalDensity.current) { pixels.toDp() }

