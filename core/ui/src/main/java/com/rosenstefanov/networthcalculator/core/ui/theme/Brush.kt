package com.rosenstefanov.networthcalculator.core.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush

private fun cornerGradient(colors: List<Color>, stops: List<Float>): ShaderBrush =
    object : ShaderBrush() {
        override fun createShader(size: Size): Shader = LinearGradientShader(
            from = Offset(0f, 0f),
            to = Offset(size.width, size.height),
            colors = colors,
            colorStops = stops,
        )
    }

val NetWorthBrandBrush: ShaderBrush = cornerGradient(NetWorthBrandGradient, listOf(0f, 0.52f, 1f))

val NetWorthLiabilitiesBrush: ShaderBrush = cornerGradient(
    colors = listOf(Color(0xFF7C3AED), Color(0xFF9333EA), Color(0xFFC026D3)),
    stops = listOf(0f, 0.5f, 1f),
)
