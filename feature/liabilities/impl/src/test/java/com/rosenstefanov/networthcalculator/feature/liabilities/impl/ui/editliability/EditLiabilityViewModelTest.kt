package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.core.common.CurrencyFormatter
import com.rosenstefanov.networthcalculator.core.testing.MainDispatcherExtension
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability.models.EditLiabilityEffect
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability.models.EditLiabilityIntent
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability.models.EditLiabilityUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
class EditLiabilityViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension(testDispatcher)

    private fun viewModel() = EditLiabilityViewModel(CurrencyFormatter())

    @Test
    fun `initial state is pre-filled Content`() = runTest(testDispatcher) {
        val state = viewModel().uiState.value as EditLiabilityUiState.Content
        assertThat(state.name).isEqualTo("Mortgage")
        assertThat(state.amount).isEqualTo("108,200")
    }

    @Test
    fun `NameChanged updates the name`() = runTest(testDispatcher) {
        val viewModel = viewModel()
        viewModel.onIntent(EditLiabilityIntent.NameChanged("Home Loan"))
        assertThat((viewModel.uiState.value as EditLiabilityUiState.Content).name).isEqualTo("Home Loan")
    }

    @Test
    fun `AmountChanged formats the amount`() = runTest(testDispatcher) {
        val viewModel = viewModel()
        viewModel.onIntent(EditLiabilityIntent.AmountChanged("95000"))
        assertThat((viewModel.uiState.value as EditLiabilityUiState.Content).amount).isEqualTo("95,000")
    }

    @Test
    fun `DescriptionChanged updates the description`() = runTest(testDispatcher) {
        val viewModel = viewModel()
        viewModel.onIntent(EditLiabilityIntent.DescriptionChanged("Updated note"))
        assertThat((viewModel.uiState.value as EditLiabilityUiState.Content).description).isEqualTo("Updated note")
    }

    @Test
    fun `SaveClicked emits NavigateBack`() = runTest(testDispatcher) {
        val viewModel = viewModel()
        viewModel.effects.test {
            viewModel.onIntent(EditLiabilityIntent.SaveClicked)
            advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(EditLiabilityEffect.NavigateBack)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `CloseClicked emits NavigateBack`() = runTest(testDispatcher) {
        val viewModel = viewModel()
        viewModel.effects.test {
            viewModel.onIntent(EditLiabilityIntent.CloseClicked)
            advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(EditLiabilityEffect.NavigateBack)
            cancelAndConsumeRemainingEvents()
        }
    }
}
