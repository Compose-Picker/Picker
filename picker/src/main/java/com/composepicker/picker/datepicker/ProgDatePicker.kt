package com.composepicker.picker.datepicker

import android.text.format.DateFormat
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.composepicker.picker.component.ScrollableSelector
import kotlinx.datetime.Instant

@Suppress("UNUSED", "UNUSED_PARAMETER")
@Composable
fun ProgDatePicker(
    modifier: Modifier = Modifier,
    progDatePickerState: ProgDatePickerState,
    dateFormat: DateFormat,
    yearSuffix: @Composable () -> Unit = {},
    monthSuffix: @Composable () -> Unit = {},
    daySuffix: @Composable () -> Unit = {},
    onDateChanged: (instant: Instant) -> Unit
) {
    // TODO : Change Composable When DateFormat Change.
    Row(modifier = modifier) {
        ScrollableSelector(
            modifier = modifier,
            valueList = progDatePickerState.yearList,
            value = progDatePickerState.year,
            onValueChanged = {
                onDateChanged(Instant.parse("$it.${progDatePickerState.month}.${progDatePickerState.day}"))
            },
            isYear = true,
        )
        ScrollableSelector(
            modifier = modifier,
            valueList = progDatePickerState.monthList,
            value = progDatePickerState.month,
            onValueChanged = {
                onDateChanged(Instant.parse("$it.${progDatePickerState.month}.${progDatePickerState.day}"))
            }
        )
        ScrollableSelector(
            modifier = modifier,
            valueList = progDatePickerState.dayList,
            value = progDatePickerState.day,
            onValueChanged = {
                onDateChanged(Instant.parse("$it.${progDatePickerState.month}.${progDatePickerState.day}"))
            }
        )
    }
}

@Preview
@Composable
fun PreviewProgDatePicker() {
    ProgDatePicker(
        modifier = Modifier.fillMaxSize(),
        progDatePickerState = rememberProgDatePickerState(Instant.parse("2018-01-28T18:35:24.00Z")),
        dateFormat = DateFormat(),
        onDateChanged = {}
    )
}