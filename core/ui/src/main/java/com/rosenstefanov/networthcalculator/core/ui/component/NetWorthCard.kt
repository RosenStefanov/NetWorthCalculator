package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthBrandBrush
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.SpaceGrotesk

val NetWorthCardGlow: Color = Color(0xFF5B45F5).copy(alpha = 0.6f)

@Composable
fun NetWorthCard(
    title: String,
    modifier: Modifier = Modifier,
    gradient: Brush = NetWorthBrandBrush,
    glow: Color = NetWorthCardGlow,
    cornerRadius: Dp = 26.dp,
    shadowElevation: Dp = 18.dp,
    contentPadding: PaddingValues = PaddingValues(start = 22.dp, top = 22.dp, end = 22.dp, bottom = 20.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = RoundedCornerShape(cornerRadius)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = shadowElevation, shape = shape, ambientColor = glow, spotColor = glow)
            .clip(shape)
            .background(gradient),
    ) {
        Box(
            Modifier
                .size(170.dp)
                .offset(x = 124.dp, y = (-56).dp)
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.12f)),
        )

        Column(Modifier.padding(contentPadding)) {
            Text(
                text = title,
                fontFamily = JakartaSans,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.05.em,
                color = Color.White.copy(alpha = 0.85f),
            )
            Spacer(Modifier.height(9.dp))
            content()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF0F6)
@Composable
internal fun NetWorthCardPreview() {
    NetWorthCalculatorTheme {
        NetWorthCard(
            title = "TOTAL NET WORTH",
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "$284,750",
                fontFamily = SpaceGrotesk,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 42.sp,
                letterSpacing = (-0.02).em,
                color = Color.White,
            )
            Spacer(Modifier.height(13.dp))
            NetWorthDeltaPill(text = "+$11,480 · 4.2% this month", isGain = true)
        }
    }
}
