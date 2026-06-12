package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.performClick
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import org.junit.Assert.assertTrue
import org.junit.Test

class DetailTopBarTest : ScreenSnapshotTest() {

    @Test
    fun detailTopBar_light() = captureSnapshot {
        DetailTopBar(
            title = "Add Asset",
            onNavigateBack = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        )
    }

    @Test
    fun detailTopBar_dark() = captureSnapshot(darkTheme = true) {
        DetailTopBar(
            title = "Settings",
            onNavigateBack = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        )
    }

    @Test
    fun backButton_invokesOnNavigateBack() {
        var back = false
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                DetailTopBar(title = "Add Asset", onNavigateBack = { back = true })
            }
        }
        composeTestRule.onNode(hasClickAction()).performClick()
        assertTrue(back)
    }
}
