package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.core.common.CurrencyFormatter
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

    private fun viewModel() = EditAssetViewModel(CurrencyFormatter())

    @Test
    fun `initial state is pre-filled Content`() = runTest(testDispatcher) {
        val state = viewModel().uiState.value as EditAssetUiState.Content
        assertThat(state.name).isEqualTo("Primary Residence")
        assertThat(state.amount).isEqualTo("312,000")
    }

    @Test
    fun `NameChanged updates the name`() = runTest(testDispatcher) {
        val viewModel = viewModel()
        viewModel.onIntent(EditAssetIntent.NameChanged("Lake House"))
        assertThat((viewModel.uiState.value as EditAssetUiState.Content).name).isEqualTo("Lake House")
    }

    @Test
    fun `AmountChanged formats the amount`() = runTest(testDispatcher) {
        val viewModel = viewModel()
        viewModel.onIntent(EditAssetIntent.AmountChanged("420000"))
        assertThat((viewModel.uiState.value as EditAssetUiState.Content).amount).isEqualTo("420,000")
    }

    @Test
    fun `DescriptionChanged updates the description`() = runTest(testDispatcher) {
        val viewModel = viewModel()
        viewModel.onIntent(EditAssetIntent.DescriptionChanged("Updated note"))
        assertThat((viewModel.uiState.value as EditAssetUiState.Content).description).isEqualTo("Updated note")
    }

    @Test
    fun `SaveClicked emits NavigateBack`() = runTest(testDispatcher) {
        val viewModel = viewModel()
        viewModel.effects.test {
            viewModel.onIntent(EditAssetIntent.SaveClicked)
            advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(EditAssetEffect.NavigateBack)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `CloseClicked emits NavigateBack`() = runTest(testDispatcher) {
        val viewModel = viewModel()
        viewModel.effects.test {
            viewModel.onIntent(EditAssetIntent.CloseClicked)
            advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(EditAssetEffect.NavigateBack)
            cancelAndConsumeRemainingEvents()
        }
    }
}
