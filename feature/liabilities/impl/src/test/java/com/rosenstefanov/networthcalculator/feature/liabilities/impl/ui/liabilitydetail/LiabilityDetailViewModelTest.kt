package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.core.testing.MainDispatcherExtension
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail.models.LiabilityDetailEffect
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail.models.LiabilityDetailIntent
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail.models.LiabilityDetailUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
class LiabilityDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension(testDispatcher)

    @Test
    fun `initial state is Content with default range`() = runTest(testDispatcher) {
        val viewModel = LiabilityDetailViewModel()
        assertThat(viewModel.uiState.value).isEqualTo(LiabilityDetailUiState.Content(range = "1Y"))
    }

    @Test
    fun `RangeSelected updates the range`() = runTest(testDispatcher) {
        val viewModel = LiabilityDetailViewModel()
        viewModel.onIntent(LiabilityDetailIntent.RangeSelected("All"))
        assertThat((viewModel.uiState.value as LiabilityDetailUiState.Content).range).isEqualTo("All")
    }

    @Test
    fun `CloseClicked emits NavigateBack`() = runTest(testDispatcher) {
        val viewModel = LiabilityDetailViewModel()
        viewModel.effects.test {
            viewModel.onIntent(LiabilityDetailIntent.CloseClicked)
            advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(LiabilityDetailEffect.NavigateBack)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `EditClicked emits NavigateToEdit`() = runTest(testDispatcher) {
        val viewModel = LiabilityDetailViewModel()
        viewModel.effects.test {
            viewModel.onIntent(LiabilityDetailIntent.EditClicked)
            advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(LiabilityDetailEffect.NavigateToEdit)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `DeleteClicked then DeleteDismissed toggles the dialog`() = runTest(testDispatcher) {
        val viewModel = LiabilityDetailViewModel()
        viewModel.onIntent(LiabilityDetailIntent.DeleteClicked)
        assertThat((viewModel.uiState.value as LiabilityDetailUiState.Content).showDeleteDialog).isTrue()
        viewModel.onIntent(LiabilityDetailIntent.DeleteDismissed)
        assertThat((viewModel.uiState.value as LiabilityDetailUiState.Content).showDeleteDialog).isFalse()
    }

    @Test
    fun `DeleteConfirmed emits NavigateBack`() = runTest(testDispatcher) {
        val viewModel = LiabilityDetailViewModel()
        viewModel.effects.test {
            viewModel.onIntent(LiabilityDetailIntent.DeleteConfirmed)
            advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(LiabilityDetailEffect.NavigateBack)
            cancelAndConsumeRemainingEvents()
        }
    }
}
