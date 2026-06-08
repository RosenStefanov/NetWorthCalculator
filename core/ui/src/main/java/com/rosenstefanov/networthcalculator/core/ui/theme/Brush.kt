package com.rosenstefanov.networthcalculator.core.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush

val NetWorthBrandBrush: ShaderBrush = object : ShaderBrush() {
    override fun createShader(size: Size): Shader = LinearGradientShader(
        from = Offset(0f, 0f),
        to = Offset(size.width, size.height),
        colors = NetWorthBrandGradient,
        colorStops = listOf(0f, 0.52f, 1f),
    )
}
