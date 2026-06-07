package com.rosenstefanov.networthcalculator.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val JakartaSans: FontFamily = FontFamily.Default
val SpaceGrotesk: FontFamily = FontFamily.Default

// Token weights.
private val Bold = FontWeight.W700
private val SemiBold = FontWeight.W600
private val Medium = FontWeight.W500

val Typography = Typography(
    // display — net-worth value (numeric face)
    displayLarge = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = Bold,
        fontSize = 42.sp,
        lineHeight = 42.sp,
    ),
    // h1 — screen / name
    headlineSmall = TextStyle(
        fontFamily = JakartaSans,
        fontWeight = Bold,
        fontSize = 19.sp,
        lineHeight = 22.8.sp,
    ),
    // title — card titles
    titleMedium = TextStyle(
        fontFamily = JakartaSans,
        fontWeight = Bold,
        fontSize = 15.sp,
        lineHeight = 19.5.sp,
    ),
    // body — list item name
    bodyLarge = TextStyle(
        fontFamily = JakartaSans,
        fontWeight = SemiBold,
        fontSize = 14.5.sp,
        lineHeight = 20.3.sp,
    ),
    // label — legends, segmented
    labelLarge = TextStyle(
        fontFamily = JakartaSans,
        fontWeight = SemiBold,
        fontSize = 12.5.sp,
        lineHeight = 16.25.sp,
    ),
    // overline — card eyebrow (uppercase applied at call site)
    labelMedium = TextStyle(
        fontFamily = JakartaSans,
        fontWeight = SemiBold,
        fontSize = 12.sp,
        lineHeight = 15.6.sp,
        letterSpacing = 0.6.sp, // .05em at 12sp
    ),
    // caption — category / sub
    bodySmall = TextStyle(
        fontFamily = JakartaSans,
        fontWeight = Medium,
        fontSize = 12.sp,
        lineHeight = 16.8.sp,
    ),
    // micro — axis labels, nav
    labelSmall = TextStyle(
        fontFamily = JakartaSans,
        fontWeight = Medium,
        fontSize = 10.sp,
        lineHeight = 13.sp,
    ),
)
