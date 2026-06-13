package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability

import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability.models.EditLiabilityIntent
import org.junit.Test

class EditLiabilityScreenTest : ScreenSnapshotTest() {

    @Test
    fun testEditLiabilityScreenPreview() = captureSnapshot { EditLiabilityScreenPreview() }

    // Route-level: a NavigateBack effect is routed to the back callback.
    @Test
    fun navigateBackEffect_invokesOnNavigateBack() {
        var navigatedBack = false
        val viewModel = EditLiabilityViewModel()
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                EditLiabilityScreen(onNavigateBack = { navigatedBack = true }, viewModel = viewModel)
            }
        }

        viewModel.onIntent(EditLiabilityIntent.CloseClicked)
        composeTestRule.waitForIdle()

        assertThat(navigatedBack).isTrue()
    }
}
