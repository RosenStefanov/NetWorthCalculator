package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.models.Holding
import com.rosenstefanov.networthcalculator.core.ui.models.SortMode
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import org.junit.Assert.assertEquals
import org.junit.Test

class RankedListSectionTest : ScreenSnapshotTest() {

    private val holdings = listOf(
        Holding("Primary Residence", "Real Estate", 312_000, Color(0xFF3B6BFF), NetWorthIcons.Home),
        Holding("Brokerage", "Investments", 46_000, Color(0xFF5B45F5), NetWorthIcons.ChartUp),
        Holding("Savings", "Cash", 7_400, Color(0xFF8A2EE8), NetWorthIcons.Cash),
    )

    @Test
    fun rankedList_largest() = captureSnapshot { RankedListSectionPreview() }

    @Test
    fun rankedList_sortedByName() = captureSnapshot {
        RankedListSection(
            title = "All assets",
            holdings = holdings,
            total = 412_300,
            sort = SortMode.Name,
            onSort = {},
            modifier = Modifier.padding(16.dp),
        )
    }

    @Test
    fun rankedList_empty() = captureSnapshot {
        RankedListSection(
            title = "All assets",
            holdings = emptyList(),
            total = 0,
            sort = SortMode.Largest,
            onSort = {},
            modifier = Modifier.padding(16.dp),
        )
    }

    @Test
    fun rankedList_chipSelectsSort() {
        var selected = SortMode.Largest
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                RankedListSection(
                    title = "All assets",
                    holdings = holdings,
                    total = 412_300,
                    sort = selected,
                    onSort = { selected = it },
                )
            }
        }
        composeTestRule.onNodeWithText("Name").performClick()
        assertEquals(SortMode.Name, selected)
    }
}
