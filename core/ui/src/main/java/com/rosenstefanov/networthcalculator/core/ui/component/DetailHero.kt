package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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

@Composable
fun DetailHero(
    navTitle: String,
    @DrawableRes icon: Int,
    name: String,
    category: String,
    value: String,
    deltaPct: String,
    deltaSub: String,
    isUp: Boolean,
    gradient: Brush,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clipToBounds()
            .background(gradient),
    ) {
        Box(
            Modifier
                .size(190.dp)
                .offset(x = 140.dp, y = (-30).dp)
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.10f)),
        )

        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(start = 22.dp, end = 22.dp, top = 14.dp, bottom = 26.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(13.dp))
                        .background(Color.White.copy(alpha = 0.18f))
                        .clickable(onClick = onBack),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(NetWorthIcons.ChevronLeft),
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp),
                    )
                }
                Text(
                    text = navTitle,
                    fontFamily = JakartaSans,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
                Spacer(Modifier.width(40.dp))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(9.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(9.dp))
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp),
                    )
                }
                Text(
                    text = name,
                    fontFamily = JakartaSans,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.01).em,
                    color = Color.White,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = category,
                    fontFamily = JakartaSans,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White.copy(alpha = 0.85f),
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(Color.White.copy(alpha = 0.16f))
                        .padding(horizontal = 10.dp, vertical = 3.dp),
                )
            }

            Text(
                text = value,
                fontFamily = SpaceGrotesk,
                fontSize = 52.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 52.sp,
                letterSpacing = (-0.02).em,
                color = Color.White,
                modifier = Modifier.padding(top = 18.dp, bottom = 12.dp),
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(Color.White.copy(alpha = 0.18f))
                        .padding(horizontal = 11.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Icon(
                        painter = painterResource(
                            if (isUp) NetWorthIcons.CaretUp else NetWorthIcons.CaretDown,
                        ),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(11.dp),
                    )
                    Text(
                        text = deltaPct,
                        fontFamily = JakartaSans,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                    )
                }
                Text(
                    text = deltaSub,
                    fontFamily = JakartaSans,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.82f),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun DetailHeroAssetPreview() {
    NetWorthCalculatorTheme {
        DetailHero(
            navTitle = "Asset",
            icon = NetWorthIcons.Home,
            name = "Primary Residence",
            category = "Real Estate",
            value = "$312,000",
            deltaPct = "6.1%",
            deltaSub = "+$18,000 this year",
            isUp = true,
            gradient = NetWorthBrandBrush,
            onBack = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun DetailHeroLiabilityPreview() {
    NetWorthCalculatorTheme {
        DetailHero(
            navTitle = "Liability",
            icon = NetWorthIcons.Home,
            name = "Mortgage",
            category = "Mortgage",
            value = "$108,200",
            deltaPct = "5.6%",
            deltaSub = "−$6,400 this year",
            isUp = false,
            gradient = NetWorthLiabilitiesBrush,
            onBack = {},
        )
    }
}
