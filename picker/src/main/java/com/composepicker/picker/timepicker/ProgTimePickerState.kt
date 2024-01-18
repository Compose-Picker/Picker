package com.composepicker.picker.timepicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Stable
class ProgTimePickerState(
    initialHour: Int,
    initialMinute: Int,
    val is24Hour: Boolean = false,
    val timeGap: TimeGap = TimeGap.ONE
) {
    init {
        require(initialHour in 0..23) { "initialHour should in [0..23] range" }
        require(initialMinute in 0..59) { "initialMinute should be in [0..59] range" }
        require(initialMinute % timeGap.interval != 0) { "initialMinute should be matched with timeGap interval."}
    }

    var hour by mutableStateOf(initialHour.toString())
    var minute by mutableStateOf(initialMinute.toString())
    var meridiem by mutableStateOf("AM")

    val hourList: List<String> =
        if (is24Hour) (0..23).map { it.toString() } else (1..12).map { it.toString() }
    val minuteList: List<String>
        get() = (0..59).filter { it.mod(timeGap.interval) == 0 }.map { it.toString() }

    val meridiemList: List<String> = listOf("AM", "PM")

    companion object {
        fun Saver(): Saver<ProgTimePickerState, *> = Saver(
            save = {
                listOf(
                    it.hour,
                    it.minute,
                    it.meridiem,
                    it.is24Hour,
                    it.timeGap
                )
            },
            restore = { value ->
                ProgTimePickerState(
                    initialHour = value[0] as Int,
                    initialMinute = value[1] as Int,
                    is24Hour = value[2] as Boolean,
                    timeGap = value[3] as TimeGap,
                )
            }
        )
    }
}


@Suppress("UNUSED")
@Composable
fun rememberProgTimePickerState(
    initialHour: Int = 0,
    initialMinute: Int = 0,
    is24Hour: Boolean = false,
    timeGap: TimeGap = TimeGap.ONE
): ProgTimePickerState = rememberSaveable(saver = ProgTimePickerState.Saver()) {
    ProgTimePickerState(
        initialHour,
        initialMinute,
        is24Hour,
        timeGap
    )
}