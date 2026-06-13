package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import org.junit.Assert.assertTrue
import org.junit.Test

class DeleteConfirmDialogTest : ScreenSnapshotTest() {

    @Test
    fun confirmButton_invokesOnConfirm() {
        var confirmed = false
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                DeleteConfirmDialog(
                    title = "Delete this asset?",
                    holdingName = "Primary Residence",
                    confirmLabel = "Delete asset",
                    onConfirm = { confirmed = true },
                    onDismiss = {},
                )
            }
        }
        composeTestRule.onNodeWithText("Delete asset").performClick()
        assertTrue(confirmed)
    }

    @Test
    fun cancelButton_invokesOnDismiss() {
        var dismissed = false
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                DeleteConfirmDialog(
                    title = "Delete this liability?",
                    holdingName = "Mortgage",
                    confirmLabel = "Delete liability",
                    onConfirm = {},
                    onDismiss = { dismissed = true },
                )
            }
        }
        composeTestRule.onNodeWithText("Cancel").performClick()
        assertTrue(dismissed)
    }
}
