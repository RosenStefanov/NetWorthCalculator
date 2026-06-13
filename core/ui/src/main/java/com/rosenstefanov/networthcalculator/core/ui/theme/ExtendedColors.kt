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
    val accent: Color,
    val accentTint: Color,
    val accentSoft: Color,
    val accentIcon: Color,
    val chevron: Color,
    val footnote: Color,
    val inputFocus: Color,
    val screen: Color,
    val placeholder: Color,
    val surfaceSunken: Color,
    val lineSoft: Color,
    val negativeTint: Color,
    val disabledFill: Color,
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
    accent = AccentLight,
    accentTint = AccentTintLight,
    accentSoft = AccentSoftLight,
    accentIcon = AccentIconLight,
    chevron = ChevronLight,
    footnote = FootnoteLight,
    inputFocus = InputFocusLight,
    screen = ScreenLight,
    placeholder = PlaceholderLight,
    surfaceSunken = SurfaceSunkenLight,
    lineSoft = LineSoftLight,
    negativeTint = NegativeTintLight,
    disabledFill = DisabledFillLight,
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
    accent = AccentDark,
    accentTint = AccentTintDark,
    accentSoft = AccentSoftDark,
    accentIcon = AccentIconDark,
    chevron = ChevronDark,
    footnote = FootnoteDark,
    inputFocus = InputFocusDark,
    screen = ScreenDark,
    placeholder = PlaceholderDark,
    surfaceSunken = SurfaceSunkenDark,
    lineSoft = LineSoftDark,
    negativeTint = NegativeTintDark,
    disabledFill = DisabledFillDark,
)

val LocalNetWorthExtendedColors = staticCompositionLocalOf { LightExtendedColors }
