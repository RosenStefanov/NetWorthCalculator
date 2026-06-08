package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import org.junit.Assert.assertEquals
import org.junit.Test

class NetWorthSegmentedChipsTest : ScreenSnapshotTest() {

    private val ranges = listOf("1M", "6M", "1Y", "All")

    @Test
    fun segmentedChips_light() = captureSnapshot {
        NetWorthSegmentedChips(
            options = ranges,
            selected = "1Y",
            onSelected = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
        )
    }

    @Test
    fun segmentedChips_dark() = captureSnapshot(darkTheme = true) {
        NetWorthSegmentedChips(
            options = ranges,
            selected = "1Y",
            onSelected = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
        )
    }

    @Test
    fun segmentedChips_clickSelects() {
        var selected = "1Y"
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                var current by remember { mutableStateOf("1Y") }
                NetWorthSegmentedChips(
                    options = ranges,
                    selected = current,
                    onSelected = {
                        current = it
                        selected = it
                    },
                )
            }
        }
        composeTestRule.onNodeWithText("6M").performClick()
        assertEquals("6M", selected)
    }
}
