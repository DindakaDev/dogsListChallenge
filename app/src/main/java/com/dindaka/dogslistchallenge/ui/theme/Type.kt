package com.dindaka.dogslistchallenge.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        color = TextPrimaryColor,
        fontSize = 25.sp
    ),
    titleMedium = TextStyle(
        color = TextPrimaryColor,
        fontSize = 25.sp
    ),
    bodyLarge = TextStyle(
        color = TextSecondaryColor,
        textAlign = TextAlign.Justify,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        color = TextPrimaryColor,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    )
)