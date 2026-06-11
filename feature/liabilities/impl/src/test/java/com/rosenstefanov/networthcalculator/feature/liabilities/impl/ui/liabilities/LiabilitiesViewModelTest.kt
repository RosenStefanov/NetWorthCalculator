package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.core.testing.MainDispatcherExtension
import com.rosenstefanov.networthcalculator.core.ui.models.SortMode
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
        val content = viewModel.uiState.value as LiabilitiesUiState.Content
        assertThat(content.total).isEqualTo("$127,550")
        assertThat(content.deltaText).isEqualTo("−1.8%")
        assertThat(content.isGain).isFalse()
        assertThat(content.summary).isEqualTo("4 debts · 4 categories")
        assertThat(content.allocationTotal).isEqualTo(127_550L)
        assertThat(content.allocation.map { it.name })
            .containsExactly("Property", "Vehicle", "Revolving", "Education")
            .inOrder()
        assertThat(content.holdings).hasSize(4)
        assertThat(content.holdingsTotal).isEqualTo(127_550L)
        assertThat(content.selectedSort).isEqualTo(SortMode.Largest)
    }

    @Test
    fun `SortSelected updates the selected sort`() = runTest(testDispatcher) {
        val viewModel = LiabilitiesViewModel()
        advanceUntilIdle()
        val before = viewModel.uiState.value as LiabilitiesUiState.Content

        viewModel.onIntent(LiabilitiesIntent.SortSelected(SortMode.Name))
        advanceUntilIdle()

        val after = viewModel.uiState.value as LiabilitiesUiState.Content
        assertThat(after.selectedSort).isEqualTo(SortMode.Name)
        assertThat(after).isEqualTo(before.copy(selectedSort = SortMode.Name))
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
