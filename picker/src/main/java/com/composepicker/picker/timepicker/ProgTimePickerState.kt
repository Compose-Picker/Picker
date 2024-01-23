package com.composepicker.picker.timepicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Stable
class ProgTimePickerState(
    initialHour: Int,
    initialMinute: Int,
    initialMeridiem: String = "AM",
    val is24Hour: Boolean = false,
    val timeGap: TimeGap = TimeGap.ONE
) {
    init {
        require(initialHour in 0..23) { "initialHour should in [0..23] range" }
        require(initialMinute in 0..59) { "initialMinute should be in [0..59] range" }
        require(initialMinute % timeGap.interval == 0) { "initialMinute should be matched with timeGap interval." }
    }

    var hour by mutableStateOf(initialHour.toString())
    var minute by mutableStateOf(initialMinute.toString())
    var meridiem by mutableStateOf(initialMeridiem)

    val hourList: List<String> =
        if (is24Hour) (0..23).map { it.toString() } else (1..12).map { it.toString() }
    @OptIn(ExperimentalStdlibApi::class)
    val minuteList: List<String>
        get() = (0..<60).filter { it.mod(timeGap.interval) == 0 }.map { it.toString() }

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
                    initialMeridiem = value[2] as String,
                    is24Hour = value[3] as Boolean,
                    timeGap = value[4] as TimeGap,
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
    initialMeridiem: String = "AM",
    is24Hour: Boolean = false,
    timeGap: TimeGap = TimeGap.ONE
): ProgTimePickerState = rememberSaveable(saver = ProgTimePickerState.Saver()) {
    ProgTimePickerState(
        initialHour,
        initialMinute,
        initialMeridiem,
        is24Hour,
        timeGap
    )
}