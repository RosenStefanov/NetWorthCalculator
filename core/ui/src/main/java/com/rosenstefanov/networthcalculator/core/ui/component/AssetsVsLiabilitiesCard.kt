package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.SemanticAssets
import com.rosenstefanov.networthcalculator.core.ui.theme.SemanticLiabilities
import com.rosenstefanov.networthcalculator.core.ui.theme.SpaceGrotesk

@Composable
fun AssetsVsLiabilitiesCard(
    assetsValue: String,
    liabilitiesValue: String,
    assetsWeight: Float,
    assetsTrend: List<Float>,
    liabilitiesTrend: List<Float>,
    monthLabels: List<String>,
    selectedRange: String,
    onRangeSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    ranges: List<String> = DefaultRanges,
    title: String = "Assets vs. Liabilities",
) {
    val ink = MaterialTheme.colorScheme.onSurface
    val surface = MaterialTheme.colorScheme.surface
    val hasTrend = assetsTrend.isNotEmpty() && liabilitiesTrend.isNotEmpty()

    NetWorthSurfaceCard(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                fontFamily = JakartaSans,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.01).em,
                color = ink,
            )
            NetWorthSegmentedChips(
                options = ranges,
                selected = selectedRange,
                onSelected = onRangeSelected,
            )
        }

        Spacer(Modifier.height(14.dp))

        if (hasTrend) {
            TrendChart(
                assetsY = assetsTrend,
                liabsY = liabilitiesTrend,
                surface = surface,
            )
            Spacer(Modifier.height(6.dp))
            MonthAxis(labels = monthLabels)
            Spacer(Modifier.height(16.dp))
            NetWorthProportionBar(assetsWeight = assetsWeight)
            Spacer(Modifier.height(14.dp))
        } else {
            Text(
                text = "Add an account to see your trend",
                fontFamily = JakartaSans,
                fontSize = 12.sp,
                color = NetWorthTheme.extendedColors.inkSub,
            )
            Spacer(Modifier.height(14.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            LegendColumn(SemanticAssets, "Assets", assetsValue, Modifier.weight(1f))
            LegendColumn(SemanticLiabilities, "Liabilities", liabilitiesValue, Modifier.weight(1f))
        }
    }
}

@Composable
private fun MonthAxis(labels: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        labels.forEach { label ->
            Text(
                text = label,
                fontFamily = JakartaSans,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = NetWorthTheme.extendedColors.inkSub,
            )
        }
    }
}

@Composable
private fun LegendColumn(
    dot: Color,
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(7.dp),
        ) {
            Box(
                Modifier
                    .size(10.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(dot),
            )
            Text(
                text = label,
                fontFamily = JakartaSans,
                fontSize = 12.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = NetWorthTheme.extendedColors.inkSub,
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = value,
            fontFamily = SpaceGrotesk,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.01).em,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

private val DefaultRanges = listOf("1M", "6M", "1Y", "All")

@Preview(showBackground = true, backgroundColor = 0xFFEEF0F6)
@Composable
internal fun AssetsVsLiabilitiesCardPreview() {
    NetWorthCalculatorTheme {
        AssetsVsLiabilitiesCard(
            assetsValue = "$412,300",
            liabilitiesValue = "$127,550",
            assetsWeight = 0.764f,
            assetsTrend = listOf(.321f, .306f, .292f, .297f, .275f, .257f, .262f, .243f, .228f, .221f, .206f, .194f),
            liabilitiesTrend = listOf(.858f, .861f, .866f, .863f, .870f, .873f, .875f, .880f, .883f, .885f, .887f, .889f),
            monthLabels = listOf("Jul", "Sep", "Nov", "Jan", "Mar", "Jun"),
            selectedRange = "1Y",
            onRangeSelected = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
