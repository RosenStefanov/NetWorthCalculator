package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NetWorthSurfaceCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    contentPadding: PaddingValues = PaddingValues(20.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    val surface = MaterialTheme.colorScheme.surface
    val isDark = surface.luminance() < 0.5f
    val shape = RoundedCornerShape(cornerRadius)

    val shaped = if (isDark) {
        Modifier
            .clip(shape)
            .background(surface)
            .border(1.dp, Color.White.copy(alpha = 0.06f), shape)
    } else {
        Modifier
            .shadow(elevation = 2.dp, shape = shape, ambientColor = ShadowColor, spotColor = ShadowColor)
            .clip(shape)
            .background(surface)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(shaped)
            .padding(contentPadding),
        content = content,
    )
}

private val ShadowColor = Color(0xFF1E1646)
