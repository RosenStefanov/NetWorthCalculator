package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail

import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail.models.AssetDetailIntent
import org.junit.Test

class AssetDetailScreenTest : ScreenSnapshotTest() {

    @Test
    fun testAssetDetailScreenPreview() = captureSnapshot { AssetDetailScreenPreview() }

    // Route-level: a NavigateBack effect is routed to the back callback.
    @Test
    fun navigateBackEffect_invokesOnNavigateBack() {
        var navigatedBack = false
        val viewModel = AssetDetailViewModel()
        composeTestRule.setContent {
            NetWorthCalculatorTheme {
                AssetDetailScreen(onNavigateBack = { navigatedBack = true }, viewModel = viewModel)
            }
        }

        viewModel.onIntent(AssetDetailIntent.CloseClicked)
        composeTestRule.waitForIdle()

        assertThat(navigatedBack).isTrue()
    }
}
