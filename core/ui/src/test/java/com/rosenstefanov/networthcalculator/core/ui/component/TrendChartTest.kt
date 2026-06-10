package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import org.junit.Assert.assertEquals
import org.junit.Test

class TrendChartTest : ScreenSnapshotTest() {

    // Raw value series — the chart computes its own axis.
    private val assets =
        listOf(358_000f, 362_000f, 368_000f, 374_000f, 382_000f, 389_000f, 394_000f, 399_000f, 404_000f, 408_000f, 411_000f, 412_300f)
    private val liabilities =
        listOf(124_000f, 124_500f, 125_000f, 125_500f, 126_000f, 126_400f, 126_700f, 127_000f, 127_200f, 127_350f, 127_450f, 127_550f)

    @Test
    fun trendChart_light() = captureSnapshot {
        val surface = MaterialTheme.colorScheme.surface
        TrendChart(
            assetsValues = assets,
            liabilitiesValues = liabilities,
            surface = surface,
            modifier = Modifier
                .background(surface)
                .padding(16.dp),
        )
    }

    @Test
    fun trendChart_dark() = captureSnapshot(darkTheme = true) {
        val surface = MaterialTheme.colorScheme.surface
        TrendChart(
            assetsValues = assets,
            liabilitiesValues = liabilities,
            surface = surface,
            modifier = Modifier
                .background(surface)
                .padding(16.dp),
        )
    }

    @Test
    fun niceAxis_roundsToCleanTicks() {
        // Max ~412k → 150k step (3 intervals) → 0,150k,300k,450k.
        val (max, ticks) = niceAxis(412_300f)
        assertEquals(450_000f, max)
        assertEquals(listOf(0f, 150_000f, 300_000f, 450_000f), ticks)
    }

    @Test
    fun niceAxis_handlesEmptyOrZeroData() {
        val (max, ticks) = niceAxis(0f)
        assertEquals(1f, max)
        assertEquals(listOf(0f, 1f), ticks)
    }
}
