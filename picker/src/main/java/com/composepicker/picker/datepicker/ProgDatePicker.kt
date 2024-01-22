package com.composepicker.picker.datepicker

import android.text.format.DateFormat
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    onDateChanged: (instant: Instant) -> Unit,
    arrangement: Dp = 8.dp,
    highlightFontColor: Color = Color.Black,
    fontColor: Color = Color.DarkGray,
    highlightTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall
) {
    // TODO : Change Composable When DateFormat Change.
    Row(modifier = modifier) {
        ScrollableSelector(
            modifier = Modifier,
            valueList = progDatePickerState.yearList,
            value = progDatePickerState.year,
            onValueChanged = {
                onDateChanged(Instant.parse("$it.${progDatePickerState.month}.${progDatePickerState.day}"))
            },
            suffix = yearSuffix,
            isYear = true,
        )
        ScrollableSelector(
            modifier = Modifier,
            valueList = progDatePickerState.monthList,
            value = progDatePickerState.month,
            suffix = monthSuffix,
            onValueChanged = {
                onDateChanged(Instant.parse("${progDatePickerState.year}.$it.${progDatePickerState.day}"))
            }
        )
        ScrollableSelector(
            modifier = Modifier,
            valueList = progDatePickerState.dayList,
            value = progDatePickerState.day,
            suffix = daySuffix,
            onValueChanged = {
                onDateChanged(Instant.parse("${progDatePickerState.year}.${progDatePickerState.month}.$it"))
            }
        )
    }
}

@Preview
@Composable
fun PreviewProgDatePicker() {
    Surface(
        modifier = Modifier.padding(12.dp),
        color = Color.Gray,
        shape = RoundedCornerShape(1)
    ) {
        ProgDatePicker(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            progDatePickerState = rememberProgDatePickerState(Instant.parse("2018-01-28T18:35:24.00Z")),
            dateFormat = DateFormat(),
            onDateChanged = {},
            yearSuffix = {
                Text(text = "년")
            },
            monthSuffix = {
                Text(text = "월")
            },
            daySuffix = {
                Text(text = "일")
            }
        )
    }

}