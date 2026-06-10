package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthBrandBrush
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthLiabilitiesBrush
import com.rosenstefanov.networthcalculator.core.ui.theme.SpaceGrotesk

@Composable
fun TotalCard(
    label: String,
    value: String,
    deltaText: String,
    isGain: Boolean,
    count: String,
    gradient: Brush,
    glow: Color,
    modifier: Modifier = Modifier,
) {
    NetWorthCard(
        title = label.uppercase(),
        modifier = modifier,
        gradient = gradient,
        glow = glow,
        cornerRadius = 24.dp,
        shadowElevation = 16.dp,
        contentPadding = PaddingValues(horizontal = 22.dp, vertical = 20.dp),
    ) {
        Text(
            text = value,
            fontFamily = SpaceGrotesk,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 38.sp,
            letterSpacing = (-0.02).em,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(Modifier.height(11.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            NetWorthDeltaPill(text = deltaText, isGain = isGain)
            Text(
                text = count,
                fontFamily = JakartaSans,
                fontSize = 12.5.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.8f),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF0F6)
@Composable
internal fun TotalCardAssetsPreview() {
    NetWorthCalculatorTheme {
        TotalCard(
            label = "Total Assets",
            value = "$412,300",
            deltaText = "+3.1%",
            isGain = true,
            count = "7 holdings · 5 categories",
            gradient = NetWorthBrandBrush,
            glow = Color(0xFF5B45F5).copy(alpha = 0.6f),
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF0F6)
@Composable
internal fun TotalCardLiabilitiesPreview() {
    NetWorthCalculatorTheme {
        TotalCard(
            label = "Total Liabilities",
            value = "$127,550",
            deltaText = "−1.8%",
            isGain = false,
            count = "4 debts · 4 categories",
            gradient = NetWorthLiabilitiesBrush,
            glow = Color(0xFF9333EA).copy(alpha = 0.6f),
            modifier = Modifier.padding(16.dp),
        )
    }
}
