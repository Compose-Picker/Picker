package com.composepicker.picker.timepicker

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composepicker.picker.component.ScrollableSelector

@Suppress("UNUSED")
@Composable
fun ProgTimePicker(
    modifier: Modifier = Modifier,
    timePickerState: ProgTimePickerState,
    hourSuffix: @Composable () -> Unit = {},
    minuteSuffix: @Composable () -> Unit = {},
    onTimeChanged: (hour: String, minute: String, meridiem: String) -> Unit,
    arrangement: Dp = 8.dp,
    highlightFontColor: Color = Color.Black,
    fontColor: Color = Color.DarkGray,
    highlightTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall
) {

    Row(modifier = modifier) {
        ScrollableSelector(
            modifier = modifier,
            valueList = timePickerState.hourList,
            value = timePickerState.hour,
            suffix = hourSuffix,
            onValueChanged = {
                onTimeChanged(
                    it,
                    timePickerState.minute,
                    timePickerState.meridiem
                )
            })

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
                onValueChanged = {
                    onTimeChanged(
                        timePickerState.hour,
                        timePickerState.minute,
                        it
                    )
                },
                is24Hour = false
            )
        }
    }
}

@Preview
@Composable
fun PreviewProgTimePicker() {
    Surface(
        modifier = Modifier.padding(12.dp),
        color = Color.Gray,
        shape = RoundedCornerShape(1)
    ) {
        ProgTimePicker(
            timePickerState = ProgTimePickerState(1, 20, is24Hour = false, timeGap = TimeGap.FIVE),
            onTimeChanged = { hour, minute, meridiem -> }
        )
    }
}