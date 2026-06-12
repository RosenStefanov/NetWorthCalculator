package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability

import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.core.common.CurrencyFormatter
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability.models.AddLiabilityIntent
import org.junit.Test

class AddLiabilityScreenTest : ScreenSnapshotTest() {

    @Test
    fun testAddLiabilityScreenPreview() = captureSnapshot { AddLiabilityScreenPreview() }

    // Route-level: a NavigateBack effect is routed to the back callback.
    @Test
    fun navigateBackEffect_invokesOnNavigateBack() {
        var navigatedBack = false
        val viewModel = AddLiabilityViewModel(CurrencyFormatter())
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                AddLiabilityScreen(onNavigateBack = { navigatedBack = true }, viewModel = viewModel)
            }
        }

        viewModel.onIntent(AddLiabilityIntent.SaveClicked)
        composeTestRule.waitForIdle()

        assertThat(navigatedBack).isTrue()
    }
}
