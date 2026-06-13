package com.rosenstefanov.networthcalculator.core.ui.theme

import androidx.compose.ui.graphics.Color

// ---- Brand ramp ----
internal val BrandBlue = Color(0xFF3B6BFF)
internal val BrandBlue600 = Color(0xFF2F57E0)
internal val BrandIndigo = Color(0xFF5B45F5)
internal val BrandPurple = Color(0xFF8A2EE8)
internal val BrandGrape = Color(0xFF9333EA)

val NetWorthBrandGradient = listOf(BrandBlue, BrandIndigo, BrandPurple)

// ---- Semantic ----
internal val SemanticAssets = Color(0xFF3B6BFF)
internal val SemanticLiabilities = Color(0xFF9333EA)
internal val SemanticPositive = Color(0xFF1FAE6E)
internal val SemanticNegative = Color(0xFFE0457B)

// ---- Tint fills (icon chips, soft backgrounds) ----
internal val AssetsTintLight = Color(0x1F3B6BFF)
internal val LiabilitiesTintLight = Color(0x1F9333EA)

// ---- Interactive accent (lifted on dark for contrast) ----
internal val AccentLight = Color(0xFF3B6BFF)
internal val AccentDark = Color(0xFF5E8CFF)
internal val AccentTintLight = Color(0x1F3B6BFF) // 12%
internal val AccentTintDark = Color(0x2E5E8CFF) // 18%
internal val AccentSoftLight = Color(0x0F3B6BFF) // 6%
internal val AccentSoftDark = Color(0x245E8CFF) // 14%

// ---- Muted greys (chevrons, placeholders, footnotes) ----
internal val ChevronLight = Color(0xFFC2C5D6)
internal val ChevronDark = Color(0xFF5A6080)
internal val FootnoteLight = Color(0xFFA4A9BD)
internal val FootnoteDark = Color(0xFF5A6080)

// ---- Inset/raised input fills ----
internal val InputFocusLight = Color(0xFFFFFFFF)
internal val InputFocusDark = Color(0xFF1C2142)

// ---- Screen base (slightly lighter than app bg in light) ----
internal val ScreenLight = Color(0xFFF4F5FB)
internal val ScreenDark = Color(0xFF0C0F24)

// ---- Placeholder text ----
internal val PlaceholderLight = Color(0xFFB7BACB)
internal val PlaceholderDark = Color(0xFF6B7099)

// ---- Sunken field fill (rest state of inline editors) ----
internal val SurfaceSunkenLight = Color(0xFFF7F8FC)
internal val SurfaceSunkenDark = Color(0x0DFFFFFF) // 5% white

// ---- Soft divider (row separators) ----
internal val LineSoftLight = Color(0xFFF1F2F8)
internal val LineSoftDark = Color(0x12FFFFFF) // 7% white

// ---- On-chip accent icon (lifted further than accent on dark) ----
internal val AccentIconLight = Color(0xFF3B6BFF)
internal val AccentIconDark = Color(0xFF8DB0FF)

// ---- Negative tint (delete chip/badge backgrounds) ----
internal val NegativeTintLight = Color(0x1AE0457B) // 10%
internal val NegativeTintDark = Color(0x29E0457B) // 16%

// ---- Disabled CTA fill ----
internal val DisabledFillLight = Color(0xFFDFE2EE)
internal val DisabledFillDark = Color(0xFF262B47)

// ---- Light theme surfaces ----
internal val LightBg = Color(0xFFEEF0F6)
internal val LightSurface = Color(0xFFFFFFFF)
internal val LightSurface2 = Color(0xFFF4F5FB)
internal val LightInk = Color(0xFF16182B)
internal val LightInkSub = Color(0xFF6B6F86)
internal val LightLine = Color(0xFFE8E9F1)
internal val LightField = Color(0xFFEEF0F7)

// ---- Dark theme surfaces ----
internal val DarkBg = Color(0xFF0C0F24)
internal val DarkSurface = Color(0xFF161A36)
internal val DarkSurface2 = Color(0xFF10132B)
internal val DarkInk = Color(0xFFEEF0FF)
internal val DarkInkSub = Color(0xFF9AA0C7)
internal val DarkLine = Color(0x14FFFFFF)
internal val DarkField = Color(0x12FFFFFF)
internal val DarkAssets = Color(0xFF9EC0FF)
internal val DarkLiabilities = Color(0xFFC79BFF)
internal val AssetsTintDark = Color(0x2E5E8CFF)
internal val LiabilitiesTintDark = Color(0x33B278FF)
