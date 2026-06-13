package com.rosenstefanov.networthcalculator.core.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Theme-independent brand & semantic colors — identical in light and dark (they already pass
 * contrast on either background). Use these for gradients, glows, the liability accent, category
 * ramps and the card-shadow tint. Theme-dependent colors live in [NetWorthExtendedColors] / the
 * Material [androidx.compose.material3.ColorScheme] instead.
 */
object NetWorthColors {
    // Brand ramp
    val Blue = Color(0xFF3B6BFF)
    val Indigo = Color(0xFF5B45F5)
    val Purple = Color(0xFF8A2EE8)
    val Grape = Color(0xFF9333EA)

    // Semantic
    val Positive = Color(0xFF1FAE6E)
    val Negative = Color(0xFFE0457B)

    // Liability gradient end stops / category ramp
    val Violet = Color(0xFF7C3AED)
    val Orchid = Color(0xFFA855F7)
    val Magenta = Color(0xFFC026D3)
    val Fuchsia = Color(0xFFD946B0)

    // Decorative glows tied to the brand gradients (theme-independent).
    val AssetGlow = Indigo
    val LiabilityGlow = Grape

    // Card / chip elevation tint (used as a shadow spot color in light; dark uses 1px borders).
    val Shadow = Color(0xFF1E1646)
}
