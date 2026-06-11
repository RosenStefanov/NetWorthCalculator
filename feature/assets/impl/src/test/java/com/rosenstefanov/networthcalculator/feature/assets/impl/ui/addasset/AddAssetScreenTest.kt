package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.addasset

import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.addasset.models.AddAssetIntent
import org.junit.Test

class AddAssetScreenTest : ScreenSnapshotTest() {

    @Test
    fun testAddAssetScreenPreview() = captureSnapshot { AddAssetScreenPreview() }

    // Route-level: a NavigateBack effect is routed to the back callback.
    @Test
    fun navigateBackEffect_invokesOnNavigateBack() {
        var navigatedBack = false
        val viewModel = AddAssetViewModel()
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                AddAssetScreen(onNavigateBack = { navigatedBack = true }, viewModel = viewModel)
            }
        }

        viewModel.onIntent(AddAssetIntent.SaveClicked)
        composeTestRule.waitForIdle()

        assertThat(navigatedBack).isTrue()
    }
}
