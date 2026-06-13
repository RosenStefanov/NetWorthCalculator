package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets

import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets.models.AssetsIntent
import org.junit.Test

class AssetsScreenTest : ScreenSnapshotTest() {

    @Test
    fun testAssetsScreenContentPreview() = captureSnapshot { AssetsScreenContentPreview() }

    @Test
    fun testAssetsScreenLoadingPreview() = captureSnapshot { AssetsScreenLoadingPreview() }

    // Route-level: a SettingsClicked effect is routed to the settings navigation callback.
    @Test
    fun settingsEffect_invokesOnNavigateToSettings() {
        var toSettings = false
        var toAdd = false
        val viewModel = AssetsViewModel()
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                AssetsScreen(
                    onNavigateToSettings = { toSettings = true },
                    onNavigateToAddAccount = { toAdd = true },
                    viewModel = viewModel,
                )
            }
        }

        viewModel.onIntent(AssetsIntent.SettingsClicked)
        composeTestRule.waitForIdle()

        assertThat(toSettings).isTrue()
        assertThat(toAdd).isFalse()
    }

    @Test
    fun addAccountEffect_invokesOnNavigateToAddAccount() {
        var toAdd = false
        val viewModel = AssetsViewModel()
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                AssetsScreen(
                    onNavigateToSettings = {},
                    onNavigateToAddAccount = { toAdd = true },
                    viewModel = viewModel,
                )
            }
        }

        viewModel.onIntent(AssetsIntent.AddAccountClicked)
        composeTestRule.waitForIdle()

        assertThat(toAdd).isTrue()
    }

    @Test
    fun holdingEffect_invokesOnNavigateToHoldingDetail() {
        var toDetail = false
        val viewModel = AssetsViewModel()
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                AssetsScreen(
                    onNavigateToSettings = {},
                    onNavigateToAddAccount = {},
                    onNavigateToHoldingDetail = { toDetail = true },
                    viewModel = viewModel,
                )
            }
        }

        viewModel.onIntent(AssetsIntent.HoldingClicked)
        composeTestRule.waitForIdle()

        assertThat(toDetail).isTrue()
    }
}
