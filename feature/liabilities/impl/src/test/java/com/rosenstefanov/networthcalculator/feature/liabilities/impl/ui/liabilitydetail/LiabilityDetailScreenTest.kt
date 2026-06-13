package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail

import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail.models.LiabilityDetailIntent
import org.junit.Test

class LiabilityDetailScreenTest : ScreenSnapshotTest() {

    @Test
    fun testLiabilityDetailScreenPreview() = captureSnapshot { LiabilityDetailScreenPreview() }

    // Route-level: a NavigateBack effect is routed to the back callback.
    @Test
    fun navigateBackEffect_invokesOnNavigateBack() {
        var navigatedBack = false
        val viewModel = LiabilityDetailViewModel()
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                LiabilityDetailScreen(onNavigateBack = { navigatedBack = true }, viewModel = viewModel)
            }
        }

        viewModel.onIntent(LiabilityDetailIntent.CloseClicked)
        composeTestRule.waitForIdle()

        assertThat(navigatedBack).isTrue()
    }
}
