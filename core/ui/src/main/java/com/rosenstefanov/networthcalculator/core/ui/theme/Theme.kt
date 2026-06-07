package com.rosenstefanov.networthcalculator.core.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = BrandBlue,
    onPrimary = LightSurface,
    primaryContainer = AssetsTintLight,
    onPrimaryContainer = LightInk,
    secondary = BrandGrape,
    onSecondary = LightSurface,
    secondaryContainer = LiabilitiesTintLight,
    onSecondaryContainer = LightInk,
    tertiary = BrandIndigo,
    onTertiary = LightSurface,
    error = SemanticNegative,
    onError = LightSurface,
    background = LightBg,
    onBackground = LightInk,
    surface = LightSurface,
    onSurface = LightInk,
    surfaceVariant = LightSurface2,
    onSurfaceVariant = LightInkSub,
    outline = LightLine,
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkAssets,
    onPrimary = DarkBg,
    primaryContainer = AssetsTintDark,
    onPrimaryContainer = DarkInk,
    secondary = DarkLiabilities,
    onSecondary = DarkBg,
    secondaryContainer = LiabilitiesTintDark,
    onSecondaryContainer = DarkInk,
    tertiary = BrandIndigo,
    onTertiary = DarkInk,
    error = SemanticNegative,
    onError = DarkBg,
    background = DarkBg,
    onBackground = DarkInk,
    surface = DarkSurface,
    onSurface = DarkInk,
    surfaceVariant = DarkSurface2,
    onSurfaceVariant = DarkInkSub,
    outline = DarkLine,
)

@Composable
fun NetWorthCalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val extendedColors = if (darkTheme) DarkExtendedColors else LightExtendedColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window ?: return@SideEffect
            val controller = WindowCompat.getInsetsController(window, view)
            controller.isAppearanceLightStatusBars = !darkTheme
            controller.isAppearanceLightNavigationBars = !darkTheme
        }
    }

    CompositionLocalProvider(LocalNetWorthExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = NetWorthShapes,
            content = content,
        )
    }
}

object NetWorthTheme {
    val extendedColors: NetWorthExtendedColors
        @Composable
        @ReadOnlyComposable
        get() = LocalNetWorthExtendedColors.current

    val spacing: NetWorthSpacing get() = NetWorthSpacing

    val radius: NetWorthRadius get() = NetWorthRadius
}
