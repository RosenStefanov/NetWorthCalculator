package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.core.testing.MainDispatcherExtension
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities.models.LiabilitiesEffect
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities.models.LiabilitiesIntent
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities.models.LiabilitiesUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
class LiabilitiesViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension(testDispatcher)

    @Test
    fun `initial state is Loading`() = runTest(testDispatcher) {
        val viewModel = LiabilitiesViewModel()
        assertThat(viewModel.uiState.value).isEqualTo(LiabilitiesUiState.Loading)
    }

    @Test
    fun `load emits Content after the delay`() = runTest(testDispatcher) {
        val viewModel = LiabilitiesViewModel()
        advanceUntilIdle()
        assertThat(viewModel.uiState.value).isEqualTo(LiabilitiesUiState.Content)
    }

    @Test
    fun `SettingsClicked emits NavigateToSettings`() = runTest(testDispatcher) {
        val viewModel = LiabilitiesViewModel()
        advanceUntilIdle()

        viewModel.effects.test {
            viewModel.onIntent(LiabilitiesIntent.SettingsClicked)
            advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(LiabilitiesEffect.NavigateToSettings)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `AddAccountClicked emits NavigateToAddAccount`() = runTest(testDispatcher) {
        val viewModel = LiabilitiesViewModel()
        advanceUntilIdle()

        viewModel.effects.test {
            viewModel.onIntent(LiabilitiesIntent.AddAccountClicked)
            advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(LiabilitiesEffect.NavigateToAddAccount)
            cancelAndConsumeRemainingEvents()
        }
    }
}
