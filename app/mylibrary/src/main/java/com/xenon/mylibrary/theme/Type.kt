package com.xenon.mylibrary.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.xenon.mylibrary.R

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)
@OptIn(ExperimentalTextApi::class)
val QuicksandTitleVariable = FontFamily(
    Font(
        R.font.quicksand_variable_font_wght,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(700)
        )
    )
)