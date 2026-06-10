package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.core.testing.MainDispatcherExtension
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets.models.AssetsEffect
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets.models.AssetsIntent
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets.models.AssetsUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
class AssetsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension(testDispatcher)

    @Test
    fun `initial state is Loading`() = runTest(testDispatcher) {
        val viewModel = AssetsViewModel()
        assertThat(viewModel.uiState.value).isEqualTo(AssetsUiState.Loading)
    }

    @Test
    fun `load emits Content after the delay`() = runTest(testDispatcher) {
        val viewModel = AssetsViewModel()
        advanceUntilIdle()
        assertThat(viewModel.uiState.value).isEqualTo(AssetsUiState.Content)
    }

    @Test
    fun `SettingsClicked emits NavigateToSettings`() = runTest(testDispatcher) {
        val viewModel = AssetsViewModel()
        advanceUntilIdle()

        viewModel.effects.test {
            viewModel.onIntent(AssetsIntent.SettingsClicked)
            advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(AssetsEffect.NavigateToSettings)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `AddAccountClicked emits NavigateToAddAccount`() = runTest(testDispatcher) {
        val viewModel = AssetsViewModel()
        advanceUntilIdle()

        viewModel.effects.test {
            viewModel.onIntent(AssetsIntent.AddAccountClicked)
            advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(AssetsEffect.NavigateToAddAccount)
            cancelAndConsumeRemainingEvents()
        }
    }
}
