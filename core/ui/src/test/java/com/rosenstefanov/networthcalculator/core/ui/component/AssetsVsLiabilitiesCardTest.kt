package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import org.junit.Test

class AssetsVsLiabilitiesCardTest : ScreenSnapshotTest() {

    private val assetsTrend =
        listOf(358_000f, 362_000f, 368_000f, 374_000f, 382_000f, 389_000f, 394_000f, 399_000f, 404_000f, 408_000f, 411_000f, 412_300f)
    private val liabilitiesTrend =
        listOf(124_000f, 124_500f, 125_000f, 125_500f, 126_000f, 126_400f, 126_700f, 127_000f, 127_200f, 127_350f, 127_450f, 127_550f)
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
