package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.core.testing.MainDispatcherExtension
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability.models.AddLiabilityEffect
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability.models.AddLiabilityIntent
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability.models.AddLiabilityUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
class AddLiabilityViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension(testDispatcher)

    @Test
    fun `initial state is empty Content`() = runTest(testDispatcher) {
        val viewModel = AddLiabilityViewModel()
        assertThat(viewModel.uiState.value).isEqualTo(AddLiabilityUiState.Content(name = ""))
    }

    @Test
    fun `NameChanged updates the name`() = runTest(testDispatcher) {
        val viewModel = AddLiabilityViewModel()
        viewModel.onIntent(AddLiabilityIntent.NameChanged("Mortgage"))
        assertThat((viewModel.uiState.value as AddLiabilityUiState.Content).name).isEqualTo("Mortgage")
    }

    @Test
    fun `SaveClicked emits NavigateBack`() = runTest(testDispatcher) {
        val viewModel = AddLiabilityViewModel()
        viewModel.effects.test {
            viewModel.onIntent(AddLiabilityIntent.SaveClicked)
            advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(AddLiabilityEffect.NavigateBack)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `CloseClicked emits NavigateBack`() = runTest(testDispatcher) {
        val viewModel = AddLiabilityViewModel()
        viewModel.effects.test {
            viewModel.onIntent(AddLiabilityIntent.CloseClicked)
            advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(AddLiabilityEffect.NavigateBack)
            cancelAndConsumeRemainingEvents()
        }
    }
}
