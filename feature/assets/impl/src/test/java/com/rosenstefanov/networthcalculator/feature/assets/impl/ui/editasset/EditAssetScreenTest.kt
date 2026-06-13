package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset

import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset.models.EditAssetIntent
import org.junit.Test

class EditAssetScreenTest : ScreenSnapshotTest() {

    @Test
    fun testEditAssetScreenPreview() = captureSnapshot { EditAssetScreenPreview() }

    // Route-level: a NavigateBack effect is routed to the back callback.
    @Test
    fun navigateBackEffect_invokesOnNavigateBack() {
        var navigatedBack = false
        val viewModel = EditAssetViewModel()
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                EditAssetScreen(onNavigateBack = { navigatedBack = true }, viewModel = viewModel)
            }
        }

        viewModel.onIntent(EditAssetIntent.CloseClicked)
        composeTestRule.waitForIdle()

        assertThat(navigatedBack).isTrue()
    }
}
