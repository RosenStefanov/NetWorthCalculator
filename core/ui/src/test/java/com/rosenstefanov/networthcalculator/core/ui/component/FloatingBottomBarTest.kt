package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.models.FloatingBottomBarItem
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import org.junit.Assert.assertEquals
import org.junit.Test

class FloatingBottomBarTest : ScreenSnapshotTest() {

    private val items = listOf(
        FloatingBottomBarItem(NetWorthIcons.Dashboard, "Dashboard"),
        FloatingBottomBarItem(NetWorthIcons.AssetsCoins, "Assets"),
        FloatingBottomBarItem(NetWorthIcons.LiabilitiesCard, "Liabilities"),
    )

    @Test
    fun floatingBar_dashboardSelected_light() = captureSnapshot {
        FloatingBottomBar(
            items = items,
            selectedIndex = 0,
            onSelect = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp),
        )
    }

    @Test
    fun floatingBar_assetsSelected_dark() = captureSnapshot(darkTheme = true) {
        FloatingBottomBar(
            items = items,
            selectedIndex = 1,
            onSelect = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp),
        )
    }

    @Test
    fun floatingBar_clickSelects() {
        var selected = 0
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                FloatingBottomBar(
                    items = items,
                    selectedIndex = selected,
                    onSelect = { selected = it },
                )
            }
        }
        composeTestRule.onNodeWithContentDescription("Liabilities").performClick()
        assertEquals(2, selected)
    }
}
