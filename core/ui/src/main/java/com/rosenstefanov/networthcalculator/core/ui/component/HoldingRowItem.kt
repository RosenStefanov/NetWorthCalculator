package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.rosenstefanov.networthcalculator.core.ui.models.HoldingType
import com.rosenstefanov.networthcalculator.core.ui.models.TopHolding
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.SpaceGrotesk

@Composable
internal fun HoldingRowItem(
    item: TopHolding,
    maxAmount: Long,
    type: HoldingType,
) {
    val isAssets = type == HoldingType.Assets
    val accent = if (isAssets) {
        NetWorthTheme.extendedColors.assets
    } else {
        NetWorthTheme.extendedColors.liabilities
    }
    val chipBg = if (isAssets) {
        NetWorthTheme.extendedColors.assetsTint
    } else {
        NetWorthTheme.extendedColors.liabilitiesTint
    }
    val fraction = if (maxAmount > 0L) {
        (item.amount.toFloat() / maxAmount).coerceIn(0f, 1f)
    } else {
        0f
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 44.dp)
            .padding(horizontal = 2.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(13.dp),
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(13.dp))
                .background(chipBg),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(item.iconRes),
                contentDescription = null,
                tint = accent,
                modifier = Modifier.size(21.dp),
            )
        }

        Column(Modifier.weight(1f)) {
            Text(
                text = item.name,
                fontFamily = JakartaSans,
                fontSize = 14.5.sp,
                fontWeight = FontWeight.W600,
                letterSpacing = (-0.01).em,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(1.dp))
            Text(
                text = item.category,
                fontFamily = JakartaSans,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = NetWorthTheme.extendedColors.inkSub,
            )
            Spacer(Modifier.height(6.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(NetWorthTheme.extendedColors.field),
            ) {
                Box(
                    Modifier
                        .fillMaxWidth(fraction)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(3.dp))
                        .background(accent),
                )
            }
        }

        Text(
            text = item.amountLabel,
            fontFamily = SpaceGrotesk,
            fontSize = 14.5.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
        )
    }
}
