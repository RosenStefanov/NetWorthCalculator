package com.rosenstefanov.networthcalculator.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class NetWorthExtendedColors(
    val assets: Color,
    val liabilities: Color,
    val positive: Color,
    val negative: Color,
    val assetsTint: Color,
    val liabilitiesTint: Color,
    val surface2: Color,
    val field: Color,
    val inkSub: Color,
    val line: Color,
)

internal val LightExtendedColors = NetWorthExtendedColors(
    assets = SemanticAssets,
    liabilities = SemanticLiabilities,
    positive = SemanticPositive,
    negative = SemanticNegative,
    assetsTint = AssetsTintLight,
    liabilitiesTint = LiabilitiesTintLight,
    surface2 = LightSurface2,
    field = LightField,
    inkSub = LightInkSub,
    line = LightLine,
)

internal val DarkExtendedColors = NetWorthExtendedColors(
    assets = DarkAssets,
    liabilities = DarkLiabilities,
    positive = SemanticPositive,
    negative = SemanticNegative,
    assetsTint = AssetsTintDark,
    liabilitiesTint = LiabilitiesTintDark,
    surface2 = DarkSurface2,
    field = DarkField,
    inkSub = DarkInkSub,
    line = DarkLine,
)

val LocalNetWorthExtendedColors = staticCompositionLocalOf { LightExtendedColors }
