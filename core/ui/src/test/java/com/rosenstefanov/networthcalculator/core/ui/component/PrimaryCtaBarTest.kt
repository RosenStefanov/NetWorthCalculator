package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthBrandBrush
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthLiabilitiesBrush
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PrimaryCtaBarTest : ScreenSnapshotTest() {

    @Test
    fun primaryCtaBar_enabled_light() = captureSnapshot {
        PrimaryCtaBar(
            label = "Add asset",
            gradient = NetWorthBrandBrush,
            glow = Color(0xFF5B45F5).copy(alpha = 0.55f),
            enabled = true,
            onClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        )
    }

    @Test
    fun primaryCtaBar_disabled_dark() = captureSnapshot(darkTheme = true) {
        PrimaryCtaBar(
            label = "Add liability",
            gradient = NetWorthLiabilitiesBrush,
            glow = Color(0xFF9333EA).copy(alpha = 0.55f),
            enabled = false,
            onClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        )
    }

    @Test
    fun enabled_tap_invokesOnClick() {
        var clicked = false
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                PrimaryCtaBar(
                    label = "Add asset",
                    gradient = NetWorthBrandBrush,
                    glow = Color(0xFF5B45F5).copy(alpha = 0.55f),
                    enabled = true,
                    onClick = { clicked = true },
                )
            }
        }
        composeTestRule.onNodeWithText("Add asset").performClick()
        assertTrue(clicked)
    }

    @Test
    fun disabled_doesNotInvokeOnClick() {
        var clicked = false
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                PrimaryCtaBar(
                    label = "Add liability",
                    gradient = NetWorthLiabilitiesBrush,
                    glow = Color(0xFF9333EA).copy(alpha = 0.55f),
                    enabled = false,
                    onClick = { clicked = true },
                )
            }
        }
        composeTestRule.onNodeWithText("Add liability").performClick()
        assertFalse(clicked)
    }
}
