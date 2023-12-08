package com.composepicker.picker.datepicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Stable
class ProgDatePickerState(
    initialDate: Instant,
    val maxDate: Instant,
    val minDate: Instant
) {
    init {
        require(initialDate < maxDate) { "initialDate should be smaller than maxDate"}
        require(initialDate > minDate) { "initialDate should be bigger than minDate"}
        require(minDate < maxDate) { "minDate should be smaller than maxDate"}
        require(maxDate <= Instant.parse("2999-12-31T23:59:59.00Z")) { "maxDate cannot be bigger than 2999.12.31" }
        require(minDate >= Instant.parse("1000-01-01T00:00:00.00Z")) { "minDate cannot be smaller than 1000.01.01" }
    }

    var year by mutableIntStateOf(initialDate.toLocalDateTime(TimeZone.currentSystemDefault()).year)
    var month by mutableIntStateOf(initialDate.toLocalDateTime(TimeZone.currentSystemDefault()).monthNumber)
    var day by mutableIntStateOf(initialDate.toLocalDateTime(TimeZone.currentSystemDefault()).dayOfMonth)

    val yearList = (minDate.toLocalDateTime(TimeZone.currentSystemDefault()).year..maxDate.toLocalDateTime(TimeZone.currentSystemDefault()).year).toList()
    val monthList = (1..12).toList()
    val dayList = (1..31).toList() // TODO : Change DateList when month change.

    companion object {
        fun Saver(): Saver<ProgDatePickerState, *> = Saver(
            save = {
                listOf(
                    "${it.year}.${it.month}.${it.day}",
                    it.maxDate,
                    it.minDate
                )
            },
            restore = { value ->
                ProgDatePickerState(
                    initialDate = Instant.parse(value[0] as String),
                    maxDate = value[1] as Instant,
                    minDate = value[2] as Instant
                )
            }
        )
    }
}

@Suppress("UNUSED")
@Composable
fun rememberProgDatePickerState(
    initialDate: Instant,
    maxDate: Instant = Instant.parse("2999-12-31T23:59:59.00Z"),
    minDate: Instant = Instant.parse("1000-01-01T00:00:00.00Z")
): ProgDatePickerState = rememberSaveable(saver = ProgDatePickerState.Saver()) {
    ProgDatePickerState(
        initialDate,
        maxDate,
        minDate
    )
}