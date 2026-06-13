package com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings

import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings.models.SettingsIntent
import com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings.models.SettingsUiState
import com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings.models.ThemeMode
import org.junit.Test

class SettingsScreenTest : ScreenSnapshotTest() {

    @Test
    fun testSettingsScreenPreview() = captureSnapshot { SettingsScreenPreview() }

    @Test
    fun testSettingsScreenDark() = captureSnapshot(darkTheme = true) {
        SettingsScreen(
            uiState = SettingsUiState(name = "Alex", currency = "USD", theme = ThemeMode.System),
            onIntent = {},
        )
    }

    // Route-level: a NavigateBack effect is routed to the back callback.
    @Test
    fun backEffect_invokesOnBack() {
        var back = false
        val viewModel = SettingsViewModel()
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                SettingsScreen(onBack = { back = true }, viewModel = viewModel)
            }
        }

        viewModel.onIntent(SettingsIntent.BackClicked)
        composeTestRule.waitForIdle()

        assertThat(back).isTrue()
    }

    // Focusing the name field then clearing it surfaces the empty-error state.
    @Test
    fun clearingName_showsEmptyError() {
        val viewModel = SettingsViewModel()
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                SettingsScreen(onBack = {}, viewModel = viewModel)
            }
        }

        composeTestRule.onNodeWithText("Alex").performClick()
        composeTestRule.onNodeWithContentDescription("Clear").performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Name can't be empty").assertExists()
        composeTestRule.onNodeWithText("Enter your name").assertExists()
    }
}

