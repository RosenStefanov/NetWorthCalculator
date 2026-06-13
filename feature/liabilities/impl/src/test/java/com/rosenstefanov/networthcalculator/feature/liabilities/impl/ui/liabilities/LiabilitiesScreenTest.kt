package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities

import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities.models.LiabilitiesIntent
import org.junit.Test

class LiabilitiesScreenTest : ScreenSnapshotTest() {

    @Test
    fun testLiabilitiesScreenContentPreview() = captureSnapshot { LiabilitiesScreenContentPreview() }

    @Test
    fun testLiabilitiesScreenLoadingPreview() = captureSnapshot { LiabilitiesScreenLoadingPreview() }

    // Route-level: a SettingsClicked effect is routed to the settings navigation callback.
    @Test
    fun settingsEffect_invokesOnNavigateToSettings() {
        var toSettings = false
        var toAdd = false
        val viewModel = LiabilitiesViewModel()
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                LiabilitiesScreen(
                    onNavigateToSettings = { toSettings = true },
                    onNavigateToAddAccount = { toAdd = true },
                    viewModel = viewModel,
                )
            }
        }

        viewModel.onIntent(LiabilitiesIntent.SettingsClicked)
        composeTestRule.waitForIdle()

        assertThat(toSettings).isTrue()
        assertThat(toAdd).isFalse()
    }

    @Test
    fun addAccountEffect_invokesOnNavigateToAddAccount() {
        var toAdd = false
        val viewModel = LiabilitiesViewModel()
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                LiabilitiesScreen(
                    onNavigateToSettings = {},
                    onNavigateToAddAccount = { toAdd = true },
                    viewModel = viewModel,
                )
            }
        }

        viewModel.onIntent(LiabilitiesIntent.AddAccountClicked)
        composeTestRule.waitForIdle()

        assertThat(toAdd).isTrue()
    }

    @Test
    fun holdingEffect_invokesOnNavigateToHoldingDetail() {
        var toDetail = false
        val viewModel = LiabilitiesViewModel()
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                LiabilitiesScreen(
                    onNavigateToSettings = {},
                    onNavigateToAddAccount = {},
                    onNavigateToHoldingDetail = { toDetail = true },
                    viewModel = viewModel,
                )
            }
        }

        viewModel.onIntent(LiabilitiesIntent.HoldingClicked)
        composeTestRule.waitForIdle()

        assertThat(toDetail).isTrue()
    }
}
