package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

// Shared icon-button spec (42×42, radius 14, 21dp glyph).
private val TopBarButtonSize = 42.dp
private val TopBarButtonShape = RoundedCornerShape(14.dp)
private val TopBarGlyphSize = 21.dp

@Composable
fun NetWorthTopBarIconButton(
    @DrawableRes icon: Int,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isDark = MaterialTheme.colorScheme.surface.luminance() < 0.5f
    val chrome = if (isDark) {
        Modifier
            .clip(TopBarButtonShape)
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, Color.White.copy(alpha = 0.22f), TopBarButtonShape)
    } else {
        Modifier
            .shadow(2.dp, TopBarButtonShape, ambientColor = NeutralShadow, spotColor = NeutralShadow)
            .clip(TopBarButtonShape)
            .background(MaterialTheme.colorScheme.surface)
    }

    Box(
        modifier = modifier
            .size(TopBarButtonSize)
            .then(chrome)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(TopBarGlyphSize),
        )
    }
}

@Composable
fun NetWorthGradientIconButton(
    @DrawableRes icon: Int,
    contentDescription: String?,
    brush: Brush,
    glow: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(TopBarButtonSize)
            .shadow(8.dp, TopBarButtonShape, ambientColor = glow, spotColor = glow)
            .clip(TopBarButtonShape)
            .background(brush)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = contentDescription,
            tint = Color.White,
            modifier = Modifier.size(TopBarGlyphSize),
        )
    }
}

private val NeutralShadow = Color(0xFF1E1646).copy(alpha = 0.06f)
