package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import org.junit.Test

class AssetsVsLiabilitiesCardTest : ScreenSnapshotTest() {

    private val assetsTrend =
        listOf(.321f, .306f, .292f, .297f, .275f, .257f, .262f, .243f, .228f, .221f, .206f, .194f)
    private val liabilitiesTrend =
        listOf(.858f, .861f, .866f, .863f, .870f, .873f, .875f, .880f, .883f, .885f, .887f, .889f)
    private val months = listOf("Jul", "Sep", "Nov", "Jan", "Mar", "Jun")

    private val card = @androidx.compose.runtime.Composable {
        AssetsVsLiabilitiesCard(
            assetsValue = "$412,300",
            liabilitiesValue = "$127,550",
            assetsWeight = 0.764f,
            assetsTrend = assetsTrend,
            liabilitiesTrend = liabilitiesTrend,
            monthLabels = months,
            selectedRange = "1Y",
            onRangeSelected = {},
            modifier = Modifier.padding(16.dp),
        )
    }

    @Test
    fun assetsVsLiabilities_light() = captureSnapshot { card() }

    @Test
    fun assetsVsLiabilities_dark() = captureSnapshot(darkTheme = true) { card() }

    @Test
    fun assetsVsLiabilities_empty() = captureSnapshot {
        AssetsVsLiabilitiesCard(
            assetsValue = "$0",
            liabilitiesValue = "$0",
            assetsWeight = 1f,
            assetsTrend = emptyList(),
            liabilitiesTrend = emptyList(),
            monthLabels = emptyList(),
            selectedRange = "1Y",
            onRangeSelected = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
