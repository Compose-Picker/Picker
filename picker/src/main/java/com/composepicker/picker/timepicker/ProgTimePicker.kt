package com.composepicker.picker.timepicker

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.composepicker.picker.component.ScrollableSelector

@Suppress("UNUSED")
@Composable
fun ProgTimePicker(
    modifier: Modifier = Modifier,
    timePickerState: ProgTimePickerState,
    hourSuffix: @Composable () -> Unit = {},
    minuteSuffix: @Composable () -> Unit = {},
    onTimeChanged: (hour: String, minute: String, meridiem: String) -> Unit
) {

    Row(modifier = modifier) {
        ScrollableSelector(
            modifier = modifier,
            valueList = timePickerState.hourList,
            value = timePickerState.hour,
            suffix = hourSuffix,
            onValueChanged = { onTimeChanged(it, timePickerState.minute, timePickerState.meridiem) })

        ScrollableSelector(
            modifier = modifier,
            valueList = timePickerState.minuteList,
            value = timePickerState.minute,
            suffix = minuteSuffix,
            onValueChanged = { onTimeChanged(timePickerState.hour, it, timePickerState.meridiem) })

        if (timePickerState.is24Hour.not()) {
            ScrollableSelector(
                modifier = modifier,
                valueList = timePickerState.meridiemList,
                value = timePickerState.meridiem,
                suffix = minuteSuffix,
                onValueChanged = { onTimeChanged(timePickerState.hour, timePickerState.minute, it) },
                is24Hour = false
            )
        }
    }
}

@Preview
@Composable
fun PreviewProgTimePicker() {
    ProgTimePicker(
        timePickerState = ProgTimePickerState(1, 1, is24Hour = true, timeGap = TimeGap.FIVE),
        onTimeChanged = { a, b, c ->  }
    )
}