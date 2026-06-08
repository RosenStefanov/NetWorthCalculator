package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import org.junit.Test

class TrendChartTest : ScreenSnapshotTest() {

    private val assetsY =
        listOf(.321f, .306f, .292f, .297f, .275f, .257f, .262f, .243f, .228f, .221f, .206f, .194f)
    private val liabsY =
        listOf(.858f, .861f, .866f, .863f, .870f, .873f, .875f, .880f, .883f, .885f, .887f, .889f)

    // Light surface → band alpha 0.28; dot halo is white.
    @Test
    fun trendChart_light() = captureSnapshot {
        val surface = MaterialTheme.colorScheme.surface
        TrendChart(
            assetsY = assetsY,
            liabsY = liabsY,
            surface = surface,
            modifier = Modifier
                .background(surface)
                .padding(16.dp),
        )
    }

    // Dark surface → band alpha 0.34; dot halo is the dark card color.
    @Test
    fun trendChart_dark() = captureSnapshot(darkTheme = true) {
        val surface = MaterialTheme.colorScheme.surface
        TrendChart(
            assetsY = assetsY,
            liabsY = liabsY,
            surface = surface,
            modifier = Modifier
                .background(surface)
                .padding(16.dp),
        )
    }
}
