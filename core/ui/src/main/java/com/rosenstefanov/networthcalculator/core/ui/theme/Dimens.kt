package com.rosenstefanov.networthcalculator.core.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

object NetWorthRadius {
    val xs = 9.dp
    val sm = 13.dp
    val md = 18.dp
    val lg = 24.dp
    val xl = 26.dp
    val pill = 999.dp
}

object NetWorthSpacing {
    val x1 = 4.dp
    val x2 = 8.dp
    val x3 = 12.dp
    val x4 = 16.dp
    val x5 = 20.dp
    val x6 = 24.dp
    val x8 = 32.dp
}

internal val NetWorthShapes = Shapes(
    extraSmall = RoundedCornerShape(NetWorthRadius.xs),
    small = RoundedCornerShape(NetWorthRadius.sm),
    medium = RoundedCornerShape(NetWorthRadius.md),
    large = RoundedCornerShape(NetWorthRadius.lg),
    extraLarge = RoundedCornerShape(NetWorthRadius.xl),
)
