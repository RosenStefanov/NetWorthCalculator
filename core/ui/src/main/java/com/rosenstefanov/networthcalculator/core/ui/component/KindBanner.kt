package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthBrandBrush
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthLiabilitiesBrush
import com.rosenstefanov.networthcalculator.core.ui.theme.SpaceGrotesk

/**
 * Gradient banner that previews the holding being added: an icon tile, a growing text block
 * (title/subtitle), and a right-aligned amount block. The [amount] mirrors the balance field live
 * (pass it already formatted, e.g. "$58,400" or "$0"), with [amountLabel] like "VALUE"/"OWED". The
 * text block truncates before the amount, so the figure stays fully visible. Per-screen difference
 * is just the text, [icon], [gradient] and [glow].
 */
@Composable
fun KindBanner(
    title: String,
    subtitle: String,
    @DrawableRes icon: Int,
    amount: String,
    amountLabel: String,
    gradient: Brush,
    glow: Color,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(20.dp)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(14.dp, shape, ambientColor = glow, spotColor = glow)
            .clip(shape)
            .background(gradient)
            .drawBehind {
                drawCircle(
                    color = Color.White.copy(alpha = 0.12f),
                    radius = 60.dp.toPx(),
                    center = Offset(x = size.width + 30.dp.toPx(), y = 20.dp.toPx()),
                )
            },
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(13.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Slot 1 — icon tile.
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp),
                )
            }

            // Slot 2 — text block (grows, truncates before the amount).
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontFamily = JakartaSans,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = subtitle,
                    fontFamily = JakartaSans,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.85f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 1.dp),
                )
            }

            // Slot 3 — amount block (fixed width, always fully visible).
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = amount,
                    fontFamily = SpaceGrotesk,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 24.sp,
                    letterSpacing = (-0.02).em,
                    color = Color.White,
                    maxLines = 1,
                )
                Text(
                    text = amountLabel.uppercase(),
                    fontFamily = JakartaSans,
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.04.em,
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.padding(top = 4.dp),
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF0F6)
@Composable
internal fun KindBannerAssetPreview() {
    NetWorthCalculatorTheme {
        KindBanner(
            title = "New asset",
            subtitle = "Something you own",
            icon = NetWorthIcons.ChartUp,
            amount = "$58,400",
            amountLabel = "VALUE",
            gradient = NetWorthBrandBrush,
            glow = Color(0xFF5B45F5).copy(alpha = 0.6f),
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF0F6)
@Composable
internal fun KindBannerLiabilityPreview() {
    NetWorthCalculatorTheme {
        KindBanner(
            title = "New liability",
            subtitle = "Something you owe",
            icon = NetWorthIcons.Scale,
            amount = "$12,300",
            amountLabel = "OWED",
            gradient = NetWorthLiabilitiesBrush,
            glow = Color(0xFF9333EA).copy(alpha = 0.6f),
            modifier = Modifier.padding(16.dp),
        )
    }
}
