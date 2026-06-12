package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.addasset

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.core.testing.MainDispatcherExtension
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.addasset.models.AddAssetEffect
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.addasset.models.AddAssetIntent
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.addasset.models.AddAssetUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
class AddAssetViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension(testDispatcher)

    @Test
    fun `initial state is empty Content`() = runTest(testDispatcher) {
        val viewModel = AddAssetViewModel()
        assertThat(viewModel.uiState.value).isEqualTo(AddAssetUiState.Content(name = ""))
    }

    @Test
    fun `NameChanged updates the name`() = runTest(testDispatcher) {
        val viewModel = AddAssetViewModel()
        viewModel.onIntent(AddAssetIntent.NameChanged("Brokerage"))
        assertThat((viewModel.uiState.value as AddAssetUiState.Content).name).isEqualTo("Brokerage")
    }

    @Test
    fun `SaveClicked emits NavigateBack`() = runTest(testDispatcher) {
        val viewModel = AddAssetViewModel()
        viewModel.effects.test {
            viewModel.onIntent(AddAssetIntent.SaveClicked)
            advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(AddAssetEffect.NavigateBack)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `CloseClicked emits NavigateBack`() = runTest(testDispatcher) {
        val viewModel = AddAssetViewModel()
        viewModel.effects.test {
            viewModel.onIntent(AddAssetIntent.CloseClicked)
            advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(AddAssetEffect.NavigateBack)
            cancelAndConsumeRemainingEvents()
        }
    }
}
