package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

// Icon-button spec from the design system.
private val IconButtonSize = 44.dp
private val IconButtonRadius = 14.dp
private val IconButtonGlyphSize = 22.dp
private val IconButtonShadowSm = 2.dp // "shadow sm" — subtle card lift

@Composable
fun NetWorthTopAppBar(
    modifier: Modifier = Modifier,
    @DrawableRes actionIcon: Int? = null,
    actionContentDescription: String? = null,
    onActionClick: () -> Unit = {},
    actionTint: Color = MaterialTheme.colorScheme.onBackground,
    leading: @Composable RowScope.() -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .heightIn(min = 56.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            content = leading,
        )

        if (actionIcon != null) {
            val onDarkSurface = MaterialTheme.colorScheme.surface.luminance() < 0.5f
            val actionBorder = if (onDarkSurface) {
                BorderStroke(1.dp, Color.White.copy(alpha = 0.22f))
            } else {
                null
            }
            Card(
                onClick = onActionClick,
                modifier = Modifier.size(IconButtonSize),
                shape = RoundedCornerShape(IconButtonRadius),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = IconButtonShadowSm),
                border = actionBorder,
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(actionIcon),
                        contentDescription = actionContentDescription,
                        tint = actionTint,
                        modifier = Modifier.size(IconButtonGlyphSize),
                    )
                }
            }
        }
    }
}
