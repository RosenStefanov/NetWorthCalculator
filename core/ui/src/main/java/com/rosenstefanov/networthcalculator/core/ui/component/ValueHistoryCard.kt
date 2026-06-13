package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthTheme

private val RangeOptions = listOf("3M", "1Y", "All")

@Composable
fun ValueHistoryCard(
    title: String,
    accent: Color,
    values: List<Float>,
    months: List<String>,
    range: String,
    onRange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    NetWorthSurfaceCard(
        modifier = modifier,
        cornerRadius = 20.dp,
        contentPadding = PaddingValues(18.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                fontFamily = JakartaSans,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.01).em,
                color = MaterialTheme.colorScheme.onSurface,
            )
            NetWorthSegmentedChips(
                options = RangeOptions,
                selected = range,
                onSelected = onRange,
            )
        }

        Spacer(Modifier.height(14.dp))

        TrendChart(
            values = values,
            accent = accent,
            surface = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth(),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = TrendChartPlotInset, top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            months.forEach { month ->
                Text(
                    text = month,
                    fontFamily = JakartaSans,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = NetWorthTheme.extendedColors.inkSub,
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF0F6)
@Composable
internal fun ValueHistoryCardAssetPreview() {
    NetWorthCalculatorTheme {
        ValueHistoryCard(
            title = "Value history",
            accent = Color(0xFF3B6BFF),
            values = listOf(168_000f, 186_000f, 203_000f, 221_000f, 244_000f, 259_000f, 276_000f, 291_000f, 304_000f, 312_000f),
            months = listOf("Jul", "Sep", "Nov", "Jan", "Mar", "Jun"),
            range = "1Y",
            onRange = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF0F6)
@Composable
internal fun ValueHistoryCardLiabilityPreview() {
    NetWorthCalculatorTheme {
        ValueHistoryCard(
            title = "Balance history",
            accent = Color(0xFF9333EA),
            values = listOf(132_000f, 129_500f, 127_000f, 124_000f, 121_000f, 118_500f, 115_500f, 112_800f, 110_000f, 108_200f),
            months = listOf("Jul", "Sep", "Nov", "Jan", "Mar", "Jun"),
            range = "1Y",
            onRange = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
