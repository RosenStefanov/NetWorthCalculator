package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
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

    @Test
    fun `initial state is Content`() = runTest(testDispatcher) {
        val viewModel = EditLiabilityViewModel()
        assertThat(viewModel.uiState.value).isEqualTo(EditLiabilityUiState.Content)
    }

    @Test
    fun `CloseClicked emits NavigateBack`() = runTest(testDispatcher) {
        val viewModel = EditLiabilityViewModel()
        viewModel.effects.test {
            viewModel.onIntent(EditLiabilityIntent.CloseClicked)
            advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(EditLiabilityEffect.NavigateBack)
            cancelAndConsumeRemainingEvents()
        }
    }
}
