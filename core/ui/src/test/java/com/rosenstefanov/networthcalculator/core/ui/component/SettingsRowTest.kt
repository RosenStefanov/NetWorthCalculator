package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import org.junit.Assert.assertTrue
import org.junit.Test

class SettingsRowTest : ScreenSnapshotTest() {

    @Test
    fun settingsRow_withDescAndValue_light() = captureSnapshot {
        SettingsRow(
            icon = NetWorthIcons.Dollar,
            name = "Currency",
            desc = "Used across the app",
            value = "USD $",
            onClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
        )
    }

    @Test
    fun settingsRow_nameOnly_dark() = captureSnapshot(darkTheme = true) {
        SettingsRow(
            icon = NetWorthIcons.Star,
            name = "Rate NetWorth",
            onClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
        )
    }

    @Test
    fun click_invokesOnClick() {
        var clicked = false
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                SettingsRow(
                    icon = NetWorthIcons.Dollar,
                    name = "Currency",
                    onClick = { clicked = true },
                )
            }
        }
        composeTestRule.onNodeWithText("Currency").performClick()
        assertTrue(clicked)
    }
}
