package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.ui.models.FloatingBottomBarItem
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthBrandBrush
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthTheme

@Composable
fun FloatingBottomBar(
    items: List<FloatingBottomBarItem>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isDark = MaterialTheme.colorScheme.surface.luminance() < 0.5f
    val barShape = RoundedCornerShape(24.dp)

    val containerColor = if (isDark) DarkBarColor else Color.White.copy(alpha = 0.82f)
    val borderColor = if (isDark) Color.White.copy(alpha = 0.10f) else Color.White.copy(alpha = 0.60f)
    val shadowColor = if (isDark) Color.Black.copy(alpha = 0.50f) else BarShadowColor

    Row(
        modifier = modifier
            .shadow(14.dp, barShape, ambientColor = shadowColor, spotColor = shadowColor)
            .clip(barShape)
            .background(containerColor)
            .border(1.dp, borderColor, barShape)
            .padding(7.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        items.forEachIndexed { index, item ->
            val selected = index == selectedIndex
            val itemShape = RoundedCornerShape(18.dp)

            val itemBackground = if (selected) {
                Modifier
                    .shadow(6.dp, itemShape, ambientColor = GlowColor, spotColor = GlowColor)
                    .clip(itemShape)
                    .background(NetWorthBrandBrush)
            } else {
                Modifier.clip(itemShape)
            }

            Box(
                modifier = Modifier
                    .size(46.dp)
                    .then(itemBackground)
                    .clickable { onSelect(index) },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(item.iconRes),
                    contentDescription = item.contentDescription,
                    tint = if (selected) Color.White else NetWorthTheme.extendedColors.inkSub,
                    modifier = Modifier.size(22.dp),
                )
            }
        }
    }
}

private val DarkBarColor = Color(0xFF161A36).copy(alpha = 0.85f)

private val BarShadowColor = Color(0xFF1E1646).copy(alpha = 0.34f)

private val GlowColor = Color(0xFF5B45F5).copy(alpha = 0.70f)
