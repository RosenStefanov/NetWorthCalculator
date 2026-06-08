package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import org.junit.Assert.assertEquals
import org.junit.Test

class NetWorthSegmentedToggleTest : ScreenSnapshotTest() {

    private val options = listOf("Assets", "Liabilities")

    @Test
    fun toggle_light() = captureSnapshot {
        NetWorthSegmentedToggle(
            options = options,
            selected = "Assets",
            onSelected = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
        )
    }

    @Test
    fun toggle_dark() = captureSnapshot(darkTheme = true) {
        NetWorthSegmentedToggle(
            options = options,
            selected = "Liabilities",
            onSelected = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
        )
    }

    @Test
    fun toggle_clickSelects() {
        var selected = "Assets"
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                NetWorthSegmentedToggle(
                    options = options,
                    selected = selected,
                    onSelected = { selected = it },
                )
            }
        }
        composeTestRule.onNodeWithText("Liabilities").performClick()
        assertEquals("Liabilities", selected)
    }
}
