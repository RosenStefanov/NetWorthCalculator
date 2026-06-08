package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans

@Composable
fun NetWorthDeltaPill(
    text: String,
    isGain: Boolean,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White.copy(alpha = 0.18f),
    contentColor: Color = Color.White,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
            .clip(RoundedCornerShape(999.dp))
            .background(containerColor)
            .padding(horizontal = 11.dp, vertical = 6.dp),
    ) {
        Icon(
            painter = painterResource(
                if (isGain) NetWorthIcons.CaretUp else NetWorthIcons.CaretDown,
            ),
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(14.dp),
        )
        Text(
            text = text,
            fontFamily = JakartaSans,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = contentColor,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF5B45F5)
@Composable
internal fun NetWorthDeltaPillGainPreview() {
    NetWorthDeltaPill(
        text = "+$11,480 · 4.2% this month",
        isGain = true,
        modifier = Modifier.padding(16.dp),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF5B45F5)
@Composable
internal fun NetWorthDeltaPillLossPreview() {
    NetWorthDeltaPill(
        text = "−€1,230 · 1.1% this month",
        isGain = false,
        modifier = Modifier.padding(16.dp),
    )
}
