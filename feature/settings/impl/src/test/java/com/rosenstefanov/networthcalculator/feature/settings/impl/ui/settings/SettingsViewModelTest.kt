package com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.rosenstefanov.networthcalculator.core.testing.MainDispatcherExtension
import com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings.models.SettingsEffect
import com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings.models.SettingsIntent
import com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings.models.ThemeMode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension(testDispatcher)

    @Test
    fun `NameChanged updates the name`() = runTest(testDispatcher) {
        val viewModel = SettingsViewModel()
        viewModel.onIntent(SettingsIntent.NameChanged("Sam"))
        assertThat(viewModel.uiState.value.name).isEqualTo("Sam")
    }

    @Test
    fun `ThemeSelected updates the theme`() = runTest(testDispatcher) {
        val viewModel = SettingsViewModel()
        viewModel.onIntent(SettingsIntent.ThemeSelected(ThemeMode.Dark))
        assertThat(viewModel.uiState.value.theme).isEqualTo(ThemeMode.Dark)
    }

    @Test
    fun `BackClicked emits NavigateBack`() = runTest(testDispatcher) {
        val viewModel = SettingsViewModel()
        viewModel.effects.test {
            viewModel.onIntent(SettingsIntent.BackClicked)
            advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(SettingsEffect.NavigateBack)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `placeholder intents keep state unchanged`() = runTest(testDispatcher) {
        val viewModel = SettingsViewModel()
        val before = viewModel.uiState.value
        viewModel.onIntent(SettingsIntent.CurrencyClicked)
        viewModel.onIntent(SettingsIntent.RateClicked)
        viewModel.onIntent(SettingsIntent.PrivacyClicked)
        assertThat(viewModel.uiState.value).isEqualTo(before)
    }
}
