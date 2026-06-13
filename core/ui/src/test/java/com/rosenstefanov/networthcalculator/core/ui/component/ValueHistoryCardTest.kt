package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import org.junit.Assert.assertEquals
import org.junit.Test

class ValueHistoryCardTest : ScreenSnapshotTest() {

    private val assetValues =
        listOf(168_000f, 186_000f, 203_000f, 221_000f, 244_000f, 259_000f, 276_000f, 291_000f, 304_000f, 312_000f)
    private val liabilityValues =
        listOf(132_000f, 129_500f, 127_000f, 124_000f, 121_000f, 118_500f, 115_500f, 112_800f, 110_000f, 108_200f)
    private val months = listOf("Jul", "Sep", "Nov", "Jan", "Mar", "Jun")

    @Test
    fun valueHistory_asset_light() = captureSnapshot {
        ValueHistoryCard(
            title = "Value history",
            accent = Color(0xFF3B6BFF),
            values = assetValues,
            months = months,
            range = "1Y",
            onRange = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        )
    }

    @Test
    fun valueHistory_liability_dark() = captureSnapshot(darkTheme = true) {
        ValueHistoryCard(
            title = "Balance history",
            accent = Color(0xFF9333EA),
            values = liabilityValues,
            months = months,
            range = "1Y",
            onRange = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        )
    }

    @Test
    fun rangeChip_invokesOnRange() {
        var picked = ""
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                ValueHistoryCard(
                    title = "Value history",
                    accent = Color(0xFF3B6BFF),
                    values = assetValues,
                    months = months,
                    range = "1Y",
                    onRange = { picked = it },
                )
            }
        }
        composeTestRule.onNodeWithText("3M").performClick()
        assertEquals("3M", picked)
    }
}
