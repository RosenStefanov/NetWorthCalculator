package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthBrandBrush
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import org.junit.Assert.assertTrue
import org.junit.Test

class DetailActionsTest : ScreenSnapshotTest() {

    private val glow = Color(0xFF5B45F5).copy(alpha = 0.5f)

    @Test
    fun detailActions_light() = captureSnapshot {
        DetailActions(
            accentGradient = NetWorthBrandBrush,
            glow = glow,
            onEdit = {},
            onDelete = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        )
    }

    @Test
    fun editButton_invokesOnEdit() {
        var edited = false
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                DetailActions(NetWorthBrandBrush, glow, onEdit = { edited = true }, onDelete = {})
            }
        }
        // Edit is the first clickable, delete the second.
        composeTestRule.onAllNodes(hasClickAction())[0].performClick()
        assertTrue(edited)
    }

    @Test
    fun deleteButton_invokesOnDelete() {
        var deleted = false
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                DetailActions(NetWorthBrandBrush, glow, onEdit = {}, onDelete = { deleted = true })
            }
        }
        composeTestRule.onAllNodes(hasClickAction())[1].performClick()
        assertTrue(deleted)
    }
}
