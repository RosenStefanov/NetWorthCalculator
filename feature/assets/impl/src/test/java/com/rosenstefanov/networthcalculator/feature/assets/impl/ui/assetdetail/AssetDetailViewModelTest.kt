package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.core.testing.MainDispatcherExtension
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail.models.AssetDetailEffect
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail.models.AssetDetailIntent
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail.models.AssetDetailUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
class AssetDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension(testDispatcher)

    @Test
    fun `initial state is Content with default range`() = runTest(testDispatcher) {
        val viewModel = AssetDetailViewModel()
        assertThat(viewModel.uiState.value).isEqualTo(AssetDetailUiState.Content(range = "1Y"))
    }

    @Test
    fun `RangeSelected updates the range`() = runTest(testDispatcher) {
        val viewModel = AssetDetailViewModel()
        viewModel.onIntent(AssetDetailIntent.RangeSelected("3M"))
        assertThat((viewModel.uiState.value as AssetDetailUiState.Content).range).isEqualTo("3M")
    }

    @Test
    fun `CloseClicked emits NavigateBack`() = runTest(testDispatcher) {
        val viewModel = AssetDetailViewModel()
        viewModel.effects.test {
            viewModel.onIntent(AssetDetailIntent.CloseClicked)
            advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(AssetDetailEffect.NavigateBack)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `EditClicked emits NavigateToEdit`() = runTest(testDispatcher) {
        val viewModel = AssetDetailViewModel()
        viewModel.effects.test {
            viewModel.onIntent(AssetDetailIntent.EditClicked)
            advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(AssetDetailEffect.NavigateToEdit)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `DeleteClicked then DeleteDismissed toggles the dialog`() = runTest(testDispatcher) {
        val viewModel = AssetDetailViewModel()
        viewModel.onIntent(AssetDetailIntent.DeleteClicked)
        assertThat((viewModel.uiState.value as AssetDetailUiState.Content).showDeleteDialog).isTrue()
        viewModel.onIntent(AssetDetailIntent.DeleteDismissed)
        assertThat((viewModel.uiState.value as AssetDetailUiState.Content).showDeleteDialog).isFalse()
    }

    @Test
    fun `DeleteConfirmed emits NavigateBack`() = runTest(testDispatcher) {
        val viewModel = AssetDetailViewModel()
        viewModel.effects.test {
            viewModel.onIntent(AssetDetailIntent.DeleteConfirmed)
            advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(AssetDetailEffect.NavigateBack)
            cancelAndConsumeRemainingEvents()
        }
    }
}
