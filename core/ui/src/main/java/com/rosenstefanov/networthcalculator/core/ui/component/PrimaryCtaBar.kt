package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthBrandBrush
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthLiabilitiesBrush

private val CtaShape = RoundedCornerShape(16.dp)
private val DisabledFill = SolidColor(Color(0xFFDFE2EE))
private val DisabledLabel = Color(0xFFA4A9BD)

@Composable
fun PrimaryCtaBar(
    label: String,
    gradient: Brush,
    glow: Color,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val fade = MaterialTheme.colorScheme.background
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    0f to fade.copy(alpha = 0f),
                    0.32f to fade,
                ),
            )
            .navigationBarsPadding()
            .padding(start = 22.dp, end = 22.dp, top = 16.dp, bottom = 22.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .shadow(
                    elevation = if (enabled) 12.dp else 0.dp,
                    shape = CtaShape,
                    ambientColor = glow,
                    spotColor = glow,
                )
                .clip(CtaShape)
                .background(if (enabled) gradient else DisabledFill)
                .clickable(enabled = enabled, onClick = onClick),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = label,
                fontFamily = JakartaSans,
                fontSize = 15.5.sp,
                fontWeight = FontWeight.Bold,
                color = if (enabled) Color.White else DisabledLabel,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF4F5FB)
@Composable
internal fun PrimaryCtaBarEnabledPreview() {
    NetWorthCalculatorTheme {
        PrimaryCtaBar(
            label = "Add asset",
            gradient = NetWorthBrandBrush,
            glow = Color(0xFF5B45F5).copy(alpha = 0.55f),
            enabled = true,
            onClick = {},
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF4F5FB)
@Composable
internal fun PrimaryCtaBarDisabledPreview() {
    NetWorthCalculatorTheme {
        PrimaryCtaBar(
            label = "Add liability",
            gradient = NetWorthLiabilitiesBrush,
            glow = Color(0xFF9333EA).copy(alpha = 0.55f),
            enabled = false,
            onClick = {},
        )
    }
}
