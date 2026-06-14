package com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard

import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.models.HoldingType
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.DashboardIntent
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.onboarding.WelcomeScreenPreview
import org.junit.Test

class DashboardScreenTest : ScreenSnapshotTest() {

    @Test
    fun testDashboardLoadingPreview() = captureSnapshot { DashboardLoadingPreview() }

    @Test
    fun testDashboardEmptyPreview() = captureSnapshot { DashboardEmptyPreview() }

    @Test
    fun testDashboardContentPreview() = captureSnapshot { DashboardContentPreview() }

    @Test
    fun testDashboardErrorPreview() = captureSnapshot { DashboardErrorPreview() }

    @Test
    fun testWelcomeScreenPreview() = captureSnapshot { WelcomeScreenPreview() }

    // Route-level: a holding click routes to the matching detail screen by type.
    @Test
    fun holdingClick_routesToDetailByType() {
        var toAsset = false
        var toLiability = false
        val viewModel = DashboardViewModel()
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                DashboardScreen(
                    onNavigateToSettings = {},
                    onNavigateToAssetDetail = { toAsset = true },
                    onNavigateToLiabilityDetail = { toLiability = true },
                    viewModel = viewModel,
                )
            }
        }

        viewModel.onIntent(DashboardIntent.HoldingClicked(HoldingType.Assets))
        composeTestRule.waitForIdle()
        assertThat(toAsset).isTrue()
        assertThat(toLiability).isFalse()

        viewModel.onIntent(DashboardIntent.HoldingClicked(HoldingType.Liabilities))
        composeTestRule.waitForIdle()
        assertThat(toLiability).isTrue()
    }
}
