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
    onTimeChanged: (hour: Int, minute: Int) -> Unit
) {

    Row(modifier = modifier) {
        ScrollableSelector(
            modifier = modifier,
            valueList = timePickerState.hourList,
            value = timePickerState.hour,
            suffix = hourSuffix,
            onValueChanged = {
                onTimeChanged(it, timePickerState.minute)
            })

        ScrollableSelector(
            modifier = modifier,
            valueList = timePickerState.minuteList,
            value = timePickerState.minute,
            suffix = minuteSuffix,
            onValueChanged = {
                onTimeChanged(timePickerState.hour, it)
            })

        if (timePickerState.is24Hour.not()) {
            // TODO : Add AM/PM SELECTOR
            return // TODO : DELETE.
        }
    }
}

@Preview
@Composable
fun PreviewProgTimePicker() {
    ProgTimePicker(
        timePickerState = ProgTimePickerState(1,1,true, TimeGap.FIVE),
        onTimeChanged = { a,b ->  },
    )
}