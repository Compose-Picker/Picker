package com.composepicker.picker.common

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * Here are the properties commonly used in both DatePicker and TimePicker, along with their descriptions.
 * @property arrangement Gap between the center and top/bottom elements.
 * @property highLightBackGroundColor Background color of the center element.
 * @property unHighLightBackGroundColor Background color of the top/bottom elements.
 * @property highLightTextColor Text color of the center element.
 * @property unHighLightTextColor Text color of the top/bottom elements.
 * @property fontFamily Text style of all elements.
 * @property highLightedTextSize Text size of the center element.
 * @property unHighLightedTextSize Text size of the top/bottom elements.
 * @property dividerStyle Separator between the center and top/bottom elements.
 * @property dividerColor Separator color between the center and top/bottom elements.
 * @property dividerThickness Separator size between the center and top/bottom elements.
 */
class PickerCommonConfiguration private constructor(
    val arrangement: Dp,
    val highLightBackGroundColor: Color,
    val unHighLightBackGroundColor: Color,
    val highLightTextColor: Color,
    val unHighLightTextColor: Color,
    val fontFamily: TextStyle,
    val highLightedTextSize: TextUnit,
    val unHighLightedTextSize: TextUnit,
    val dividerStyle: Shape,
    val dividerColor: Color,
    val dividerThickness: Dp
) {
    class Builder {
        private var arrangement: Dp = DefaultPickerConfig.arrangement
        private var highLightBackGroundColor: Color = DefaultPickerConfig.highLightBackGroundColor
        private var unHighLightBackGroundColor: Color =
            DefaultPickerConfig.unHighLightBackGroundColor
        private var highLightTextColor: Color = DefaultPickerConfig.highLightTextColor
        private var unHighLightTextColor: Color = DefaultPickerConfig.unHighLightTextColor
        private var fontFamily: TextStyle = DefaultPickerConfig.fontFamily
        private var highLightedTextSize: TextUnit = DefaultPickerConfig.highLightedTextSize
        private var unHighLightedTextSize: TextUnit = DefaultPickerConfig.unHighLightedTextSize
        private var dividerStyle: Shape = DefaultPickerConfig.dividerStyle
        private var dividerColor: Color = DefaultPickerConfig.dividerColor
        private var dividerThickness: Dp = DefaultPickerConfig.dividerThickness

        fun arrangement(arrangement: Dp) = apply { this.arrangement = arrangement }
        fun highLightBackGroundColor(highLightBackGroundColor: Color) =
            apply { this.highLightBackGroundColor = highLightBackGroundColor }

        fun unHighLightBackGroundColor(unHighLightBackGroundColor: Color) =
            apply { this.unHighLightBackGroundColor = unHighLightBackGroundColor }

        fun highLightTextColor(highLightTextColor: Color) =
            apply { this.highLightTextColor = highLightTextColor }

        fun unHighLightTextColor(unHighLightTextColor: Color) =
            apply { this.unHighLightTextColor = unHighLightTextColor }

        fun fontFamily(fontFamily: TextStyle) = apply { this.fontFamily = fontFamily }
        fun highLightedTextSize(highLightedTextSize: TextUnit) =
            apply { this.highLightedTextSize = highLightedTextSize.clampTextSize() }

        fun unHighLightedTextSize(unHighLightedTextSize: TextUnit) =
            apply { this.unHighLightedTextSize = unHighLightedTextSize.clampTextSize() }

        fun dividerStyle(dividerStyle: Shape) = apply { this.dividerStyle = dividerStyle }
        fun dividerColor(dividerColor: Color) = apply { this.dividerColor = dividerColor }
        fun dividerThickness(dividerThickness: Dp) =
            apply { this.dividerThickness = dividerThickness }

        fun build() = PickerCommonConfiguration(
            arrangement,
            highLightBackGroundColor,
            unHighLightBackGroundColor,
            highLightTextColor,
            unHighLightTextColor,
            fontFamily,
            highLightedTextSize,
            unHighLightedTextSize,
            dividerStyle,
            dividerColor,
            dividerThickness
        )
    }
}

fun TextUnit.clampTextSize() = when {
    this < 14.sp -> 14.sp
    this > 32.sp -> 32.sp
    else -> this
}