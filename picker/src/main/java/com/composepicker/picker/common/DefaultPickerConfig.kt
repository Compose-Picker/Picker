package com.composepicker.picker.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DefaultPickerConfig private constructor() {
    companion object {
        val arrangement: Dp = 8.dp
        val highLightBackGroundColor: Color = Color.Blue
        val unHighLightBackGroundColor: Color = Color.White
        val highLightTextColor: Color = Color.White
        val unHighLightTextColor: Color = Color.Black
        val fontFamily: TextStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            color = Color.Black
        )
        val highLightedTextSize: TextUnit = 20.sp
        val unHighLightedTextSize: TextUnit = 16.sp
        val dividerStyle: Shape = RoundedCornerShape(10.dp)
        val dividerColor: Color = Color.Gray
        val dividerThickness: Dp = 1.dp
    }
}