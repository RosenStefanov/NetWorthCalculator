package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.core.testing.MainDispatcherExtension
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset.models.EditAssetEffect
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset.models.EditAssetIntent
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset.models.EditAssetUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
class EditAssetViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension(testDispatcher)

    @Test
    fun `initial state is Content`() = runTest(testDispatcher) {
        val viewModel = EditAssetViewModel()
        assertThat(viewModel.uiState.value).isEqualTo(EditAssetUiState.Content)
    }

    @Test
    fun `CloseClicked emits NavigateBack`() = runTest(testDispatcher) {
        val viewModel = EditAssetViewModel()
        viewModel.effects.test {
            viewModel.onIntent(EditAssetIntent.CloseClicked)
            advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(EditAssetEffect.NavigateBack)
            cancelAndConsumeRemainingEvents()
        }
    }
}
