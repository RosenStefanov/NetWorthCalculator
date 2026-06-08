package com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.core.testing.MainDispatcherExtension
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.DashboardEffect
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.DashboardIntent
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.DashboardUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import java.math.BigDecimal

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension(testDispatcher)

    @Test
    fun `initial state is Loading before load completes`() = runTest(testDispatcher) {
        // When
        val viewModel = DashboardViewModel()

        // Then
        assertThat(viewModel.uiState.value).isEqualTo(DashboardUiState.Loading)
    }

    @Test
    fun `load emits Content after the load delay`() = runTest(testDispatcher) {
        // Given
        val viewModel = DashboardViewModel()

        // When
        advanceUntilIdle()

        // Then
        assertThat(viewModel.uiState.value).isInstanceOf(DashboardUiState.Content::class.java)
    }

    @Test
    fun `loaded Content holds the expected totals and holdings`() = runTest(testDispatcher) {
        // Given
        val viewModel = DashboardViewModel()

        // When
        advanceUntilIdle()

        // Then
        val content = viewModel.uiState.value as DashboardUiState.Content
        assertThat(content.netWorth.amount).isEqualTo(BigDecimal("42500.00"))
        assertThat(content.netWorth.currencyCode).isEqualTo("EUR")
        assertThat(content.assetsTotal.amount).isEqualTo(BigDecimal("58200.00"))
        assertThat(content.liabilitiesTotal.amount).isEqualTo(BigDecimal("15700.00"))
        assertThat(content.topHoldings).hasSize(5)
        assertThat(content.topHoldings.map { it.label })
            .containsExactly("Apartment", "Mortgage", "S&P 500 ETF", "Savings (USD)", "Credit Card")
            .inOrder()
        assertThat(content.topHoldings.filter { it.isLiability }.map { it.label })
            .containsExactly("Mortgage", "Credit Card")
        // assetsWeight = 58200 / (58200 + 15700) ≈ 0.7875
        assertThat(content.assetsWeight).isWithin(0.001f).of(0.7875f)
        assertThat(content.selectedRange).isEqualTo("1Y")
        assertThat(content.ranges).containsExactly("1M", "6M", "1Y", "All").inOrder()
        assertThat(content.trend.assets).hasSize(12)
        assertThat(content.trend.monthLabels).containsExactly("Jul", "Sep", "Nov", "Jan", "Mar", "Jun").inOrder()
    }

    @Test
    fun `RangeSelected updates selectedRange without reloading`() = runTest(testDispatcher) {
        // Given - loaded content on the default range
        val viewModel = DashboardViewModel()
        advanceUntilIdle()
        val before = viewModel.uiState.value as DashboardUiState.Content
        assertThat(before.selectedRange).isEqualTo("1Y")

        // When
        viewModel.onIntent(DashboardIntent.RangeSelected("6M"))
        advanceUntilIdle()

        // Then - only the selected range changed; the rest of the content is untouched
        val after = viewModel.uiState.value as DashboardUiState.Content
        assertThat(after.selectedRange).isEqualTo("6M")
        assertThat(after).isEqualTo(before.copy(selectedRange = "6M"))
    }

    @Test
    fun `Refresh transitions through Loading back to Content`() = runTest(testDispatcher) {
        // Given - start in Content
        val viewModel = DashboardViewModel()
        advanceUntilIdle()

        // When / Then
        viewModel.uiState.test {
            assertThat(awaitItem()).isInstanceOf(DashboardUiState.Content::class.java)

            viewModel.onIntent(DashboardIntent.Refresh)
            assertThat(awaitItem()).isEqualTo(DashboardUiState.Loading)

            advanceUntilIdle()
            assertThat(awaitItem()).isInstanceOf(DashboardUiState.Content::class.java)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `RetryClicked reloads content`() = runTest(testDispatcher) {
        // Given
        val viewModel = DashboardViewModel()
        advanceUntilIdle()

        // When / Then
        viewModel.uiState.test {
            assertThat(awaitItem()).isInstanceOf(DashboardUiState.Content::class.java)

            viewModel.onIntent(DashboardIntent.RetryClicked)
            assertThat(awaitItem()).isEqualTo(DashboardUiState.Loading)

            advanceUntilIdle()
            assertThat(awaitItem()).isInstanceOf(DashboardUiState.Content::class.java)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `SettingsClicked emits NavigateToSettings effect`() = runTest(testDispatcher) {
        // Given
        val viewModel = DashboardViewModel()
        advanceUntilIdle()

        // When / Then
        viewModel.effects.test {
            viewModel.onIntent(DashboardIntent.SettingsClicked)
            advanceUntilIdle()

            assertThat(awaitItem()).isEqualTo(DashboardEffect.NavigateToSettings)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `SettingsClicked does not change the ui state`() = runTest(testDispatcher) {
        // Given
        val viewModel = DashboardViewModel()
        advanceUntilIdle()
        val stateBefore = viewModel.uiState.value

        // When
        viewModel.onIntent(DashboardIntent.SettingsClicked)
        advanceUntilIdle()

        // Then
        assertThat(viewModel.uiState.value).isEqualTo(stateBefore)
    }
}
