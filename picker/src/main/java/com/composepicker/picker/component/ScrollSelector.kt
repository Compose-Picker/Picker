package com.composepicker.picker.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Suppress("UNUSED", "UNUSED_PARAMETER")
@Composable
fun ScrollableSelector(
    modifier: Modifier,
    valueList: List<String>,
    value: String,
    limit: Int = 3,
    suffix: @Composable (() -> Unit) = {},
    onValueChanged: (String) -> Unit,
    isYear: Boolean = false,
    is24Hour: Boolean = true,
    arrangement: Dp = 8.dp,
    highlightFontColor: Color = Color.Black,
    fontColor: Color = Color.DarkGray,
    highlightTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
) {

    require(limit % 2 != 0) { "limit Must be Odd." }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isYear || is24Hour.not()) {
            FiniteLazyColumn(
                modifier = modifier,
                valueList = valueList,
                value = value,
                limit = limit,
                onValueChanged = {})
        } else {
            InfiniteLazyColumn(
                modifier = modifier,
                valueList = valueList,
                value = value,
                limit = limit,
                onValueChanged = {})
        }
        suffix()
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FiniteLazyColumn(
    modifier: Modifier = Modifier,
    valueList: List<String>,
    value: String,
    limit: Int,
    arrangement: Dp = 8.dp,
    highlightFontColor: Color = Color.Black,
    fontColor: Color = Color.DarkGray,
    highlightTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
    onValueChanged: (String) -> Unit,
) {

    val yearValueList = valueList.toMutableList()

    val halfLimit = limit / 2

    repeat(halfLimit) {
        yearValueList.add(0, "0")
        yearValueList.add(yearValueList.lastIndex + 1, "0")
    }

    val unSelectLineHeight = (textStyle.lineHeight * (limit - 1))
    val lineHeight =
        rememberUpdatedState(newValue = unSelectLineHeight.value + highlightTextStyle.lineHeight.value + (arrangement.value * (halfLimit)))
    val listState =
        rememberLazyListState(initialFirstVisibleItemIndex = (yearValueList.indexOf(value) - halfLimit))
    val centerIndex = remember { mutableStateOf((listState.firstVisibleItemIndex + halfLimit)) }
    val centerItem = remember { mutableStateOf(yearValueList[centerIndex.value]) }
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    LazyColumn(
        state = listState,
        modifier = modifier.height(lineHeight.value.dp),
        verticalArrangement = Arrangement.spacedBy(arrangement),
        flingBehavior = flingBehavior,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(yearValueList.size) { index ->
            val item = yearValueList[index]

            Row {
                if (item != "0") {
                    if (item == centerItem.value) {
                        Text(text = "$item", style = highlightTextStyle, color = highlightFontColor)
                    } else {
                        Text(text = "$item", style = textStyle, color = fontColor)
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
            (it % yearValueList.size) + halfLimit
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
    arrangement: Dp = 8.dp,
    onValueChanged: (String) -> Unit,
    highlightFontColor: Color = Color.Black,
    fontColor: Color = Color.DarkGray,
    highlightTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
) {
    val unSelectLineHeight = (textStyle.lineHeight * (limit - 1))
    val lineHeight =
        rememberUpdatedState(newValue = unSelectLineHeight.value + highlightTextStyle.lineHeight.value + (arrangement.value * (limit - 2)))
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = (value.toInt() + valueList.size - (limit / 2) - 1) % valueList.size
    )
    val centerIndex =
        remember { mutableStateOf((listState.firstVisibleItemIndex + limit / 2) % valueList.size) }
    val centerItem = remember { mutableStateOf(valueList[centerIndex.value]) }
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    LazyColumn(
        state = listState,
        modifier = modifier.height(lineHeight.value.dp),
        flingBehavior = flingBehavior,
        verticalArrangement = Arrangement.spacedBy(arrangement),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(count = Int.MAX_VALUE) { index ->
            val idx = index % valueList.size
            val item = valueList[idx]

            if (item == centerItem.value) {
                Text(text = "$item", style = highlightTextStyle, color = highlightFontColor)
            } else {
                Text(text = "$item", style = textStyle, color = fontColor)
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

//@Composable
//private fun pixelsToDp(pixels: Int) = with(LocalDensity.current) { pixels.toDp() }